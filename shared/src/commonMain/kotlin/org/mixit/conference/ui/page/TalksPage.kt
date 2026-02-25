package org.mixit.conference.ui.page

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.img
import kotlinx.html.small
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.unsafe
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.TALKS_YEARS
import org.mixit.conference.ui.component.languageComponent
import org.mixit.conference.ui.component.roomComponent
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.speakersComponentInDiv
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.talkFormatComponent
import org.mixit.conference.ui.component.topicPrefixComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.form.FormField
import org.mixit.conference.ui.form.FormFieldType
import org.mixit.conference.ui.formatter.formatDate
import org.mixit.conference.ui.formatter.formatTime
import org.mixit.conference.ui.renderTemplate

data class TalksCriteria(val topic: Topic?, val filter: String?, val favorites: Boolean)

fun talkSearchForm(
    context: Context,
    values: TalksCriteria? = null,
    valuesInRequest: Map<String, String?> = emptyMap(),
    converter: (FormDescriptor<TalksCriteria>) -> TalksCriteria = { throw IllegalStateException() }
) = FormDescriptor(
    fields = listOf(
        FormField(
            "topic",
            type = FormFieldType.Select,
            fieldPlaceholder = context.i18n("talks.topic"),
            options = listOf("" to context.i18n("talks.topic.all")) + Topic.entries.map { it.name to context.i18n("topics.${it.value}.title") },
            defaultValue = values?.topic?.name ?: valuesInRequest["topic"]
        ),
        FormField(
            "filter",
            type = FormFieldType.Text,
            fieldPlaceholder = context.i18n("talks.filter"),
            defaultValue = values?.filter ?: valuesInRequest["filter"],
        ),
        FormField(
            "favorites",
            type = FormFieldType.Checkbox,
            help = context.i18n("talks.favorites"),
            defaultValue = values?.favorites?.toString() ?: valuesInRequest["favorites"],
        )

    ),
    converter = converter
)

fun renderTalks(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    favorites: List<String>,
    talksByDate: Map<LocalDateTime?, List<Talk>>,
    filter: FormDescriptor<TalksCriteria>
) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = context.i18n("talks.title")
            h1 {
                img(classes = "mxt-img__header-talk") {
                    alt = pageTitle
                }
                +pageTitle
            }
            yearSelectorComponent(context, event, url = "", alt = pageTitle, years = TALKS_YEARS)

            val keys: List<LocalDate?> = talksByDate.keys.map { it?.date }.toSet().sortedBy { it }

            div(classes = "mt-3") {
                filter.filterForm(this, "${context.uriBasePath}/${event.year}", formMethod = FormMethod.get) {
                    button(classes = "btn mxt-btn-primary") {
                        type = ButtonType.submit
                        small {
                            +context.i18n("talks.filter.button")
                        }
                    }
                }
            }
            if (!context.isAuthenticated) {
                small { +context.i18n("favorite.connected") }
            }


            keys.forEach { date ->
                if (date != null) {
                    h2(classes = "mt-5") {
                        +date.formatDate(context.language)
                    }
                }
                val talkFilteredByDate:Map<LocalDateTime?, List<Talk>> = talksByDate.filter { it.key?.date == date }

                div(classes = "mxt-year__selector") {
                    talkFilteredByDate.keys.filterNotNull().sorted().forEach { time ->
                        a(href = "#$time", classes = "mxt-talks__time-card") {
                            +time.formatTime()
                        }
                    }
                }
                talkFilteredByDate.entries.sortedBy { it.key }.forEach { (date, talks) ->
                    div(classes = "mxt-talks__container ${if (date == null) "mxt-talks__container-no-date" else ""}") {
                        if (date != null) {
                            div(classes = "mxt-talks__time") {
                                attributes["id"] = date.toString()
                                +date.formatTime()
                            }
                        }
                        div {
                            talks.sortedBy { it.room.name }.forEach { talk ->
                                div(classes = "mxt-talks__container-line") {
                                    a(href = "${context.uriBasePath}/${event.year}/${talk.slug}") {
                                        topicPrefixComponent(context, talk.topic, talk.title)
                                        div(classes = "d-flex align-items-center") {
                                            languageComponent(context, talk)
                                            talkFormatComponent(context, talk)
                                            if (talk.videos.isNotEmpty()) {
                                                img(classes = "mxt-img__header-video ms-2") {
                                                    attributes["aria-label"] = context.i18n("talks.video.available")
                                                    title = context.i18n("talks.video.available")
                                                }
                                            }
                                            roomComponent(context, talk)

                                            if (context.email != null) {
                                                if (favorites.contains(talk.id)) {
                                                    img(
                                                        src = "/images/svg/favorites/mxt-favorite.svg",
                                                        classes = "mxt-favorite"
                                                    ) {
                                                        alt = context.i18n("favorite.selected")
                                                    }
                                                } else {
                                                    img(
                                                        src = "/images/svg/favorites/mxt-favorite-non.svg",
                                                        classes = "mxt-favorite"
                                                    ) {
                                                        alt = context.i18n("favorite.selected")
                                                    }
                                                }
                                            }

                                        }


                                        div(classes = "mt-3 mxt-talks__detail mxt-no-link") {
                                            unsafe {
                                                raw(context.markdown(talk.summary))
                                            }
                                            unsafe {
                                                raw(context.markdown(talk.description))
                                            }
                                        }
                                        span { +"..." }
                                        speakersComponentInDiv(context, talk.speakers)
                                        div(classes = "mb-2") { +"  " }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }


    }

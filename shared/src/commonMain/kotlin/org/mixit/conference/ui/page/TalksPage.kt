package org.mixit.conference.ui.page

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.component.languageComponent
import org.mixit.conference.ui.component.roomComponent
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.speakersComponentInDiv
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.talkFormatComponent
import org.mixit.conference.ui.component.topicPrefixComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.formatter.formatDate
import org.mixit.conference.ui.formatter.formatTime
import org.mixit.conference.ui.renderTemplate

fun renderTalks(context: Context, event: Event, sponsors: List<Sponsor>, talksByDate: Map<LocalDateTime?, List<Talk>>) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = context.i18n("talks.title")
            h1 {
                img(classes = "mxt-img__header-talk") {
                    alt = pageTitle
                }
                +pageTitle
            }
            yearSelectorComponent(context, event, url = "", alt = pageTitle)

            val keys: List<LocalDate?> = talksByDate.keys.map { it?.date }.toSet().sortedBy { it }


            // TODO add a search field

            keys.forEach { date ->
                if(date != null) {
                    h2(classes = "mt-5") {
                        +date.formatDate(context.language)
                    }
                }
                val talkFilteredByDate = talksByDate.filter { it.key?.date == date }
                talkFilteredByDate.keys.filterNotNull().forEach { time ->
                    a(href = "#$time", classes = "mxt-talks__time-card") {
                        +time.formatTime()
                    }
                }
                talkFilteredByDate.forEach { (date, talks) ->
                    div(classes = "mxt-talks__container ${if (date == null) "mxt-talks__container-no-date" else ""}") {
                        if(date != null) {
                            div(classes = "mxt-talks__time") {
                                attributes["id"] = date.toString()
                                +date.formatTime()
                            }
                        }
                        div {
                            talks.forEach { talk ->
                                div(classes = "mxt-talks__container-line") {
                                    a(href = "${context.uriBasePath}/${event.year}/${talk.slug}") {
                                        topicPrefixComponent(context, talk.topic, talk.title)
                                        div(classes = "d-flex align-items-center") {
                                            languageComponent(context, talk)
                                            talkFormatComponent(context, talk)
                                            if(talk.videos.isNotEmpty()) {
                                                img(classes = "mxt-img__header-video ms-2") {
                                                    attributes["aria-label"] = context.i18n("talks.video.available")
                                                    title = context.i18n("talks.video.available")
                                                }
                                            }
                                            roomComponent(context, talk)
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
                                        div(classes = "mb-2") { + "  " }
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
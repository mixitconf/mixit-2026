package org.mixit.conference.ui.page

import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.img
import kotlinx.html.small
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsorGroupComponent
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.renderTemplate


fun renderTalkFeedbacks(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    talks: Map<Talk, String>,
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
            talks
                .entries
                .sortedBy { it.key.title }
                .forEach { (talk, svg) ->
                    displayTalk(talk, event, context, emptyList(), svg)
                }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }


    }

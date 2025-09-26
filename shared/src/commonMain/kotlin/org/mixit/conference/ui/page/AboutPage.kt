package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.SectionEffect
import org.mixit.conference.ui.component.SectionStyle
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.renderTemplate

fun renderAboutPage(
    context: Context,
    event: Event,
    staff: List<Staff>,
    volunteers: List<Volunteer>,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("about.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-about") {
                    alt = pageTitle
                }
                +pageTitle
            }
            p(classes = "lead") {
                +context.i18n("about.intro")
            }
            p { +context.i18n("about.history") }
            p { +context.i18n("about.who") }

            yearSelectorComponent(context, event, url = "/about", alt = pageTitle)
        }


        sectionComponent(
            context, i18nKey = "about.team.title",
            effect = SectionEffect.BOTH,
            style = SectionStyle.LIGHT
        ) {
            p { +context.i18n("about.team.intro") }

            div(classes = "mxt-speaker__grid") {
                staff.shuffled().forEach {
                    div(classes = "mxt-speaker") {
                        //href = "${context.uriBasePath}/user/${it.id}"
                        attributes["alt"] = "${it.firstname} ${it.lastname}"
                        attributes["aria-label"] = "${it.firstname} ${it.lastname}"

                        h4 { +it.firstname }
                        div { +it.lastname }
                        img(classes = "mxt-speaker__img") {
                            src = it.photoUrl ?: DEFAULT_IMG_URL
                            attributes["aria-label"] = "${it.firstname} ${it.lastname} photo"
                        }
                    }
                }
            }
            div(classes = "mxt-speaker") { +"    " }
        }

        sectionComponent(context, i18nKey = "about.volunteers.title") {
            p { +context.i18n("about.volunteers.intro") }
            div(classes = "mxt-speaker__grid") {
                volunteers.shuffled().forEach {
                    div(classes = "mxt-speaker") {
                        // href = "${context.uriBasePath}/user/${it.id}"
                        attributes["alt"] = "${it.firstname} ${it.lastname}"
                        attributes["aria-label"] = "${it.firstname} ${it.lastname}"

                        h4 { +it.firstname }
                        div { +it.lastname }
                        img(classes = "mxt-speaker__img") {
                            src = it.photoUrl ?: DEFAULT_IMG_URL
                            attributes["aria-label"] = "${it.firstname} ${it.lastname} photo"
                        }
                    }
                }
            }
        }


        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }
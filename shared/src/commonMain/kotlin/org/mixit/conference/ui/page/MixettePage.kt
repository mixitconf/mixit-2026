package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.MIXETTE_YEARS
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.renderTemplate

fun renderMixettePage(
    context: Context,
    event: Event,
    organizations: List<Organization>,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("mixette.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-mixette") {
                    alt = pageTitle
                }
                +pageTitle
            }

            p(classes = "lead") {
                +context.i18n("mixette.intro")
            }

            p { +context.i18n("mixette.description") }
        }


        sectionComponent(context) {
            yearSelectorComponent(context, event, url = "/mixette", alt = pageTitle, years = MIXETTE_YEARS)

            p { +context.i18n("mixette.orgas") }

            organizations.sortedBy { it.name }.forEach {
                div(classes = "d-flex align-items-center") {
                    img(classes = "mxt-speaker__img") {
                        src = (it.photoUrl?: DEFAULT_IMG_URL).replace("https://mixitconf.org", "")
                        attributes["aria-label"] = it.name
                    }
                    div(classes = "d-flex flex-column ms-3 mb-4") {
                        h3 { +it.name }
                        p {
                            unsafe {
                                raw(
                                    context.markdown(
                                        it.description[Language.FRENCH] ?: it.description[Language.ENGLISH].orEmpty()
                                    )
                                )
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
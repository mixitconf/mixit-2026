package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.component.linkAsPrimaryButton
import org.mixit.conference.ui.component.linkAsSecondaryButton
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.renderTemplate

fun renderSponsor(context: Context, event: Event, sponsors: List<Sponsor>) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = context.i18n("sponsor.title")
            h1 {
                img(classes = "mxt-img__header-sponsor") {
                    alt = pageTitle
                }
                +pageTitle
            }
            yearSelectorComponent(context, event, url = "/sponsors", alt = pageTitle)

            p(classes = "lead mt-3") {
                +context.i18n("sponsor.intro")
            }
            if(event.year == CURRENT_YEAR) {
                p {
                    +context.i18n("sponsor.become")
                }
                p {
                    +context.i18n("sponsor.partenariat")
                }

                div(classes = "mxt-btn__group mb-5") {
                    linkAsPrimaryButton(
                        context,
                        Link(
                            url = "mailto:contact@mixitconf.org",
                            name = context.i18n("sponsor.contact")
                        )
                    )
                    linkAsPrimaryButton(
                        context,
                        Link(
                            url = context.i18n("sponsor.leaflet.url"),
                            name = context.i18n("sponsor.leaflet.name")
                        )
                    )
                    linkAsPrimaryButton(
                        context,
                        Link(
                            url = context.i18n("sponsor.mixit.form.url"),
                            name = context.i18n("sponsor.mixit.form.name")
                        )
                    )
                    linkAsPrimaryButton(
                        context,
                        Link(
                            url = context.i18n("sponsor.mixteen.form.url"),
                            name = context.i18n("sponsor.mixteen.form.name")
                        )
                    )
                }
            }


            val sponsorByLevel = sponsors
                .groupBy { it.level }
                .entries
                .sortedByDescending { it.key.priority }


            sponsorByLevel.forEach { (level, sponsors) ->
                sectionComponent(context, i18nKey = "sponsor.level.${level.name}") {
                    sponsors.forEach { sponsor ->
                        sectionComponent(context) {
                            div(classes = "mxt-sponsor__grid") {
                                div {
                                    div(classes = "mxt-sponsor__image-container") {
                                        img(
                                            classes = "mxt-sponsor__image",
                                            alt = sponsor.name,
                                            src = sponsor.photoUrl!!.replace("https://mixitconf.org", "")
                                        )                                                                                       
                                    }
                                }
                                div {
                                    unsafe {
                                        raw(context.markdown(sponsor.description[Language.FRENCH] ?: ""))
                                    }
                                    div(classes = "mxt-btn__group") {
                                        sponsor.links.forEach { linkAsSecondaryButton(context, it) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }  
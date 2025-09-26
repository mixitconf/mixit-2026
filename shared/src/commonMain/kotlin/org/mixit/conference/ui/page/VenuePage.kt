package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.SectionEffect
import org.mixit.conference.ui.component.SectionStyle
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.renderTemplate

fun renderVenuePage(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("venue.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-venue") {
                    alt = pageTitle
                }
                +pageTitle
            }

            p(classes = "lead") {
                +context.i18n("venue.intro")
            }
            div(classes = "mb-4") {
                unsafe {
                    raw(context.i18n("venue.info"))
                }
            }


            sectionComponent(context, effect = SectionEffect.BOTH, style = SectionStyle.LIGHT) {
                div {
                    iframe {
                        src =
                            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d44532.84179104339!2d4.810176969092425!3d45.76512532471853!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47f4ea977b847259%3A0x9efbb71d73134a76!2sCPE%20Lyon!5e0!3m2!1sfr!2sfr!4v1679595963075!5m2!1sfr!2sfr"
                        width = "100%"
                        style = "border:0;border-radius:12px"
                        attributes["allowfullscreen"] = "true"
                        attributes["loading"] = "lazy"
                        attributes["referrerpolicy"] = "no-referrer-when-downgrade"
                    }
                }
                div(classes = "mxt-no-link") {
                    a(classes = "mxt-no-link") {
                        href = "https://www.google.com/maps/place/CPE+Lyon/@45.7841638,4.8675628,17z"
                        target = "_blank"
                        +context.i18n("venue.map")
                    }
                }
            }
            div(classes = "mt-5 mb-3") {
                transportMode(context, "mxt-img__transport-train", "venue.train")
                transportMode(context, "mxt-img__transport-bus", "venue.tram")
                transportMode(context, "mxt-img__transport-bike", "venue.cycle")
                transportMode(context, "mxt-img__transport-car", "venue.car")
                transportMode(context, "mxt-img__transport-flight", "venue.plane")
            }
        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

fun DIV.transportMode(context: Context, imgClass: String, key: String) {
    div(classes = "d-flex align-items-center mb-3") {
        img(classes = "me-3 $imgClass") {
            alt = context.i18n(key)
        }
        div(classes = "mxt-no-link") {
            unsafe {
                raw(context.i18n(key))
            }
        }
    }
}
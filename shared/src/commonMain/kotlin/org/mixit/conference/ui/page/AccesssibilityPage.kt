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

fun renderAccessibilityPage(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("accessibility.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-accessibility") {
                    alt = pageTitle
                }
                +pageTitle
            }
            p(classes = "lead") {
                +context.i18n("accessibility.intro")
            }
            p { +context.i18n("accessibility.free.ticket") }

            accessibilityElement(context, "mxt-img__accessibility-child-care", "accessibility.baby")
            accessibilityElement(context, "mxt-img__accessibility-hearing-impaired", "accessibility.mute")
            accessibilityElement(context, "mxt-img__accessibility-motor-disability", "accessibility.chair")
            accessibilityElement(context, "mxt-img__accessibility-pregnant", "accessibility.pregnant")
        }

        sectionComponent(context, effect = SectionEffect.BOTH, style = SectionStyle.LIGHT) {
            div(classes = "row") {
                div(classes = "col-6") {
                    img(classes = "img-fluid") {
                        src = "/images/jpg/mxt-accessibility1.jpg"
                        alt = context.i18n("accessibility.title")
                    }
                }
                div(classes = "col-6") {
                    img(classes = "img-fluid") {
                        src = "/images/jpg/mxt-accessibility2.jpg"
                        alt = context.i18n("accessibility.title")
                    }
                }
            }

        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

fun DIV.accessibilityElement(context: Context, imgClass: String, key: String) {
    div(classes = "d-flex align-items-center mb-3") {
        img(classes = "me-3 $imgClass") {
            alt = context.i18n(key)
        }
        span { +context.i18n(key) }

    }
}

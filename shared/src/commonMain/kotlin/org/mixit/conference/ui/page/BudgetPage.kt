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

fun renderBudgetPage(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("budget.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-budget") {
                    alt = pageTitle
                }
                +pageTitle
            }

            div(classes = "lead") {
                +context.i18n("budget.intro")
            }
            p(classes = "mt-2") {
                +context.i18n("budget.details")
            }
        }

        sectionComponent(context, "budget.lines.title", effect = SectionEffect.BOTH, style = SectionStyle.LIGHT) {
            p {
                +context.i18n("budget.lines.intro")
            }
            div(classes = "row justify-content-center align-items-center") {
                img(classes = "col-12 col-md-6 pb-3",) {
                    src = "/images/svg/budget-2026.svg"
                    alt = context.i18n("budget.lines.title")
                    style = "max-width:100%;max-height:20em;"
                }
                div(classes = "mxt-chart--container col-12 col-md-6") {
                    legend(context, "speakers","color1")
                    legend(context, "logistics","color2")
                    legend(context, "media","color3")
                    legend(context, "accessibility","color4")
                    legend(context, "venue","color5")
                    legend(context, "admin","color6")
                }
            }
            listOf("speakers", "logistics", "venue", "accessibility", "media", "admin").forEach {
                div(classes = "pt-4") {
                    b { +context.i18n("budget.lines.$it.title") }
                    div {
                        +context.i18n("budget.lines.$it.intro")
                    }
                }
            }
        }

        sectionComponent(context, "budget.why.title") {
            p {
                +context.i18n("budget.why.intro")
            }
            ul {
                li { +context.i18n("budget.why.step1") }
                li { +context.i18n("budget.why.step2") }
            }
            p(classes = "mt-3") {
                +context.i18n("budget.questions")
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

private fun DIV.legend(context: Context, section: String, colorClass: String) {
    div(classes = "d-flex ms-4") {
        div(classes = "mxt-chart--bullet mxt-chart--$colorClass") {}
        div(classes = "ms-2") {
            small {
                +context.i18n("budget.lines.$section.title")
            }
        }
    }
}
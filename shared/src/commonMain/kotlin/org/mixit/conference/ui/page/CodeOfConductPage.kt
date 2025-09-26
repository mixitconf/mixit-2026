package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.renderTemplate

fun renderCodeOfConduct(context: Context, event: Event, sponsors: List<Sponsor>) =
    renderTemplate(context, event) {
        sectionComponent(context)   {
            h1 {
                img(classes = "mxt-img__header-coc") {
                    alt = context.i18n("coc.title")
                }
                +context.i18n("coc.title")
            }
            p(classes = "lead") {
                +context.i18n("coc.intro")
            }
            p { +context.i18n("coc.enforcement") }
        }

        sectionComponent(context, i18nKey = "coc.quick.title") {
            p { +context.i18n("coc.quick.part1") }
            p { +context.i18n("coc.quick.part2") }
            p { +context.i18n("coc.quick.part3") }
            p { +context.i18n("coc.quick.part4") }
            p { +context.i18n("coc.quick.part5") }
        }

        sectionComponent(context, i18nKey = "coc.long.title") {
            p { +context.i18n("coc.long.part1") }
            p { +context.i18n("coc.long.part2") }
            p { +context.i18n("coc.long.part3") }
            p { +context.i18n("coc.long.part4") }
            p { +context.i18n("coc.long.part5") }
            p { +context.i18n("coc.long.part6") }
            p { +context.i18n("coc.long.part7") }
            p { +context.i18n("coc.long.part8") }

            p {
                +context.i18n("coc.credits")
                a(href = "https://confcodeofconduct.com/index-fr.html") {
                    +" confcodeofconduct.com"
                }
            }
        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

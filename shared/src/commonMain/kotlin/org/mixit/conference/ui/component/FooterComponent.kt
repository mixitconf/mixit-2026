package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.MENU
import org.mixit.conference.ui.SOCIALS

fun DIV.footerComponent(ctx: Context, event: Event?) {
    footer("bd-footer py-4 py-md-5") {
        div("container py-4 py-md-5 px-4 px-md-3") {
            div("row") {
                div("col-lg-3 mb-3") {
                    mixitSocialComponent()
                }
                MENU.forEach { menu -> renderMenu(ctx, MenuUsage.FOOTER, menu) }
            }
        }
    }
}
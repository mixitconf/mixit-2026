package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h5
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.MENU

fun DIV.lateralMenuComponent(ctx: Context) {
    div("offcanvas offcanvas-start") {
        attributes["tabindex"] = "-1"
        attributes["id"] = "offcanvasNavbar"
        attributes["aria-labelledby"] = "offcanvasNavbarLabel"
        div("offcanvas-header") {
            h5("offcanvas-title") {
                attributes["id"] = "offcanvasNavbar"
                +"Menu"
            }
            button(classes ="btn-close btn-close-white") {
                attributes["type"] = "button"
                attributes["data-bs-dismiss"] = "offcanvas"
                attributes["aria-label"] = "Close"
            }
        }
        div(classes ="offcanvas-body") {
            MENU.forEach { menu -> renderMenu(ctx, MenuUsage.HEADER, menu) }
            mixitSocialComponent()
        }
    }
}
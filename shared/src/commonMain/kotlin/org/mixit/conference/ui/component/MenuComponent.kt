package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.menu.Menu
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.MENU
import org.mixit.conference.ui.SOCIALS

enum class MenuUsage {
    HEADER, FOOTER
}

fun DIV.renderMenu(ctx: Context, menuUsage: MenuUsage, menu: Menu) {
    div(classes = if(menuUsage == MenuUsage.FOOTER) "col-6 col-lg-2 offset-lg-1 mb-3" else "row") {
        h5 {
            +ctx.i18n(menu.title)
        }
        ul(classes = "list-unstyled small") {
            menu.items.forEach { item ->
                li(classes = "mb-2") {
                    a(classes = "mxt-menu__item") {
                        href = "${ctx.uriBasePath}/${item.href}"
                        +ctx.i18n(item.title)
                    }
                }
            }
        }
    }
}
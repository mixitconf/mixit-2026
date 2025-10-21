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
    when (menuUsage) {
        MenuUsage.HEADER -> {
            div(classes = "row") {
                h4 {
                    +ctx.i18n(menu.title)
                }
                ul(classes = "list-unstyled") {
                    menu.items.forEach { item ->
                        li(classes = "mb-2") {
                            a(classes = "mxt-menu__item") {
                                href = "${ctx.forceUriBAsePath()}/${item.href}"
                                +ctx.i18n(item.title)
                            }
                        }
                    }
                }
            }
        }

        MenuUsage.FOOTER -> {
            div(classes = "col-4 col-lg-2 offset-lg-1 mb-3") {
                h5 {
                    +ctx.i18n(menu.title)
                }
                ul(classes = "list-unstyled small") {
                    menu.items.forEach { item ->
                        li(classes = "mb-2") {
                            a(classes = "mxt-menu__item") {
                                href = "${ctx.forceUriBAsePath()}/${item.href}"
                                +ctx.i18n(item.title)
                            }
                        }
                    }
                }
            }
        }
    }
}
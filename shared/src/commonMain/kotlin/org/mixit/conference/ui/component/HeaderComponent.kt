package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.ui.formatter.formatDate

fun DIV.headerComponent(ctx: Context, event: Event?) {
    nav(classes ="navbar navbar-default mxt-navbar") {
        div(classes ="container-fluid") {
            button(classes = "navbar-toggler") {
                attributes["data-bs-toggle"] = "offcanvas"
                attributes["data-bs-target"] = "#offcanvasNavbar"
                attributes["aria-controls"] = "offcanvasNavbar"
                attributes["aria-label"] = "Toggle navigation"
                img(classes = "mxt-navbar-toggler", src = "/images/svg/mxt-icon--hamburger.svg", alt = "Menu") {
                    attributes["id"] = " offcanvasNavbarLabel"
                }
            }
            lateralMenuComponent(ctx)
            div(classes ="flex-fill") { }
            div(classes ="navbar-header mxt-navbar-brand") {
                a(classes = "navbar-brand", href = "/") {
                    img(
                        classes = "navbar-brand-icon",
                        src = "/images/svg/logo/mxt-icon--logo--light.svg",
                        alt = "MiXiT logo"
                    )
                }
                div {
                    if (event != null) {
                        span { +"${event.start.formatDate(ctx.language)}, ${event.end.formatDate(ctx.language)} ${event.end.year}" }
                    }
                    span(classes ="mxt-navbar-description ps-2") { +"Lyon - FRANCE" }
                }
            }
            div(classes ="flex-fill") { }
            div(classes ="d-flex flex-column") {
                button(classes = "navbar-toggler") {
                    onClick = "javascript:switchTheme()"
                    img(classes ="mxt-navbar-mode", alt = ctx.translator.invoke("header.mode"))
                }
                div(classes ="d-flex me-2") {
                    if (ctx.language == Language.FRENCH) {
                        div(classes = "mxt-navbar-language me-2 mxt-navbar-language--active") {
                            +"FR"
                        }
                        a(href = "/en", classes = "mxt-navbar-language") {

                            +"EN"
                        }
                    } else {
                        a( href = "/fr", classes = "mxt-navbar-language me-2") {
                            +"FR"
                        }
                        div(classes = "mxt-navbar-language mxt-navbar-language--active") {
                            +"EN"
                        }
                    }
                }
            }
        }
    }
}
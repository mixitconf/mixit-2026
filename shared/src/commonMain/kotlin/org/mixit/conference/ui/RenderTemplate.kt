package org.mixit.conference.ui

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.footerComponent
import org.mixit.conference.ui.component.headerComponent

fun renderTemplate(ctx: Context, event: Event? = null, layoutContent: DIV.(Context) -> Unit = {}) = createHTML().html {
    attributes["lang"] = ctx.language.toLanguageTag()
    head {
        meta {
            httpEquiv = "Content-Type"
            content = "text/html; charset=UTF-8"
        }
        meta {
            httpEquiv = "x-ua-compatible"
            content = "ie=edge"
        }
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1.0"
        }
        meta {
            name = "mobile-web-app-capable"
            content = "yes"
        }
        meta {
            name = "application-name"
            content = "MiXiT"
        }
        meta {
            name = "apple-mobile-web-app-capable"
            content = "yes"
        }
        meta {
            name = "apple-mobile-web-app-status-bar-style"
            content = "black"
        }
        meta {
            name = "apple-mobile-web-app-title"
            content = "MiXiT"
        }
        meta {
            name = "og:title"
            content = "MiXiT"
        }
        meta {
            name = "og:type"
            content = "website"
        }
        meta {
            name = "og:url"
            content = "https://mixitconf.org/"
        }
        meta {
            name = "og:image"
            content = "https://mixitconf.org/images/png/fbpreview.png"
        }
        meta {
            name = "theme-color"
            content = "#2c233d"
        }

        link {
            rel = "icon"
            sizes = "192x192"
            href = "/images/png/mxt-favicon_chrome.png"
        }
        link {
            rel = "icon"
            sizes = "192x192"
            href = "/images/png/mxt-favicon_chrome.png"
        }
        link {
            rel = "alternate"
            hrefLang = ctx.language.toLanguageTag()
            href = "/${ctx.language.toLanguageTag()}/"
        }
        link {
            rel = "apple-touch-icon"
            href = "/images/png/mxt-favicon_apple.png"
        }
        link {
            rel = "icon"
            href = "/favicon.ico"
        }
        link {
            rel = "manifest"
            href = "/manifest.json"
        }
        title("MiXiT")
        link {
            rel = "stylesheet"
            href = "/webjars/bootstrap/css/bootstrap.min.css"
        }
        link {
            rel = "stylesheet"
            href = "/mixit.css"
        }
        script {
            type = "text/javascript"
            src = "/mixit.js"
        }
        script(type = ScriptType.textJavaScript) {
            // We try to read the usage mode choosed by the user
            unsafe {
                raw("""   
                     function switchTheme() {
                        const existing = localStorage.getItem('mode') || 'light';
                        var theme = 'light'; 
                        switch(existing) {
                            case 'dark':
                                theme = 'old'
                                break;
                            case 'old':
                                theme = 'car'
                                break;
                            case 'car':
                                theme = 'fire'
                                break;    
                            case 'light':
                                theme = 'dark'
                                break;
                            default:
                                theme = 'light';
                        }
                        document.body.setAttribute('data-theme', theme);
                        localStorage.setItem('mode', theme);
                     }
                     
                     function loadImages() {
                        const images = document.getElementsByClassName('mxt-img__lazyload');
                        Array
                            .from(images)
                            .forEach((image) => image.src = image.getAttribute('data-src'));
                    }
                    window.addEventListener("load", () => loadImages());
            """)
            }
        }
    }
    body {
        div(classes ="mxt-layout__container") {
            div(classes ="mxt-layout__header") {
                headerComponent(ctx, event)
            }
            div(classes ="mxt-layout__main") {
                div(classes ="mxt-layout__main-page") {
                    layoutContent(ctx)
                }
                div(classes="container-xxl text-center pt-5 pb-3") {
                    small {
                        +"Hosted and supported by"
                    }
                    br
                    img(src="/images/sponsors/logo-clevercloud.svg") {
                        style = "max-height: 2em"
                    }
                }
                footerComponent(ctx, event)
            }
        }
        script(type = ScriptType.textJavaScript) {
            // We try to read the usage mode choosed by the user
            unsafe {
                raw("""
             const theme = (localStorage.getItem('mode')) ? localStorage.getItem('mode') : 'light';
             document.body.setAttribute('data-theme', theme);
             localStorage.setItem('mode', theme);
            """)
            }
        }
        script {
            type = "text/javascript"
            src = "/webjars/bootstrap/js/bootstrap.bundle.min.js"
        }
    }
}
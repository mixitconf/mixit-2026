package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.img
import kotlinx.html.span
import org.mixit.conference.model.link.SocialNetwork
import org.mixit.conference.ui.SOCIALS


fun DIV.socialLink(social: SocialNetwork) {
    a(
        href = social.url,
        target = "_blank",
        classes = "mxt-social-link mxt-has-tooltip"
    ) {
        attributes["alt"] = social.title
        img(
            src = "/images/svg/social/mxt-icon--social-${social.key}.svg",
            alt = social.title,
            classes = "mxt-img__social"
        )
        span(classes = "mxt-tooltip mxt-tooltip-inverse") { +social.title }
    }
}

fun DIV.mixitSocialComponent() {
    a(classes = "navbar-brand", href = "/") {
        img(
            classes = "navbar-brand-icon",
            src = "/images/svg/logo/mxt-icon--logo--light.svg",
            alt = "MiXiT logo"
        )
    }
    div(classes = "d-flex") {
        SOCIALS.forEach {
            socialLink(it)
        }
    }
}

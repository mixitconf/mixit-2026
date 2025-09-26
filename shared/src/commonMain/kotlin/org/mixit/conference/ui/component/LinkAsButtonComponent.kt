package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.img
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.shared.Context

fun DIV.linkAsPrimaryButton(ctx: Context, link: Link) {
    a(href = link.url, classes = "btn btn-primary mxt-btn-primary", target = "_blank") {
        attributes["alt"] = link.name
        if (link.image != null) {
            img(src = link.image, alt = link.name)
        }
        +link.name
    }
}

fun DIV.linkAsSecondaryButton(ctx: Context, link: Link) {
    a(href = link.url, classes = "btn btn-secondary mxt-btn-secondary", target = "_blank") {
        attributes["alt"] = link.name
        if (link.image != null) {
            img(src = link.image, alt = link.name, classes = "me-2")
        }
        +link.name
    }
}
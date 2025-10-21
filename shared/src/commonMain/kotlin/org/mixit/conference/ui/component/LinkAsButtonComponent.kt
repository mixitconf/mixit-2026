package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.img
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.shared.Context

fun DIV.linkAsPrimaryButton(link: Link, label: String?) {
    a(href = link.url, classes = "btn btn-primary mxt-btn-primary", target = "_blank") {
        attributes["alt"] = label ?: link.name
        if (link.type.image != null) {
            img(src = link.type.image, alt = label ?:link.name, classes = "me-2")
        }
        +(label ?:link.name)
    }
}

fun DIV.linkAsSecondaryButton(link: Link, label: String? = null) {
    a(href = link.url, classes = "btn btn-secondary mxt-btn-secondary", target = "_blank") {
        attributes["alt"] = label ?:link.name
        if (link.type.image != null) {
            img(src = link.type.image, alt = label ?:link.name, classes = "me-2")
        }
        +(label ?: link.name)
    }
}
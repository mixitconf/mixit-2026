package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.div
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.YEARS


fun DIV.yearSelectorComponent(context: Context, event: Event, url: String, alt: String, years: List<Int> = YEARS) {
    div(classes = "mxt-year__selector") {
        years.forEach { year ->
            a(classes = "mxt-year__selector-link ${if (event.year == year) "mxt-year__selector-selected" else ""}") {
                attributes["alt"] = alt
                href = "${context.uriBasePath}/${year}$url"
                +year.toString()
            }
        }
    }
}
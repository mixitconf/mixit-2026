package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.onClick
import kotlinx.html.span
import kotlinx.html.title
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.formatter.formatTime

fun DIV.roomComponent(context: Context, talk: Talk, withLink: Boolean = false, withCapacity: Boolean = false, withTime: Boolean = false) {
    if(talk.event.toInt() == CURRENT_YEAR) {
        if (withLink) {
            a(classes = "mxt-room") {
                if (talk.room.hasLink) {
                    href = context.i18n("rooms.${talk.room.name.lowercase()}.link")
                    title = context.i18n("rooms.${talk.room.name.lowercase()}.link")
                    target = "_blank"
                    rel = "noopener noreferrer"
                } else {
                    href = "#"
                    onClick = "return false;"
                }
                +context.i18n("rooms.${talk.room.name.lowercase()}")
                if (withCapacity) {
                    +" - ${context.i18n("talks.seats")} ${talk.room.capacity}"
                }
                if (withTime && talk.start != null && talk.end != null) {
                    +" - ${talk.startLocalTime()!!.formatTime()}  ${context.i18n("talks.to")} ${talk.endLocalTime()!!.formatTime()}"
                }

            }
        } else {
            span(classes = "mxt-room") {
                +context.i18n("rooms.${talk.room.name.lowercase()}")
                if (withCapacity) {
                    +" - ${context.i18n("talks.seats")} ${talk.room.capacity}"
                }
            }
        }
    }
}

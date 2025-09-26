package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.renderTemplate

fun renderError(context: Context) =
    renderTemplate(context, null) {
        sectionComponent(context) {
            h1 {
                +"Oops !"
            }

        }
    }  
package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.faq.QuestionSet
import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.SectionEffect
import org.mixit.conference.ui.component.SectionStyle
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.renderTemplate

fun renderFaqPage(
    context: Context,
    event: Event,
    questionSets: List<QuestionSet>,
    sponsors: List<Sponsor>
) =
    renderTemplate(context, event) {
        val pageTitle = context.i18n("about.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-faq") {
                    alt = pageTitle
                }
                +pageTitle
            }
        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }
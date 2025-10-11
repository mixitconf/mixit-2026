package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.faq.QuestionSet
import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
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
        val pageTitle = context.i18n("faq.title")
        sectionComponent(context) {
            h1 {
                img(classes = "mxt-img__header-faq") {
                    alt = pageTitle
                }
                +pageTitle
            }
            div(classes = "lead") {
                +context.i18n("faq.intro")
            }
        }

        questionSets.forEachIndexed { index, questionSet ->
            sectionComponent(
                context,
                style = if (index % 2 == 0) SectionStyle.LIGHT else SectionStyle.TRANSPARENT,
                effect = if (index % 2 == 0) SectionEffect.BOTH else SectionEffect.NONE
            ) {
                h2 {
                    when(context.language) {
                        Language.FRENCH -> +questionSet.title.fr
                        Language.ENGLISH -> +questionSet.title.en
                    }
                }
                questionSet.questions
                    .sortedBy { it.order }
                    .forEach { question ->
                        div(classes = "mt-3") {
                            h4 {
                                when(context.language) {
                                    Language.FRENCH -> +question.title.fr
                                    Language.ENGLISH -> +question.title.en
                                }
                            }
                            div {
                                unsafe {
                                    when(context.language) {
                                        Language.FRENCH -> +question.answer.fr
                                        Language.ENGLISH -> +question.answer.en
                                    }
                                }
                            }
                        }
                    }
            }
        }
        div(classes = "mt-3") {}
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }
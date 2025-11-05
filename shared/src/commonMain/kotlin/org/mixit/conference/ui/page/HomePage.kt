package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.renderTemplate

fun renderHomePage(context: Context, event: Event, sponsors: List<Sponsor>, keynotes: List<Talk>) =
    renderTemplate(context, event) {
        sectionComponent(context, effect = SectionEffect.END, style = SectionStyle.DARK) {
            div(classes = "mxt-text__wrapper") {
                +context.i18n("home.wrapper.start")
                span(classes = "mxt-text__third") {
                    +" ${context.i18n("home.wrapper.ethic")} "
                }
                br
                +context.i18n("home.wrapper.crepe")
                span(classes = "mxt-text__secondary") {
                    +" ${context.i18n("home.wrapper.love")} "
                }
            }
        }
        +"TESTS"
        iframe {
            width = "560"
            height = "315"
            src = "https://f2498cb6.sibforms.com/serve/MUIFACCrnNpm4RYbbUu7Ym7J2MIWnA7zQ3My1xfU6_iWunSa9ChFT73CqZhw0Vw6CfaWBPv0ORut4DDSlcVZ0FkGh0VklDe1tBgK_Tp05kg-2rGDPFZ-KcQLKUltsH430wR4EBdL_-r9FGvxawI_ZeY-JmiqEd72fynJhFAhFZQqe0_mPa9_5WNK5OLC0MgWPQC_biJ4_b4qV7lC"
           attributes["frameborder"] = "0"
            attributes["scrolling"] = "auto"
            attributes["allowfullscreen"] = "true"
            style = "display: block;margin-left: auto;margin-right: auto;max-width: 100%;"
        }
        sectionComponent(context, i18nKey = "home.section.news.title") {
            div("lead") { +context.i18n("home.section.news.when") }
            div("mt-2") {
                b { +context.i18n("home.section.news.birthday") }
                +context.i18n("home.section.news.birthday2")
            }
            timeline(context)
        }
        sectionComponent(
            context,
            i18nKey = "home.section.onair.title",
            effect = SectionEffect.BOTH,
            style = SectionStyle.LIGHT
        ) {
            div("lead") { +context.i18n("home.section.onair.subtitle") }
            div("mt-2") { +context.i18n("home.section.onair.description") }
            podcastComponent()
            div { +context.i18n("home.section.onair.joinus") }
            podcastLinks()
        }
        sectionComponent(context, i18nKey = "home.section.values.title") {
            renderValueBlock(context, "accessibility")
            renderValueBlock(context, "safe")
            renderValueBlock(context, "eco")
        }
        sectionComponent(
            context,
            i18nKey = "home.section.editorialline.title",
            effect = SectionEffect.BOTH,
            style = SectionStyle.LIGHT
        ) {
            div("lead") { +context.i18n("home.section.editorialline.description") }
            div("mt-2") { +context.i18n("home.section.editorialline.format") }
            div("mt-2") { +context.i18n("home.section.editorialline.themes") }
            div("mt-2 row justify-content-center") {
                listOf(Topic.ALIENS, Topic.TECH, Topic.ETHICS, Topic.TEAM).forEach { topic ->
                    div("col-12 col-md-6 col-lg-3 pt-2") {
                        renderHomeTopic(context, topic)
                    }
                }

                div(classes = "mxt-video__multi-container") {
                    keynotes.forEach {
                        videoComponent(context, it)
                    }
                }
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

fun DIV.renderHomeTopic(context: Context, topic: Topic) {
    div("row align-items-center") {
        div("col-4 col-md-3 col-lg-3 text-center") {
            img(classes = "mxt-img__topic-home") {
                src = "/images/svg/topic/mxt-icon--${topic.value.lowercase()}.svg"
                alt = context.i18n("topics.${topic.value.lowercase()}.title")
            }
        }
        div("col-8 col-md-9 col-lg-9") {
            h3 { +context.i18n("topics.${topic.value.lowercase()}.title") }
            div { +context.i18n("topics.${topic.value.lowercase()}.description") }
        }
    }
}

fun DIV.renderValueBlock(context: Context, category: String) {
    div(classes = "d-flex align-items-center pt-3") {
        div {
            img(classes = "mxt-icon__category-$category") {
                alt = context.i18n("home.section.values.$category.title")
            }
        }
        div {
            h3 { +context.i18n("home.section.values.$category.title") }
        }
    }
    div {
        +context.i18n("home.section.values.$category.desc")
    }
}

fun DIV.timeline(context: Context) {
    div(classes = "d-flex justify-content-center gap-4 mt-5") {
        timelineElement(context, "home.timeline.cfp.open", "mxt-timeline--cfp-open")
        timelineElement(context, "home.timeline.cfp.close", "mxt-timeline--cfp-close")
        timelineElement(context, "home.timeline.tickets.sale", "mxt-timeline--ticketing-open")
    }

    div(classes = "mt-5 mb-5") {
        div(classes = "d-flex align-items-center mt-3") {
            div {
                img(classes = "mxt-img__header-cfp-home") {
                    alt = "CFP"
                }
            }
            div(classes = "mxt-no-link") {
                unsafe {
                    raw(
                        context.markdown(context.i18n("home.section.news.cfp"))
                    )
                }
            }
        }
        div(classes = "d-flex align-items-center") {
            div {
                img(classes = "mxt-img__header-sponsor-home") {
                    alt = "Sponsor"
                }
            }
            div(classes = "mxt-no-link") {
                unsafe {
                    raw(
                        context.markdown(context.i18n("home.section.news.sponsor"))
                    )
                }
            }
        }
    }

}

fun DIV.timelineElement(context: Context, titleKey: String, className: String) {
    div(classes = "d-flex flex-column align-items-center") {
        img(classes = className) {
            alt = context.i18n(titleKey)
        }
        div {
            b {
                +context.i18n(titleKey)
            }
        }
    }
}

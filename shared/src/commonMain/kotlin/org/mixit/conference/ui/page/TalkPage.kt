package org.mixit.conference.ui.page

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.html.ButtonType
import kotlinx.html.DIV
import kotlinx.html.FORM
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.ScriptType
import kotlinx.html.a
import kotlinx.html.b
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h4
import kotlinx.html.i
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.input
import kotlinx.html.li
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.small
import kotlinx.html.span
import kotlinx.html.textArea
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.feedback.Feedback
import org.mixit.conference.model.feedback.TalkFeedback
import org.mixit.conference.model.feedback.UserTalkFeedback
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.SectionEffect
import org.mixit.conference.ui.component.SectionStyle
import org.mixit.conference.ui.component.languageComponent
import org.mixit.conference.ui.component.roomComponent
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsorGroupComponent
import org.mixit.conference.ui.component.topicComponent
import org.mixit.conference.ui.component.videoComponent
import org.mixit.conference.ui.renderTemplate

fun renderTalk(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    talk: Talk,
    isFavorite: Boolean,
    isATalkSpeakerOrAdmin: Boolean,
    displayFeedback: Boolean,
    talkFeedback: TalkFeedback?,
    userTalkFeedback: UserTalkFeedback?,
) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = talk.title
            h2 {
                img(classes = "mxt-img__header-talk") {
                    alt = pageTitle
                }
                +context.i18n("talk.format.${talk.format.name}")
            }
            h1 {
                unsafe {
                    raw(context.markdown(talk.title))
                }
            }
            div(classes = "d-flex align-items-center mb-4") {
                div(classes = "flex-fill") {
                    div(classes = "d-flex align-items-center mb-4") {
                        languageComponent(context, talk)
                        if (talk.videos.isNotEmpty()) {
                            img(classes = "mxt-img__header-video ms-2") {
                                attributes["aria-label"] = context.i18n("talks.video.available")
                                title = context.i18n("talks.video.available")
                            }
                        }
                        roomComponent(context, talk, withCapacity = true, withLink = true, withTime = true)

                    }
                }
                div {
                    if (context.email != null) {
                        form {
                            method = FormMethod.post
                            action = "/talks/${talk.id}/favorite"

                            button {
                                classes = setOf("mxt-btn-primary")
                                if (isFavorite) {
                                    attributes["aria-label"] = context.i18n("favorite.selected")
                                    img(src = "/images/svg/favorites/mxt-favorite-dark.svg") {
                                        alt = context.i18n("favorite.selected")
                                    }
                                    span(classes = "ms-2") {
                                        +context.i18n("favorite.selected")
                                    }
                                } else {
                                    attributes["aria-label"] = context.i18n("favorite.nonselected")
                                    img(src = "/images/svg/favorites/mxt-favorite-non-dark.svg") {
                                        alt = context.i18n("favorite.nonselected")
                                    }
                                    span(classes = "ms-2") {
                                        +context.i18n("favorite.nonselected")
                                    }
                                }
                            }
                        }
                    } else {
                        a(href = "${context.uriBasePath}/login", classes = "mxt-btn-primary") {
                            attributes["aria-label"] = context.i18n("favorite.connected")
                            img(src = "/images/svg/favorites/mxt-favorite-non-dark.svg") {
                                alt = context.i18n("favorite.nonselected")
                            }
                            span(classes = "ms-2") {
                                +context.i18n("favorite.nonselected")
                            }
                        }
                    }

                }
            }

            topicComponent(context, talk.topic)


            div(classes = "mt-3 mb-3") {
                unsafe {
                    raw(context.markdown(talk.summary))
                }
                unsafe {
                    raw(context.markdown(talk.description))
                }
            }
        }

        sectionComponent(context) {
            p(classes = "mb-4") {
                +" "
            }
            talk.speakers.forEach { speaker ->
                sectionComponent(context) {
                    div(classes = "d-flex align-items-center mb-4") {
                        img(classes = "mxt-speaker__img me-2") {
                            src = speaker.photoUrl ?: DEFAULT_IMG_URL
                            attributes["aria-label"] = "${speaker.firstname} ${speaker.lastname}"
                        }
                        div {
                            h4 {

                                +"${speaker.firstname} ${speaker.lastname}"
                            }
                            p {
                                unsafe {
                                    raw(
                                        context.markdown(
                                            speaker.description[Language.FRENCH]
                                                ?: speaker.description[Language.ENGLISH].orEmpty()
                                        )
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
        sectionComponent(context) {
            id = "feedbacks"
            if (displayFeedback) {
                h2 {
                    +context.i18n("feedback.title")
                }

                if (Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Paris")).date < event.start) {
                    p {
                        +context.i18n("feedback.preconf")
                    }
                }

                if (context.isAuthenticated) {
                    p {
                        +context.i18n("feedback.description")
                        i(classes = "ms-2") {
                            +talk.title
                        }
                    }
                    form(action = "/talks/${talk.id}/feedback", method = FormMethod.post) {
                        feedbackTable(context, talk, talkFeedback, userTalkFeedback)
                        div(classes = "mt-3") {
                            b {
                                +context.i18n("feedback.comment")
                            }
                        }
                        textArea(classes = "form-control", rows = "2") {
                            id = "comment"
                            name = "comment"
                            +(userTalkFeedback?.comment ?: "")
                        }
                        button(classes = "btn mxt-btn-primary mt-3 mb-4") {
                            +context.i18n("feedback.action")
                        }
                    }

                } else {
                    p {
                        +context.i18n("feedback.anonymous")
                    }
                    a(classes = "btn mxt-btn-primary mb-5") {
                        href = "${context.uriBasePath}/login"
                        +context.i18n("login.action.log")
                    }
                    feedbackTable(context, talk, talkFeedback)
                }

                if (isATalkSpeakerOrAdmin) {
                    sectionComponent(context) {

                        if (talkFeedback == null || talkFeedback.comments.isEmpty()) {
                            div {
                                b {
                                    +context.i18n("feedback.none.comments")
                                }
                            }
                        } else {
                            div {
                                b {
                                    +context.i18n("feedback.your.comments")
                                }
                            }
                            ul {
                                talkFeedback.comments.forEach { comment ->
                                    li {
                                        +comment
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        sectionComponent(context) {
            if (talk.videos.isNotEmpty()) {
                videoComponent(talk.videos.first())
            }
        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }

fun DIV.feedbackTable(context: Context, talk: Talk, talkFeedback: TalkFeedback?) {
    div(classes = "mxt-feedback__container mb-4") {
        Feedback.forFormat(talk.format).forEach {
            val votes = talkFeedback?.state?.get(it) ?: 0
            div(classes = "mxt-feedback ") {
                id = "feedback-container-${it.name}"
                a(classes = "mxt-feedback-button mxt-feedback-link") {
                    href = "${context.uriBasePath}/login"
                    id = "feedback-text-${it.name}"
                    div(classes = "mxt-feedback-link ") { +context.i18n("feedback.${it.name}") }
                    div {
                        small(classes = "mxt-feedback-link ") {
                            +"$votes vote${if (votes > 1) "s" else ""}"
                        }
                    }
                }
            }
        }

    }
}

fun FORM.feedbackTable(context: Context, talk: Talk, talkFeedback: TalkFeedback?, userTalkFeedback: UserTalkFeedback?) {
    div(classes = "mxt-feedback__container") {
        script(type = ScriptType.textJavaScript) {
            // We try to read the usage mode choosed by the user
            unsafe {
                raw(
                    """   
                                     function voteFeedback(feedback) {
                                        const container = document.getElementById("feedback-container-"+feedback);
                                        const input = document.getElementById("feedback-"+feedback);
                                        const initialValue = parseInt(input.getAttribute("initial-value"));
                                        const userInput = document.getElementById("feedback-user-"+feedback);
                                        if(userInput.value === "1" || parseInt(userInput.value) > 0) {
                                            input.value = (parseInt(input.value) - 1).toString();
                                            userInput.value = "0";
                                            container.classList.remove("mxt-feedback-active");
                                        } else {
                                            input.value = (parseInt(input.value) + 1).toString();
                                            userInput.value = "1";
                                            container.classList.add("mxt-feedback-active");
                                        }
                                        const feedbackText = document.getElementById("feedback-text-"+feedback);
                                        feedbackText.innerHTML = "<small>" + input.value + " vote" + (input.value > 1 ? "s" : "") + "</small>";
                                        // Call backend to save the feedback
                                        fetch(`/api/talks/${talk.id}/${"$"}{feedback}/` + userInput.value, {method: 'post'})
                                            .then(() => console.log("Feedback saved"));
                                     }
                                    """
                )
            }
        }
        Feedback.forFormat(talk.format).forEach {
            val votes = talkFeedback?.state?.get(it) ?: 0
            val userVote = if (userTalkFeedback?.notes?.contains(it) ?: false) 1 else 0

            div(classes = "mxt-feedback " + if (userVote == 1) "mxt-feedback-active" else "") {
                id = "feedback-container-${it.name}"
                input(classes = "mxt-feedback-input") {
                    type = InputType.hidden
                    name = "feedback-${it.name}"
                    id = "feedback-${it.name}"
                    value = votes.toString()
                    attributes["initial-value"] = votes.toString()
                }
                input {
                    readonly = true
                    type = InputType.hidden
                    id = "feedback-user-${it.name}"
                    value = userVote.toString()
                }
                button(classes = "mxt-feedback-button") {
                    type = ButtonType.button
                    onClick = "javascript:voteFeedback('${it.name}')"
                    +context.i18n("feedback.${it.name}")
                    div {
                        id = "feedback-text-${it.name}"
                        small {
                            +"$votes vote${if (votes > 1) "s" else ""}"
                        }
                    }
                }
            }
        }

    }
}

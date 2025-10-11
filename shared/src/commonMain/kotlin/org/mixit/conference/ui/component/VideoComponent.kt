package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.model.link.Video
import org.mixit.conference.model.link.VideoType
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk

fun DIV.videoComponent(context: Context, talk: Talk, withTopic: Boolean = true) {
    div {
        if(!withTopic) {
            style = "flex-grow: 1;flex-basis: 0;min-width: 20em;"
        }
        div(classes = "mxt-video__container") {
            div(classes = "mxt-video__container-body") {
                div(classes = "mxt-video__container-image") {
                    div(classes = "mxt-video__container-header mb-2") {
                        topicComponent(context, talk.topic, talk.event.toInt())
                    }
                    a(href = "${context.uriBasePath}/${talk.event}/${talk.slug}", classes = "mxt-video__player-title") {
                        attributes["aria-label"] = talk.title
                        +talk.title
                    }
                    a(href = "${context.uriBasePath}/${talk.event}/${talk.slug}", classes = "mxt-video__player-title") {
                        attributes["aria-label"] = talk.title
                        img(classes = "mxt-img__lazyload", alt = "Video") {
                            attributes["data-src"] = "/images/svg/mxt-icon-video-hover.svg"
                            style = "max-width: 2.5em;"
                        }
                    }
                }
            }
        }
//        div(classes = "mxt-video__container") {
//            if(withTopic) {
//                div(classes = "mxt-video__container-header") {
//                    topicComponent(context, talk.topic, talk.event.toInt())
//                }
//            }
//
//            div(classes = "mxt-video__container-body") {
//                div("mxt-video__player-container") {
//                    val video = talk.videos.first()
//                    videoComponent(video)
//                }
//                a(href = "${context.uriBasePath}/${talk.event}/${talk.slug}", classes = "mxt-video__player-title") {
//                    attributes["aria-label"] = talk.title
//                    +talk.title
//                }
//                speakersComponentInDiv(context, talk.speakers, isSmall = !withTopic)
//            }
//        }
    }
}

fun DIV.videoComponent(video: Video) {
    div("mxt-video__player-container") {
        when (video.type) {
            VideoType.VIMEO -> {
                iframe(classes = "mxt-video__player mxt-img__lazyload") {
                    attributes["data-src"] = video.playerUrl!!
                    attributes["allowfullscreen"] = "true"
                }
            }

            VideoType.YOUTUBE -> {
                iframe(classes = "mxt-video__player mxt-img__lazyload") {
                    attributes["data-src"] = video.playerUrl!!
                    attributes["allowfullscreen"] = "true"
                    attributes["accelerometer"] = "true"
                    attributes["autoplay"] = "true"
                    attributes["clipboard-write"] = "true"
                    attributes["encrypted-media"] = "true"
                    attributes["gyroscope"] = "true"
                    attributes["picture-in-picture"] = "true"
                    attributes["web-share"] = "true"
                }
            }

            else -> {
                a(href = video.playerUrl, target = "_blank") {
                    img(classes = "mxt-img__lazyload") {
                        attributes["data-src"] = "/images/svg/mxt-no-player.svg"
                        alt = "No video player"
                    }
                }
            }
        }
    }
}
package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.ui.PODCASTS

fun DIV.podcastComponent(lastPodCastId: String) {
    div(classes = "mxt-podcast__container") {
        iframe {
            src="https://player.ausha.co/?podcastId=$lastPodCastId&v=3&playerId=ausha-tvtg"
            attributes["name"] = "Ausha Podcast Player"
            attributes["frameborder"] = "0"
            attributes["loading"] = "lazy"
            attributes["id"] = "ausha-tvtg"
            attributes["height"] = "220"
            attributes["style"] = "border: none; width:100%; height:220px"
        }
        script {
            type = "text/javascript"
            src = "https://player.ausha.co/ausha-player.js"
        }
    }
}

fun DIV.podcastLinks() {
    div(classes = "d-flex justify-content-center") {
        PODCASTS.forEach { podcast ->
            div {
                a(
                    href = podcast.url,
                    target = "_blank",
                    classes = "mxt-has-tooltip"
                ) {
                    attributes["alt"] = podcast.title
                    img(
                        src = "/images/svg/podcast/mxt-icon_${podcast.key}.svg",
                        alt = podcast.title,
                        classes = "mxt-img__podcast"
                    )
                    span(classes = "mxt-tooltip") { +podcast.title }
                }
            }
        }
    }
}

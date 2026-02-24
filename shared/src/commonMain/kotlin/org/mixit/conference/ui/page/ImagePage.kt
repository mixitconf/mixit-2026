package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.picture.Album
import org.mixit.conference.model.picture.AlbumSection
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.model.talk.TalkFormat
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.renderTemplate

fun renderImage(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    albumUrl: String,
    sectionId: String,
    image: String,
    previousImage: String?,
    nextImage: String?,

) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = context.i18n("talks.media")
            h1 {
                img(classes = "mxt-img__header-video me-2") {
                    alt = pageTitle
                }
                +pageTitle
            }

            // Add navigation for previous and next image
            div(classes = "d-flex justify-content-between mb-5") {
                if (previousImage != null) {
                    a(href = previousImage.substringBeforeLast('.'), classes = "btn btn-custom") {
                        +context.i18n("talks.previous")
                    }
                } else {
                    span {}
                }
                if (nextImage != null) {
                    a(href = nextImage.substringBeforeLast('.'), classes = "btn btn-custom") {
                        +context.i18n("talks.next")
                    }
                } else {
                    span {}
                }
            }

            div(classes = "text-center mb-5") {
                img(src = "${albumUrl}${event.year}/${sectionId}/$image", alt = image, classes = "img-fluid")
            }
        }

        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }
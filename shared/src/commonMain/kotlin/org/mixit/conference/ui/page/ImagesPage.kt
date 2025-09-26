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

fun renderImages(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    albumUrl: String,
    section: AlbumSection
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

            h2 {
                +context.i18n(section.i18nKey)
            }
            if (section.photos.isNotEmpty()) {
                div(classes = "mxt-video__container") {
                    pictureAlbumSectionComponent(
                        context,
                        event,
                        albumUrl,
                        section
                    )
                }
            }

        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }
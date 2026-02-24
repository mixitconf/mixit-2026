package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.img
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.picture.Album
import org.mixit.conference.model.picture.AlbumSection
import org.mixit.conference.model.shared.Context


fun DIV.pictureAlbumComponent(context: Context, event: Event, album: Album) {
    div(classes = "mxt-year__selector") {
        album.sections.forEach {
            val name = it.photos.first().name
            a(classes = "mxt-img__card d-flex flex-column align-items-center m-2") {
                href = "${context.uriBasePath}/${event.year}/medias/images/${it.sectionId}"
                img(classes = "mxt-img__lazyload") {
                    attributes["data-src"] = "${album.url}${event.year}/${it.sectionId}/${name}"
                    alt = context.i18n(it.i18nKey)
                }
                div {
                    +context.i18n(it.i18nKey)
                }
            }
        }
    }
}

fun DIV.pictureAlbumSectionComponent(context: Context, event: Event, albumUrl: String, albumSection: AlbumSection) {
    div(classes = "mxt-image__multi-container") {
        albumSection.photos.forEach {
            // name contains the filename with extension. We remove this extension
            val imagePageUrl = it.name.substringBeforeLast('.')
            a(classes = "mxt-img__card-multi d-flex flex-column align-items-center m-2") {
                href = "${context.uriBasePath}/${event.year}/medias/images/${albumSection.sectionId}/$imagePageUrl"
                img(classes = "mxt-img__lazyload") {
                    attributes["data-src"] = "${albumUrl}${event.year}/${albumSection.sectionId}/${it.name}"
                    alt = "Photo ${it.name} from ${context.i18n(albumSection.i18nKey)}"
                }
            }
        }
    }
}
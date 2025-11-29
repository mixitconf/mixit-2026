package org.mixit.conference.media.handler

import org.mixit.WebContext
import org.mixit.conference.event.spi.EventRepository
import org.mixit.conference.media.api.MediaHandlerApi
import org.mixit.conference.media.spi.PhotoRepository
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.TalkFormat
import org.mixit.conference.people.spi.PeopleRepository
import org.mixit.conference.talk.spi.TalkRepository
import org.mixit.conference.ui.page.renderImage
import org.mixit.conference.ui.page.renderImages
import org.mixit.conference.ui.page.renderMedia
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class MediaHandler(
    private val talkRepository: TalkRepository,
    private val peopleRepository: PeopleRepository,
    private val eventRepository: EventRepository,
    private val photoRepository: PhotoRepository,
    private val webContext: WebContext
) : MediaHandlerApi {

    override fun findMediaByYear(year: Int, searchText: String?): ServerResponse {
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val album = photoRepository.findPhotoAlbum(year)
        val talks = talkRepository
            .findByYear(year)
            .filter { it.search(searchText) && it.format != TalkFormat.ON_AIR && it.videos.isNotEmpty() }
            .groupBy { it.format }

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderMedia(
                context = webContext.context ?: Context.default(),
                event = event,
                sponsors = peopleRepository.findSponsorByYear(year),
                photoAlbum = album,
                talksWithVideo = talks
            )
        )

    }

    override fun findMediaByYearAndSection(
        year: Int,
        sectionId: String
    ): ServerResponse {
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val album = photoRepository.findPhotoAlbum(year) ?: return ServerResponse.notFound().build()
        val section = album.sections.firstOrNull { it.sectionId == sectionId }
            ?: return ServerResponse.notFound().build()

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderImages(
                context = webContext.context ?: Context.default(),
                event = event,
                sponsors = peopleRepository.findSponsorByYear(year),
                albumUrl = album.url,
                section = section
            )
        )
    }

    override fun findPhoto(
        year: Int,
        sectionId: String,
        name: String
    ): ServerResponse {
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val album = photoRepository.findPhotoAlbum(year) ?: return ServerResponse.notFound().build()
        val section = album.sections.firstOrNull { it.sectionId == sectionId }
            ?: return ServerResponse.notFound().build()
        val index = section.photos.indexOfFirst { it.name == name }

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderImage(
                context = webContext.context ?: Context.default(),
                event = event,
                sponsors = peopleRepository.findSponsorByYear(year),
                albumUrl = album.url,
                sectionId = sectionId,
                image = name,
                previousImage = section.photos.getOrNull(if (index <= 0) section.photos.size - 1 else index - 1)?.name,
                nextImage = section.photos.getOrNull(if (index >= section.photos.size - 1) 0 else index + 1)?.name,
            )
        )
    }

}
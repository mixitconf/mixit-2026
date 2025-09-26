package org.mixit.storage.domain.photo

import org.mixit.Constants
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.picture.Album
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.storage.domain.Cache
import org.mixit.storage.domain.Cryptographer
import org.mixit.storage.domain.event.LinkDto
import org.mixit.storage.domain.people.PersonDto
import org.mixit.storage.domain.talk.TalkDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class PhotoStaticFileRepository {
    private val _data: MutableMap<Int, Album> = mutableMapOf()

    init {
        (2012..CURRENT_YEAR).filterNot { it == 2020 || it == 2021}.forEach { year ->
            val path = Path.of(ClassPathResource("data/events_image_$year.json").url.path)
            val json = Files.readString(path)
            val albumDtos = Constants.serializer.decodeFromString< Array<AlbumDto>>(json)
            _data[year] = albumDtos[0].toAlbum()
        }
    }

    @Cacheable(Cache.PHOTO_CACHE)
    fun findAll(): Map<Int, Album> {
        return _data
    }
}

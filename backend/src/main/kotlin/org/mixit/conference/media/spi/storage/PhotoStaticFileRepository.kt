package org.mixit.conference.media.spi.storage

import org.mixit.Constants
import org.mixit.conference.model.picture.Album
import org.mixit.conference.ui.CURRENT_MEDIA_YEAR
import org.mixit.util.cache.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class PhotoStaticFileRepository {
    private val _data: MutableMap<Int, Album> = mutableMapOf()

    init {
        (2012..CURRENT_MEDIA_YEAR).filterNot { it == 2020 || it == 2021 }.forEach { year ->
            val path = Path.of(ClassPathResource("data/events_image_$year.json").url.path)
            val json = Files.readString(path)
            val albumDtos = Constants.serializer.decodeFromString<Array<AlbumDto>>(json)
            _data[year] = albumDtos[0].toAlbum()
        }
    }

    @Cacheable(Cache.PHOTO_CACHE)
    fun findAll(): Map<Int, Album> {
        return _data
    }
}

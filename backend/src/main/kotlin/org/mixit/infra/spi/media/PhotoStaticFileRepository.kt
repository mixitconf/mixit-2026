package org.mixit.infra.spi.media

import jakarta.annotation.PostConstruct
import org.mixit.conference.model.picture.Album
import org.mixit.conference.ui.CURRENT_MEDIA_YEAR
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.spi.DataService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PhotoStaticFileRepository(
    private val dataService: DataService
) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableMap<Int, Album> = mutableMapOf()

    @PostConstruct
    fun init() {
        (2012..CURRENT_MEDIA_YEAR).filterNot { it == 2020 || it == 2021 }.forEach { year ->
            val images  = dataService.load(
                localPath = "data/events_image_$year.json",
                remotePath = "/images/$year",
                responseType = Array<AlbumDto>::class.java,
            )
            if(images.isNotEmpty()){
                _data[year] = images[0].toAlbum()
            }
        }
    }

    @Cacheable(Cache.PHOTO_CACHE)
    fun findAll(): Map<Int, Album> = _data
}

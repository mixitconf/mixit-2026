package org.mixit.conference.media.spi.storage

import org.mixit.conference.media.spi.PhotoRepository
import org.mixit.conference.model.picture.Album
import org.springframework.stereotype.Component

@Component
class PhotoFileRepository(
    private val repository: PhotoStaticFileRepository,
) : PhotoRepository {
    override fun findPhotoAlbum(year: Int): Album? = repository.findAll()[year]
}

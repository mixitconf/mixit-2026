package org.mixit.infra.spi.media

import org.mixit.conference.model.picture.Album
import org.mixit.domain.spi.PhotoRepository
import org.springframework.stereotype.Component

@Component
class PhotoFileRepository(
    private val repository: PhotoStaticFileRepository,
) : PhotoRepository {
    override fun findPhotoAlbum(year: Int): Album? = repository.findAll()[year]
}

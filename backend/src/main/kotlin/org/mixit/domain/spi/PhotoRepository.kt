package org.mixit.domain.spi

import org.mixit.conference.model.picture.Album

interface PhotoRepository {
    fun findPhotoAlbum(year: Int): Album?
}
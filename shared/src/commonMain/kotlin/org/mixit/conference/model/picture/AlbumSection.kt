package org.mixit.conference.model.picture

data class AlbumSection (
    val sectionId: String,
    val i18nKey: String,
    val photos: List<Photo>
)
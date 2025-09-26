package org.mixit.conference.model.picture

data class Album (
    val event: String,
    val sections: List<AlbumSection>,
    val url: String
)
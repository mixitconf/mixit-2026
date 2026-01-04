package org.mixit.infra.spi.media

import kotlinx.serialization.Serializable
import org.mixit.conference.model.picture.Album
import org.mixit.conference.model.picture.AlbumSection
import org.mixit.conference.model.picture.Photo

@Serializable
data class AlbumDto(
    val event: String,
    val sections: List<AlbumSectionDto> = emptyList(),
    val rootUrl: String?,
) {
    fun toAlbum() =
        Album(
            event = event,
            url = rootUrl ?: "https://raw.githubusercontent.com/mixitconf/mixitconf-images/main/",
            sections = sections.map { it.toAlbumSection() },
        )
}

@Serializable
data class AlbumSectionDto(
    val sectionId: String,
    val i18n: String,
    val images: List<PhotoDto>,
) {
    fun toAlbumSection() =
        AlbumSection(
            sectionId = sectionId,
            i18nKey = i18n,
            photos =
                images.map {
                    Photo(
                        name = it.name,
                    )
                },
        )
}

@Serializable
data class PhotoDto(
    val name: String,
    val talkId: String? = null,
    val mustacheTemplate: String? = null,
)

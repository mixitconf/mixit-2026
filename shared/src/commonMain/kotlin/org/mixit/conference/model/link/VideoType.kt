package org.mixit.conference.model.link

enum class VideoType(val hasPlayer: Boolean) {
    YOUTUBE(true), VIMEO(true), INFOQ(false), UNKNOWN(false);

    companion object {
        fun fromString(url: String): VideoType =
            entries.firstOrNull { url.contains(it.name.lowercase()) } ?: UNKNOWN
    }
}
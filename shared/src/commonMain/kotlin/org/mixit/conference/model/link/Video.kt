package org.mixit.conference.model.link


class Video(videoUrl: String) {
    val type: VideoType = VideoType.Companion.fromString(videoUrl)

    val playerUrl: String? =
        when(type) {
            VideoType.YOUTUBE -> if (videoUrl.startsWith("https://www.youtube.com/watch?v="))
                videoUrl.replace("https://www.youtube.com/watch?v=", "https://www.youtube.com/embed/") else videoUrl
            VideoType.VIMEO -> if (videoUrl.startsWith("https://vimeo.com/"))
                videoUrl.replace("https://vimeo.com/", "https://player.vimeo.com/video/") else videoUrl
            else -> null
        }

}
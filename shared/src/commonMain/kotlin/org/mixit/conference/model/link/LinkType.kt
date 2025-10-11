package org.mixit.conference.model.link


enum class LinkType(val image: String?, val value: String) {
    CUSTOM("/images/svg/social/mxt-icon--social-link-solid.svg", ""),
    BLUESKY("/images/svg/social/mxt-icon--social-bsky.svg", "bluesky"),
    DISCORD("/images/svg/social/mxt-icon--social-discord.svg", "discord"),
    FACEBOOK("/images/svg/social/mxt-icon--social-facebook.svg", "facebook"),
    GITHUB("/images/svg/social/mxt-icon--social-github.svg", "github"),
    INSTAGRAM("/images/svg/social/mxt-icon--social-instagram.svg", "instagram"),
    LINKEDIN("/images/svg/social/mxt-icon--social-linkedin.svg", "linkedin"),
    MASTODON("/images/svg/social/mxt-icon--social-masto.svg", "mastodon"),
    MEDIUM("/images/svg/social/mxt-icon--social-medium.svg", "medium"),
    PERSONAL("/images/svg/social/mxt-icon--social-link-solid.svg", "personal"),
    PINTEREST("/images/svg/social/mxt-icon--social-pinterest.svg", "pinterest"),
    REDDIT("/images/svg/social/mxt-icon--social-reddit.svg", "reddit"),
    SNAPCHAT("/images/svg/social/mxt-icon--social-snapchat.svg", "snapchat"),
    TIKTOK("/images/svg/social/mxt-icon--social-tiktok.svg", "tiktok"),
    TWITCH("/images/svg/social/mxt-icon--social-twitch.svg", "twitch"),
    VIDEO(null, "video"),
    PHOTO(null, "photo"),
    WHATSAPP("/images/svg/social/mxt-icon--social-whatsapp.svg", "whatsapp"),
    YOUTUBE("/images/svg/social/mxt-icon--social-youtube.svg", "youtube");

    companion object {
        fun fromValue(name: String): LinkType =
            if (name.contains("github", true)) GITHUB
            else if (name.contains("linkedin", true)) LINKEDIN
            else if (name.contains("sky", true)) BLUESKY
            else if (name.contains("facebook", true)) FACEBOOK
            else if (name.contains("instagram", true)) INSTAGRAM
            else if (name.contains("youtube", true)) YOUTUBE
            else if (name.contains("medium", true)) MEDIUM
            else if (name.contains("twitch", true)) TWITCH
            else if (name.contains("bsky", true))BLUESKY
            else if (name.contains("bluesky", true)) BLUESKY
            else if (name.contains("tiktok", true)) TIKTOK
            else if (name.contains("discord", true)) DISCORD
            else if (name.contains("snapchat", true)) SNAPCHAT
            else if (name.contains("whatsapp", true)) WHATSAPP
            else if (name.contains("pinterest", true)) PINTEREST
            else if (name.contains("tumblr", true)) CUSTOM
            else if (name.contains("reddit", true)) REDDIT
            else CUSTOM
    }
}
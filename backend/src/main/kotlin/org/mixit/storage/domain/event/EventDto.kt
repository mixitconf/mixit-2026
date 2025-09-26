package org.mixit.storage.domain.event

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.event.EventMedia
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.people.SponsorshipLevel

@Serializable
data class EventDto(
    val id: String = "",
    val start: LocalDate,
    val end: LocalDate,
    val current: Boolean = false,
    val sponsors: List<EventSponsoringDto> = emptyList(),
    val organizations: List<EventOrganizationDto> = emptyList(),
    val volunteers: List<EventVolunteerDto> = emptyList(),
    val organizers: List<EventOrganizerDto> = emptyList(),
    val photoUrls: List<LinkDto> = emptyList(),
    val videoUrl: LinkDto? = null,
    val schedulingFileUrl: String? = null,
    val year: Int = start.year,
    val streamingUrl: String? = null
) {
    fun toEvent() =
        Event(
            id = id,
            start = start,
            end = end,
            year = year,
            media = EventMedia(
                photoUrls = photoUrls.map {
                    Link(name = it.name, url = it.url, image = null)
                },
                videoUrl = videoUrl?.let {
                    Link(name = it.name, url = it.url, image = null)
                },
                schedulingFileUrl = schedulingFileUrl,
                streamingUrl = streamingUrl
            )
        )
}

@Serializable
data class EventOrganizationDto(
    val organizationLogin: String
)

@Serializable
data class EventSponsoringDto(
    val level: SponsorshipLevel = SponsorshipLevel.NONE,
    val sponsorId: String = "",
    val subscriptionDate: LocalDate
)

@Serializable
data class EventVolunteerDto(
    val volunteerLogin: String
)

@Serializable
data class EventOrganizerDto(
    val organizerLogin: String
)

@Serializable
data class LinkDto(
    val name: String,
    val url: String,
    val image: String = "/images/svg/social/" +
        if (name.contains("github", true)) "mxt-icon--social-github.svg"
        else if (name.contains("linkedin", true)) "mxt-icon--social-linkedin.svg"
        else if (name.contains("sky", true)) "mxt-icon--social-bsky.svg"
        else if (name.contains("facebook", true)) "mxt-icon--social-facebook.svg"
        else if (name.contains("instagram", true)) "mxt-icon--social-instagram.svg"
        else if (name.contains("youtube", true)) "mxt-icon--social-youtube.svg"
        else if (name.contains("medium", true)) "mxt-icon--social-medium.svg"
        else if (name.contains("twitch", true)) "mxt-icon--social-twitch.svg"
        else if (name.contains("bsky", true)) "mxt-icon--social-bsky.svg"
        else if (name.contains("bluesky", true)) "mxt-icon--social-bsky.svg"
        else if (name.contains("tiktok", true)) "mxt-icon--social-tiktok.svg"
        else if (name.contains("discord", true)) "mxt-icon--social-discord.svg"
        else if (name.contains("snapchat", true)) "mxt-icon--social-snapchat.svg"
        else if (name.contains("whatsapp", true)) "mxt-icon--social-whatsapp.svg"
        else if (name.contains("pinterest", true)) "mxt-icon--social-pinterest.svg"
        else if (name.contains("tumblr", true)) "mxt-icon--social-tumblr.svg"
        else if (name.contains("reddit", true)) "mxt-icon--social-reddit.svg"
        else "mxt-icon--social-link-solid.svg"
) {
companion object {
    val excludedSocialNetworks = listOf("twitter", "x.org", "x.com", "truthsocial", "truth social")
}
    fun isTwitterOrTruthSocial() =
        excludedSocialNetworks.any { url.lowercase().contains(it) || name.lowercase().contains(it) }
}



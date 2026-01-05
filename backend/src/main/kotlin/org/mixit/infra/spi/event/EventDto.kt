package org.mixit.infra.spi.event

import kotlinx.serialization.Serializable
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.event.EventMedia
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.link.LinkType
import org.mixit.conference.model.people.SponsorshipLevel
import org.mixit.infra.util.serializer.toLocalDate

@Serializable
data class EventDto(
    val id: String = "",
    val start: String,
    val end: String,
    val current: Boolean = false,
    val sponsors: List<EventSponsoringDto> = emptyList(),
    val organizations: List<EventOrganizationDto> = emptyList(),
    val volunteers: List<EventVolunteerDto> = emptyList(),
    val organizers: List<EventOrganizerDto> = emptyList(),
    val photoUrls: List<LinkDto> = emptyList(),
    val videoUrl: LinkDto? = null,
    val schedulingFileUrl: String? = null,
    val year: Int,
    val streamingUrl: String? = null,
) {
    fun toEvent() =
        Event(
            id = id,
            start = start.toLocalDate(),
            end = end.toLocalDate(),
            year = year,
            media =
                EventMedia(
                    videoUrl =
                        videoUrl?.let {
                            Link(type = LinkType.VIDEO, url = it.url)
                        },
                    schedulingFileUrl = schedulingFileUrl,
                    streamingUrl = streamingUrl,
                ),
        )
}

@Serializable
data class EventOrganizationDto(
    val organizationLogin: String,
)

@Serializable
data class EventSponsoringDto(
    val level: SponsorshipLevel = SponsorshipLevel.NONE,
    val sponsorId: String = "",
    val subscriptionDate: String,
)

@Serializable
data class EventVolunteerDto(
    val volunteerLogin: String,
)

@Serializable
data class EventOrganizerDto(
    val organizerLogin: String,
)

@Serializable
data class LinkDto(
    val name: String,
    val url: String,
    val type: LinkType = LinkType.fromValue(name),
) {
    companion object {
        val excludedSocialNetworks = listOf("twitter", "x.org", "x.com", "truthsocial", "truth social")
    }

    fun isTwitterOrTruthSocial() = excludedSocialNetworks.any { url.lowercase().contains(it) || name.lowercase().contains(it) }
}

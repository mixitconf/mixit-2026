package org.mixit.storage.domain.event

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.mixit.conference.people.model.SponsorshipLevel
import org.mixit.storage.serializer.LocalDateSerializer

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
)

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
    val url: String
)

package org.mixit.infra.spi.people

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.mixit.infra.spi.event.LinkDto
import org.mixit.conference.model.link.Link
import org.mixit.conference.model.people.Attendee
import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.SponsorshipLevel
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer
import org.mixit.conference.model.shared.Language

/**
 * Person DTO data are crypted when stored in database except id, login, role and newsletterSubscriber
 */
@Serializable
data class PersonDto(
    val login: String = "mixit",
    val firstname: String? = "",
    val lastname: String? = "",
    val email: String? = null,
    val company: String? = null,
    val description: Map<Language, String> = emptyMap(),
    val emailHash: String? = null,
    val photoUrl: String? = null,
    val photoShape: String? = null,
    val role: Role = Role.USER,
    val links: List<LinkDto> = emptyList(),
    val legacyId: Long? = null,
    val cfpId: String? = null,
    var newsletterSubscriber: Boolean = false,
) {
    fun toAttendee() =
        Attendee(
            id = login,
            firstname = firstname,
            lastname = lastname,
            email = email,
            photoUrl = photoUrl,
        )

    fun toSpeaker() =
        Speaker(
            id = login,
            email = email,
            photoUrl = photoUrl,
            company = company,
            firstname = firstname ?: "",
            lastname = lastname ?: "",
            links = links.filterNot { it.isTwitterOrTruthSocial() }.map { Link(it.type, it.url) },
            description = description,
        )

    fun toSponsor(
        level: SponsorshipLevel,
        subscriptionDate: LocalDate,
    ) = Sponsor(
        id = login,
        email = email,
        photoUrl = photoUrl,
        name = company ?: "",
        links = links.filterNot { it.isTwitterOrTruthSocial() }.map { Link(it.type, it.url) },
        description = description,
        level = level,
        subscriptionDate = subscriptionDate,
    )

    fun toOrganization() =
        Organization(
            id = login,
            email = email,
            photoUrl = photoUrl,
            name = company ?: "",
            links = links.filterNot { it.isTwitterOrTruthSocial() }.map { Link(it.type, it.url) },
            description = description,
        )

    fun toStaff() =
        Staff(
            id = login,
            email = email,
            photoUrl = photoUrl,
            firstname = firstname ?: "",
            lastname = lastname ?: "",
            links = links.filterNot { it.isTwitterOrTruthSocial() }.map { Link(it.type, it.url) },
            description = description,
        )

    fun toVolunteer() =
        Volunteer(
            id = login,
            email = email,
            photoUrl = photoUrl,
            firstname = firstname ?: "",
            lastname = lastname ?: "",
            links = links.filterNot { it.isTwitterOrTruthSocial() }.map { Link(it.type, it.url) },
            description = description,
        )
}

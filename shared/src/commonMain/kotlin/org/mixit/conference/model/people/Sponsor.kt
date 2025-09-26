package org.mixit.conference.model.people

import kotlinx.datetime.LocalDate
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.link.Link

data class Sponsor(
    override val id: String,
    override val email: String?,
    override val photoUrl: String?,
    val name: String,
    val links: List<Link>,
    val description: Map<Language, String> = emptyMap(),
    val subscriptionDate: LocalDate,
    val level: SponsorshipLevel,
) : Person(
    id = id,
    email = email,
    photoUrl = photoUrl
)
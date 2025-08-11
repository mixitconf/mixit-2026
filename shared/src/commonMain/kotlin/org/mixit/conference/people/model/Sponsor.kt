package org.mixit.conference.people.model

import kotlinx.datetime.LocalDate
import org.mixit.conference.shared.model.Language
import org.mixit.conference.shared.model.Link

enum class SponsorshipLevel {
    GOLD,
    SILVER,
    BRONZE,
    LANYARD,
    PARTY,
    BREAKFAST,
    LUNCH,
    HOSTING,
    ECOLOGY,
    VIDEO,
    COMMUNITY,
    MIXTEEN,
    ECOCUP,
    ACCESSIBILITY,
    NONE;

    companion object {
        fun sponsorshipLevels() =
            listOf(GOLD, SILVER, HOSTING, ECOLOGY, LANYARD, ACCESSIBILITY, MIXTEEN, PARTY, VIDEO)
    }
}

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
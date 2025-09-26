package org.mixit.conference.model.people

enum class SponsorshipLevel(val priority: Int) {
    GOLD(8),
    SILVER(7),
    BRONZE(6),
    LANYARD(10),
    PARTY(9),
    BREAKFAST(6),
    LUNCH(6),
    HOSTING(6),
    ECOLOGY(6),
    VIDEO(6),
    COMMUNITY(6),
    MIXTEEN(6),
    ECOCUP(6),
    ACCESSIBILITY(6),
    NONE(0);

    companion object {
        fun sponsorshipLevels() =
            listOf(GOLD, SILVER, HOSTING, ECOLOGY, LANYARD, ACCESSIBILITY, MIXTEEN, PARTY, VIDEO)
                .sortedByDescending { it.priority }
    }
}
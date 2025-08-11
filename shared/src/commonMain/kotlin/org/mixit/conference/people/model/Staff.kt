package org.mixit.conference.people.model

import org.mixit.conference.shared.model.Language
import org.mixit.conference.shared.model.Link

data class Staff(
    override val id: String,
    override val email: String?,
    override val photoUrl: String?,
    val firstname: String,
    val lastname: String,
    val links: List<Link>,
    val description: Map<Language, String> = emptyMap(),
) : Person(
    id = id,
    email = email,
    photoUrl = photoUrl
)
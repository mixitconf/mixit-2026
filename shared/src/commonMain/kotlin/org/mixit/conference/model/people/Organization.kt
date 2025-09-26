package org.mixit.conference.model.people

import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.link.Link

data class Organization(
    override val id: String,
    override val email: String?,
    override val photoUrl: String?,
    val name: String,
    val links: List<Link>,
    val description: Map<Language, String> = emptyMap(),
) : Person(
    id = id,
    email = email,
    photoUrl = photoUrl
)
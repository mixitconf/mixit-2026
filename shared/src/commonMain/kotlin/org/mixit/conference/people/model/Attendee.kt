package org.mixit.conference.people.model

data class Attendee(
    override val id: String,
    val firstname: String,
    val lastname: String,
    override val email: String?,
    override val photoUrl: String?,
) : Person(
    id = id,
    email = email,
    photoUrl = photoUrl
)
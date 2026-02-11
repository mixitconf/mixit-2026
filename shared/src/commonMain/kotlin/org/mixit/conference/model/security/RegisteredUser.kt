package org.mixit.conference.model.security

import kotlinx.serialization.Serializable

@Serializable
data class RegisteredUser(
    val email: String,
    val firstname: String,
    val lastname: String,
)
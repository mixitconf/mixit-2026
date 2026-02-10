package org.mixit.infra.spi.manager

import kotlinx.serialization.Serializable
import org.mixit.conference.model.people.Role

@Serializable
data class AuthenticatedUserDto(
    val username: String,
    val email: String,
    val role: Role
)
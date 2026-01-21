package org.mixit.infra.spi.manager

import org.mixit.conference.model.people.Role

data class AuthenticatedUserDto(
    val username: String,
    val email: String,
    val role: Role
)
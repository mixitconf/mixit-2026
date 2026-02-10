package org.mixit.domain.api

import kotlinx.datetime.Instant
import org.mixit.conference.model.people.Role
import org.mixit.domain.model.JwtToken

/**
 * API for handling JWT (JSON Web Token) operations such as parsing, generation, and key management.
 */
interface JwtTokenApi {
    /**
     * Parses the given JWT token and extracts the associated email if valid.
     *
     * @param jwtToken the JWT token to parse
     * @return the extracted [org.mixit.conference.model.people.Email] if the token is valid, or null otherwise
     */
    fun parse(jwtToken: JwtToken): Pair<String, Role>?

    /**
     * Generates a JWT token for the given email with a specified expiration time.
     *
     * @param email the email to include in the token
     * @param expiration the expiration [kotlinx.datetime.Instant] of the token
     * @return the generated [JwtToken]
     */
    fun generate(
        email: String,
        role: Role,
        expiration: Instant,
    ): JwtToken

    /**
     * Generates a new cryptographic key for signing JWT tokens.
     *
     * @return the generated key as a [String]
     */
    fun generateKey(): String
}

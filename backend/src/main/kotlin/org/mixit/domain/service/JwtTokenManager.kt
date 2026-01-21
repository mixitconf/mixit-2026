package org.mixit.domain.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import org.mixit.infra.config.MixitProperties
import org.mixit.domain.api.JwtTokenApi
import org.mixit.domain.model.JwtToken
import org.mixit.infra.spi.people.Role
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import javax.crypto.KeyGenerator
import kotlin.let
import kotlin.text.trim
import kotlin.to

/**
 * This service can be used to parse the BCS/Portal JwtToken and extract the user identity and his displayable markets.
 * Displayable markets are chosen on the BCS/Portal header. In the future this class will delegate the work to another
 * solution as AWS Cognito for example
 */
@Component
class JwtTokenManager(
    private val properties: MixitProperties,
) : JwtTokenApi {
    companion object {
        const val ALGORITHM = "HmacSHA512"
        const val TOKEN_ROLE = "ROLE_USER"
    }

    override fun generateKey(): String = Base64.getEncoder().encodeToString(KeyGenerator.getInstance(ALGORITHM).generateKey().encoded)

    override fun generate(
        email: String,
        role: Role,
        expiration: Instant,
    ): JwtToken =
        Jwts
            .builder()
            .subject(email)
            .claims()
            .add(TOKEN_ROLE, role.name)
            .and()
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.security.jwtKey)), Jwts.SIG.HS256)
            .expiration(Date.from(expiration.toJavaInstant()))
            .compact()

    /**
     * This method decrypt the token and try to read the user identity
     */
    override fun parse(jwtToken: JwtToken): Pair<String, Role> {
        val trimmedToken = jwtToken.trim()
        val payload =
            Jwts
                .parser()
                .verifyWith(
                    Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.security.jwtKey)),
                ).build()
                .parseSignedClaims(trimmedToken)
                .payload

        val role = payload[TOKEN_ROLE]?.let { Role.valueOf(it.toString()) } ?: Role.USER
        return payload.subject to role
    }
}

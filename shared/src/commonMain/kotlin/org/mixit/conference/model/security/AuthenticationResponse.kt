package org.mixit.conference.model.security

import kotlinx.datetime.Instant

class AuthenticationResponse{
    lateinit var jwtToken: String
    lateinit var username: String
    lateinit var jwtExpiration: String
}
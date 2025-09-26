package org.mixit.conference.model.link

import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val name: String,
    val url: String,
    val image: String? = null
)
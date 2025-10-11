package org.mixit.conference.model.link

import kotlinx.serialization.Serializable
import org.mixit.conference.camelCase

@Serializable
data class Link(
    val type: LinkType = LinkType.CUSTOM,
    val url: String,
    val name: String = type.name.camelCase(),
)
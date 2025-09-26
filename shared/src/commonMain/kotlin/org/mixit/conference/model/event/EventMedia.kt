package org.mixit.conference.model.event

import org.mixit.conference.model.link.Link

data class EventMedia(
    val photoUrls: List<Link> = emptyList(),
    val videoUrl: Link? = null,
    val schedulingFileUrl: String? = null,
    val streamingUrl: String? = null
)
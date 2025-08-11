package org.mixit.conference.event.model

import kotlinx.datetime.LocalDate
import org.mixit.conference.shared.model.Link
data class Event(
    val id: String,
    val year: Int,
    val start: LocalDate,
    val end: LocalDate,
    val media: EventMedia? = null,
)

data class EventMedia(
    val photoUrls: List<Link> = emptyList(),
    val videoUrl: Link? = null,
    val schedulingFileUrl: String? = null,
    val streamingUrl: String? = null
)
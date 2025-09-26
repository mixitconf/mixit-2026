package org.mixit.conference.model.event

import kotlinx.datetime.LocalDate

data class Event(
    val id: String,
    val year: Int,
    val start: LocalDate,
    val end: LocalDate,
    val media: EventMedia? = null,
)


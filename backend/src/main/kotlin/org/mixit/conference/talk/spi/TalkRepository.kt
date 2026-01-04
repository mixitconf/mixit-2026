package org.mixit.conference.talk.spi

import org.mixit.conference.model.talk.Talk

interface TalkRepository {
    fun findByYear(year: Int): List<Talk>

    fun findRandomKeynote(nb: Int): List<Talk>

    fun findSpeakerTalks(speakerId: String): List<Talk>

    fun findBySlug(
        year: Int,
        slug: String,
    ): Talk?
}

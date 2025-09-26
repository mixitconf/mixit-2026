package org.mixit.conference.event.spi

import org.mixit.conference.model.faq.QuestionSet

interface FaqRepository {
    fun findAll(): List<QuestionSet>
}
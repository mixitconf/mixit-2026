package org.mixit.conference.faq.spi

import org.mixit.conference.model.faq.QuestionSet

interface FaqRepository {
    fun findAll(): List<QuestionSet>
}

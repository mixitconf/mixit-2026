package org.mixit.domain.spi

import org.mixit.conference.model.faq.QuestionSet

interface FaqRepository {
    fun findAll(): List<QuestionSet>
}
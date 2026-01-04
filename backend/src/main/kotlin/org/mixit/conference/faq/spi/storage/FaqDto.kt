package org.mixit.conference.faq.spi.storage

import kotlinx.serialization.Serializable
import org.mixit.conference.model.faq.I18nDescription
import org.mixit.conference.model.faq.Question
import org.mixit.conference.model.faq.QuestionSet

@Serializable
data class TextDto(
    val descriptionFr: String,
    val descriptionEn: String
) {
    fun toI18n() =
        I18nDescription(
            fr = descriptionFr,
            en = descriptionEn
        )
}

@Serializable
data class QuestionDto(
    val title: TextDto,
    val answer: TextDto,
    val order: Int,
    val id: String
) {
    fun toQuestion() =
        Question(
            id = id,
            title = title.toI18n(),
            answer = answer.toI18n(),
            order = order
        )
}

@Serializable
data class FaqDto(
    val title: TextDto,
    val questions: List<QuestionDto>,
    val order: Int,
    val id: String,
    val index: String
) {
    fun toQuestionSet() =
        QuestionSet(
            id = id,
            title = title.toI18n(),
            questions = questions.map { it.toQuestion() },
            order = order
        )
}
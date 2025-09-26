package org.mixit.conference.model.faq


data class QuestionSet(
    val title: I18nDescription,
    val questions: List<Question>,
    val order: Int,
    val id: String
)
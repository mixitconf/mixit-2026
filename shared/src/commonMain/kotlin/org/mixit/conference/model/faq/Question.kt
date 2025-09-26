package org.mixit.conference.model.faq

data class Question(
    val title: I18nDescription,
    val answer: I18nDescription,
    val order: Int,
    val id: String
)
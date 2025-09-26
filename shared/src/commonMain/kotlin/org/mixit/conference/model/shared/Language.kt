package org.mixit.conference.model.shared

enum class Language(val urlPrefix: String) {
    FRENCH("/fr"),
    ENGLISH("/en");

    fun toLanguageTag(): String = name.lowercase().subSequence(0, 2).toString()

    companion object {
        fun findByTag(name: String): Language {
            val language = entries.filter { value -> value.toLanguageTag() == name }
            if (language.isEmpty()) {
                throw IllegalArgumentException()
            }
            return language.first()
        }
    }
}

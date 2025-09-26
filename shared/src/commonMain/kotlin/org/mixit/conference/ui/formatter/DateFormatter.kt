package org.mixit.conference.ui.formatter

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.mixit.conference.model.shared.Language

val enMonthNames = MonthNames(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

val frMonthNames = MonthNames(
    "janvier", "février", "mars", "avril", "mai",
    "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"
)

val enLocalDateFormatter = LocalDate.Format {
    monthName(enMonthNames)
    char(' ')
    dayOfMonth()
}
val frLocalDateFormatter = LocalDate.Format {
    dayOfMonth()
    char(' ')
    monthName(frMonthNames)
}

fun LocalDate.formatDate(language: Language): String =
    if(language == Language.FRENCH) this.format(frLocalDateFormatter) else this.format(enLocalDateFormatter)

fun LocalDateTime.formatTime(): String =
    this.toString().substring(11, 16)

package org.mixit.util

import java.text.Normalizer
import java.util.Locale

fun String.stripAccents() = Normalizer
    .normalize(this, Normalizer.Form.NFD)
    .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

fun String.toSlug() = lowercase()
    .stripAccents()
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-") // Avoid multiple consecutive "--"
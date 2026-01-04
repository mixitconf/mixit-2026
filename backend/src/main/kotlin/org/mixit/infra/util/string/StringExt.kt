package org.mixit.infra.util.string

import java.text.Normalizer

fun String.stripAccents() =
    Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

fun String.toSlug() =
    lowercase()
        .stripAccents()
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-") // Avoid multiple consecutive "--"

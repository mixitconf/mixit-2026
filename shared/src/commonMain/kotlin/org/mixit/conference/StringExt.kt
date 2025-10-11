package org.mixit.conference

fun String.camelCase(): String = this
    .trim()
    .split(" ")
    .joinToString(" ") { str ->
        str.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

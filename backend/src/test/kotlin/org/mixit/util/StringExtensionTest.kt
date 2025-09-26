package org.mixit.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringExtensionTest {
    @Test
    fun toSlug() {
        assertEquals("", "".toSlug())
        assertEquals("-", "---".toSlug())
        assertEquals("billetterie-mixit-2017-pre-inscription", "Billetterie MiXiT 2017 : pré-inscription".toSlug())
        assertEquals("mixit-2017-ticketing-pre-registration", "MiXiT 2017 ticketing: pre-registration".toSlug())
    }

    @Test
    fun camelCase() {
        assertEquals("Mixit", "mixit".camelCase())
        assertEquals("Mixit Conference", "mixit conference".camelCase())
        assertEquals("Mixit Conference", "  mixit conference  ".camelCase())
        assertEquals("Mixit", "MIXIT".camelCase())
        assertEquals("", "".camelCase())
    }

}
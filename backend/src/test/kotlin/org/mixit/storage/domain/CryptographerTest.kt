package org.mixit.storage.domain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mixit.config.MixitProperties
import org.mixit.config.SecurityProperties
import org.mixit.infra.util.serializer.Cryptographer

class CryptographerTest {
    @MockK
    private lateinit var properties: MixitProperties
    private lateinit var service: Cryptographer

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        val securityProperties =
            SecurityProperties(
                key = "myKeyIsTheBest01",
                initVector = "myvectorIsBetter",
            )
        service = Cryptographer(properties)

        every { properties.security } returns securityProperties
    }

    @Test
    fun `should encrypt and decrypt a String`() {
        val stringToEncode = "MiXiT is the best conf in the world"
        val encodedString = service.encrypt(stringToEncode)

        Assertions.assertThat(encodedString).isNotEmpty()
        Assertions.assertThat(service.decrypt(encodedString)).isEqualTo(stringToEncode)
    }

    @Test
    fun `encrypt and decrypt functions are null safe`() {
        Assertions.assertThat(service.encrypt(null)).isNull()
        Assertions.assertThat(service.decrypt(null)).isNull()
    }
}

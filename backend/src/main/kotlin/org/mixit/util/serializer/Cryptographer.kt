package org.mixit.util.serializer

import org.mixit.MixitProperties
import org.springframework.stereotype.Component
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 15/10/17.
 */
@Component
class Cryptographer(
    private val properties: MixitProperties,
) {
    fun encrypt(value: String?): String? = value?.encrypt(properties.security.key, properties.security.initVector)

    fun decrypt(value: String?): String? = value?.decrypt(properties.security.key, properties.security.initVector)

    private fun String?.encrypt(
        key: String,
        initVector: String,
    ): String? {
        try {
            if (this == null) return null
            val encrypted = cipher(key, initVector, Cipher.ENCRYPT_MODE).doFinal(toByteArray())
            return Base64.getEncoder().encodeToString(encrypted)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun String?.decrypt(
        key: String,
        initVector: String,
    ): String? {
        try {
            if (this == null) return null
            val encrypted = Base64.getDecoder().decode(toByteArray())
            return String(cipher(key, initVector, Cipher.DECRYPT_MODE).doFinal(encrypted))
        } catch (ex: Exception) {
            return this
        }
    }

    private fun cipher(
        key: String,
        initVector: String,
        mode: Int,
    ): Cipher {
        val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
        val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(mode, skeySpec, iv)
        return cipher
    }
}

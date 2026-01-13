package org.mixit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties("mixit")
data class MixitProperties(
    val security: SecurityProperties,
    val doc: DocumentProperties,
    val cfpUrl: URI,
    val billetwebUrl: URI,
    val newsLetterUrl: URI,
    val podcastId: String,
    val externalData: ExternalDataProperties,
)

data class ExternalDataProperties(
    val url: String,
    val enabled: Boolean,
)

data class SecurityProperties(
    val initVector: String,
    val key: String,
)

data class DocumentProperties(
    val fr: LanguageDocumentsProperties,
    val en: LanguageDocumentsProperties,
)

data class LanguageDocumentsProperties(
    val sponsorForm: URI,
    val sponsor: URI,
    val sponsorMixteenForm: URI,
    val speaker: URI,
    val press: URI,
)

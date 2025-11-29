package org.mixit

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties("mixit")
data class MixitProperties(
    val security: SecurityProperties,
    val doc: DocumentProperties,
    val cfpUrl: URI,
    val newsLetterUrl: URI,
    val podcastId: String
)

data class SecurityProperties(
    val initVector: String,
    val key: String
)

data class DocumentProperties(
    val fr : LanguageDocumentsProperties,
    val en : LanguageDocumentsProperties
)

data class LanguageDocumentsProperties(
    val sponsorForm: URI,
    val sponsor: URI,
    val sponsorMixteenForm: URI,
    val speaker: URI,
    val press: URI
)

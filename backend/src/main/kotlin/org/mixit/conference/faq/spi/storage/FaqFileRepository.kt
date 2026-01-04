package org.mixit.conference.faq.spi.storage

import org.mixit.Constants
import org.mixit.conference.faq.spi.FaqRepository
import org.mixit.conference.model.faq.QuestionSet
import org.mixit.util.cache.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path

@Component
class FaqFileRepository : FaqRepository {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableSet<FaqDto>

    init {
        val path = Path.of(ClassPathResource("data/faq.json").url.path)
        val json = Files.readString(path)
        _data = Constants.serializer.decodeFromString<Array<FaqDto>>(json).toMutableSet()
    }

    @Cacheable(Cache.FAQ_CACHE)
    override fun findAll(): List<QuestionSet> = _data.map { faq -> faq.toQuestionSet() }
}

package org.mixit.infra.spi.faq

import jakarta.annotation.PostConstruct
import org.mixit.conference.faq.spi.storage.FaqDto
import org.mixit.conference.model.faq.QuestionSet
import org.mixit.domain.spi.FaqRepository
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.spi.DataService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class FaqFileRepository(
    private val dataService: DataService
) : FaqRepository {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableSet<FaqDto> = mutableSetOf()

    @PostConstruct
    fun init() {
        _data.addAll(
            dataService.load(
                localPath = "data/faq.json",
                remotePath = "/faq",
                responseType = Array<FaqDto>::class.java,
            )
        )
    }

    @Cacheable(Cache.FAQ_CACHE)
    override fun findAll(): List<QuestionSet> = _data.map { faq -> faq.toQuestionSet() }
}

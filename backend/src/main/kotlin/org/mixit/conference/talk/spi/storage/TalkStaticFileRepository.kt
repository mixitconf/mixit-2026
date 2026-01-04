package org.mixit.conference.talk.spi.storage

import org.mixit.Constants
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.util.cache.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class TalkStaticFileRepository {

    private val _data: MutableMap<Int, List<TalkDto>> = mutableMapOf()

    init {
        (2012..CURRENT_YEAR).forEach { year ->
            val path = Path.of(ClassPathResource("data/talks_$year.json").url.path)
            val json = Files.readString(path)
            val talks = Constants.serializer.decodeFromString<Array<TalkDto>>(json)
            _data[year] = talks.toList()
        }
    }

    @Cacheable(Cache.TALK_CACHE)
    fun findAll(): Map<Int, List<TalkDto>> {
        return _data
    }

    @Cacheable(Cache.TALK_SPEAKER_IDS)
    fun findAllSpeakers(): Set<String> =
        _data.entries.map { talks -> talks.value.flatMap { it.speakerIds } }.flatten().toSet()

}
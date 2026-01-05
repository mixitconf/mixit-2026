package org.mixit.infra.spi.talk

import jakarta.annotation.PostConstruct
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.spi.DataService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class TalkStaticFileRepository(
    private val dataService: DataService
) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableMap<Int, List<TalkDto>> = mutableMapOf()

    @PostConstruct
    fun init() {
        (2012..CURRENT_YEAR).forEach { year ->
            dataService.load(
                localPath = "data/talks_$year.json",
                remotePath = "/talks/$year",
                responseType = Array<TalkDto>::class.java,
            ).let { talks ->
                _data[year] = talks
            }
        }
    }
//    init {
//        (2012..CURRENT_YEAR).forEach { year ->
//            val path = Path.of(ClassPathResource("data/talks_$year.json").url.path)
//            val json = Files.readString(path)
//            val talks = Constants.serializer.decodeFromString<Array<TalkDto>>(json)
//            _data[year] = talks.toList()
//        }
//    }

    @Cacheable(Cache.TALK_CACHE)
    fun findAll(): Map<Int, List<TalkDto>> = _data

    @Cacheable(Cache.TALK_SPEAKER_IDS)
    fun findAllSpeakers(): Set<String> =
        _data.entries
            .map { talks -> talks.value.flatMap { it.speakerIds } }
            .flatten()
            .toSet()
}

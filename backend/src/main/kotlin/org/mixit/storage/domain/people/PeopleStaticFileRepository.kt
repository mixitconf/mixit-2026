package org.mixit.storage.domain.people

import org.mixit.Constants
import org.mixit.conference.model.link.Link
import org.mixit.storage.domain.Cache
import org.mixit.storage.domain.Cryptographer
import org.mixit.storage.domain.event.LinkDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class PeopleStaticFileRepository(
    private val cryptographer: Cryptographer
) {
    private val _data: MutableSet<PersonDto>

    init {
            val path = Path.of(ClassPathResource("data/users.json").url.path)
            val json = Files.readString(path)
            val users = Constants.serializer.decodeFromString<Array<PersonDto>>(json)
            _data = users
                .map { dto ->
                    val firstname = cryptographer.decrypt(dto.firstname)
                    val lastname = cryptographer.decrypt(dto.lastname)

                    PersonDto(
                        login = cryptographer.decrypt(dto.login) ?: "",
                        email = cryptographer.decrypt(dto.email),
                        emailHash =  cryptographer.decrypt(dto.emailHash),
                        photoUrl = cryptographer.decrypt(dto.photoUrl),
                        company = cryptographer.decrypt(dto.company) ?: "$firstname $lastname",
                        links = dto.links.map { LinkDto(
                            name = cryptographer.decrypt(it.name) ?: "",
                            url = cryptographer.decrypt(it.url) ?: ""
                        ) },
                        description = dto.description.mapValues { cryptographer.decrypt(it.value) ?: "" },
                        firstname = firstname,
                        lastname = lastname,
                        role = dto.role
                    )
                }
                .toMutableSet()
    }

    @Cacheable(Cache.PEOPLE_CACHE)
    fun findAll(): Set<PersonDto> {
        return _data
    }
}

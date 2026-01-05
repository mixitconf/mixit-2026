package org.mixit.infra.spi.people

import jakarta.annotation.PostConstruct
import org.mixit.infra.spi.event.LinkDto
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.spi.DataService
import org.mixit.infra.util.serializer.Cryptographer
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PeopleStaticFileRepository(
    private val cryptographer: Cryptographer,
    private val dataService: DataService
) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableSet<PersonDto> = mutableSetOf()

    @PostConstruct
    fun init() {
        _data.addAll(
            dataService.load(
                localPath = "data/users.json",
                remotePath = "/users",
                responseType = Array<PersonDto>::class.java,
            ).map { dto ->
                val firstname = cryptographer.decrypt(dto.firstname)
                val lastname = cryptographer.decrypt(dto.lastname)

                PersonDto(
                    login = cryptographer.decrypt(dto.login) ?: "",
                    email = cryptographer.decrypt(dto.email),
                    emailHash = cryptographer.decrypt(dto.emailHash),
                    photoUrl = cryptographer.decrypt(dto.photoUrl),
                    company = cryptographer.decrypt(dto.company) ?: "$firstname $lastname",
                    links =
                        dto.links.map {
                            LinkDto(
                                name = cryptographer.decrypt(it.name) ?: "",
                                url = cryptographer.decrypt(it.url) ?: "",
                            )
                        },
                    description = dto.description.mapValues { cryptographer.decrypt(it.value) ?: "" },
                    firstname = firstname,
                    lastname = lastname,
                    role = dto.role,
                )
            }.toMutableSet()
        )
    }

    @Cacheable(Cache.PEOPLE_CACHE)
    fun findAll(): Set<PersonDto> = _data
}

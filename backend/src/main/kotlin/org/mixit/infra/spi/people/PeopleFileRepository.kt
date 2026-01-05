package org.mixit.infra.spi.people

import org.mixit.infra.spi.event.EventStaticFileRepository
import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer
import org.mixit.domain.spi.PeopleRepository
import org.mixit.infra.spi.talk.TalkStaticFileRepository
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.util.serializer.toLocalDate
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PeopleFileRepository(
    private val peopleRepository: PeopleStaticFileRepository,
    private val eventRepository: EventStaticFileRepository,
    private val talkFileRepository: TalkStaticFileRepository,
) : PeopleRepository {

    @Cacheable(Cache.SPEAKER_CACHE)
    override fun findSpeakerByYear(year: Int): List<Speaker> {
        val talks = talkFileRepository.findAll()[year] ?: return emptyList()
        val speakerIds = talks.flatMap { it.speakerIds }
        return findSpeakerByIds(speakerIds)
    }

    override fun findSpeakerByIds(ids: List<String>): List<Speaker> =
        peopleRepository
            .findAll()
            .asSequence()
            .filter { ids.contains(it.login) }
            .map { it.toSpeaker() }
            .toList()

    @Cacheable(Cache.SPONSOR_CACHE)
    override fun findSponsorByYear(year: Int): List<Sponsor> {
        val event = eventRepository.findAll().firstOrNull { it.year == year } ?: return emptyList()
        val sponsorIds = event.sponsors.map { it.sponsorId }
        return peopleRepository
            .findAll()
            .asSequence()
            .filter { sponsorIds.contains(it.login) }
            .flatMap { personDto ->
                event.sponsors
                    .filter { it.sponsorId == personDto.login }
                    .map { sponsor -> personDto.toSponsor(sponsor.level, sponsor.subscriptionDate.toLocalDate()) }
            }.toList()
    }

    @Cacheable(Cache.ORGANIZATION_CACHE)
    override fun findOrganizationByYear(year: Int): List<Organization> {
        val event = eventRepository.findAll().firstOrNull { it.year == year } ?: return emptyList()
        val ids = event.organizations.map { it.organizationLogin }
        return peopleRepository
            .findAll()
            .asSequence()
            .filter { ids.contains(it.login) }
            .map { it.toOrganization() }
            .toList()
    }

    @Cacheable(Cache.STAFF_CACHE)
    override fun findStaffByYear(year: Int): List<Staff> {
        val event = eventRepository.findAll().firstOrNull { it.year == year } ?: return emptyList()
        val staffs = event.organizers.map { it.organizerLogin }
        return peopleRepository
            .findAll()
            .asSequence()
            .filter { staffs.contains(it.login) }
            .map { it.toStaff() }
            .toList()
    }

    @Cacheable(Cache.VOLUNTEER_CACHE)
    override fun findVolunteerByYear(year: Int): List<Volunteer> {
        val event = eventRepository.findAll().firstOrNull { it.year == year } ?: return emptyList()
        val volunteers = event.volunteers.map { it.volunteerLogin }
        return peopleRepository
            .findAll()
            .asSequence()
            .filter { volunteers.contains(it.login) }
            .map { it.toVolunteer() }
            .toList()
    }

    /**
     * For the moment we return only if the person is a speaker or a sponsor
     */
    @Cacheable(Cache.PERSON_CACHE)
    override fun findSpeaker(login: String): Speaker? {
        // We need to know if the person is a speaker, sponsor, staff or volunteer
        val person = peopleRepository.findAll().firstOrNull { it.login == login } ?: return null
        return talkFileRepository
            .findAllSpeakers()
            .firstOrNull { it == login }
            ?.let { person.toSpeaker() }
    }
}

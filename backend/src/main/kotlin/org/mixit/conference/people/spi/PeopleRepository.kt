package org.mixit.conference.people.spi

import org.mixit.conference.model.people.Organization
import org.mixit.conference.model.people.Person
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.people.Staff
import org.mixit.conference.model.people.Volunteer

interface PeopleRepository {
    fun findSpeakerByYear(year: Int): List<Speaker>
    fun findSpeakerByIds(ids: List<String>): List<Speaker>
    fun findSponsorByYear(year: Int): List<Sponsor>
    fun findStaffByYear(year: Int): List<Staff>
    fun findVolunteerByYear(year: Int): List<Volunteer>
    fun findOrganizationByYear(year: Int): List<Organization>
    fun findSpeaker(login: String): Speaker?
}
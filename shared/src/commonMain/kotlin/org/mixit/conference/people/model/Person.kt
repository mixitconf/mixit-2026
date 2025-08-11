package org.mixit.conference.people.model

sealed class Person(
    open val id: String,
    open val email: String?,
    open val photoUrl: String?
)





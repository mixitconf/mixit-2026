package org.mixit.conference.model.people

sealed class Person(
    open val id: String,
    open val email: String?,
    open val photoUrl: String?
)





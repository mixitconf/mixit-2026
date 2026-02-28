package org.mixit.conference.model.talk

enum class TalkFormat(val duration: Int, val order: Int) {
    TALK(50, 2),
    LIGHTNING_TALK(20, 3),
    WORKSHOP(110, 4),
    RANDOM(25, 5),
    KEYNOTE(25, 1),
    KEYNOTE_SURPRISE(25, 1),
    CLOSING_SESSION(25, 1),
    INTERVIEW(45, 6),
    ON_AIR(30, 6);

    fun isKeynote(): Boolean =
        this == KEYNOTE || this == KEYNOTE_SURPRISE || this == CLOSING_SESSION
}

package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.h2
import org.mixit.conference.model.shared.Context

enum class SectionEffect {
    START, END, NONE, BOTH
}

enum class SectionStyle(private val effectStyle: String) {
    DARK("mxt-layout__section-effect mxt-layout__section-effect-%%-dark"),
    TRANSPARENT(""),
    LIGHT("mxt-layout__section-effect mxt-layout__section-effect-%%-light");

    fun classes(effect: SectionEffect) = effectStyle.replace("%%", effect.name.lowercase()).trim()
}

fun DIV.sectionComponent(
    context: Context?,
    i18nKey: String? = null,
    effect: SectionEffect = SectionEffect.NONE,
    style: SectionStyle = SectionStyle.TRANSPARENT,
    block: DIV.() -> Unit
) {
    if (effect == SectionEffect.START || effect == SectionEffect.BOTH) {
        div(classes ="mt-4 " + style.classes(SectionEffect.START)) {}
    }
    div(classes ="mxt-layout__section-${style.name.lowercase()}") {
        div(classes ="container-xxl") {
            if (i18nKey != null) {
                h2 {
                    attributes["id"] = i18nKey
                    +(context?.i18n(i18nKey) ?: "")
                }
            }
            block()
        }
    }
    if (effect == SectionEffect.END || effect == SectionEffect.BOTH) {
        div(classes =style.classes(SectionEffect.END)) {}
    }
}
package org.mixit.conference.ui.form

import kotlinx.html.DIV
import kotlinx.html.FORM
import kotlinx.html.FormMethod
import kotlinx.html.div
import kotlinx.html.form

enum class FormMode {
    NEW, EDIT
}

data class FormDescriptor<T>(
    val fields: List<FormField>,
    val globalError: FormFieldError ? = null,
    val converter: (FormDescriptor<T>) -> T
){
    companion object {
        fun addHiddenFormFieldMode (mode: FormMode) = FormField(
            "mode",
            type = FormFieldType.Hidden,
            defaultValue = mode.name,
        )
    }

    fun isModeNew() =
        fields.firstOrNull { it.fieldName == "mode" }?.defaultValue == FormMode.NEW.name

    fun isModeEdit() =
        fields.firstOrNull { it.fieldName == "mode" }?.defaultValue == FormMode.EDIT.name



    fun form(div: DIV, action: String, buttons: () -> Unit) {
        div.form(
            classes = "needs-validation mxt-form",
            action = action,
            method = FormMethod.post
        ) {
            val form = this
            attributes["novalidate"] = "true"
            if (globalError != null) {
                div("alert alert-danger mxt-form__global-error mt-2") {
                    attributes["role"] = "alert"
                    +globalError
                }
            }
            div(classes="mxt-form__container") {
                fields.forEach {
                    it.formField(form)
                }
            }
            div(classes="mxt-btn__group mt-4") {
                attributes["role"] = "group"
                buttons.invoke()
            }
        }
    }

    fun filterForm(div: DIV, action: String, formMethod: FormMethod = FormMethod.post, buttons: () -> Unit) {
        div.form(
            classes = "needs-validation mxt-form mxt-form__filter",
            action = action,
            method = formMethod
        ) {
            val form = this
            attributes["novalidate"] = "true"
            if (globalError != null) {
                div("alert alert-danger mxt-form__global-error mt-2") {
                    attributes["role"] = "alert"
                    +globalError
                }
            }
            div(classes="mxt-form__container mxt-form__filter") {
                fields.forEach {
                    it.formField(form)
                }
                div(classes="flex-fill") {}
                buttons.invoke()
            }
        }
    }

    fun isValid(): Boolean = fields.all { it.error == null && it.validator.invoke() } && globalError == null

    fun field(name: String): FormField? = fields.firstOrNull { it.fieldName == name }

    fun value(): T = converter(this)
}
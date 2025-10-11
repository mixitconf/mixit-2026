package org.mixit.conference.ui.form

import kotlinx.html.*

data class FormField(
    val fieldName: String,
    val type: FormFieldType,
    val fieldLabel: String? = null,
    val isRequired: Boolean = false,
    val isReadonly: Boolean = false,
    val options: List<Pair<String, String>> = emptyList(),
    val defaultValue: String? = null,
    val textAreaRows: String = "4",
    val fieldPlaceholder: String? = when (type) {
        FormFieldType.Date -> "YYYY-MM-DD"
        else -> null
    },
    val help: String? = null,
    val validationPattern: String? = when (type) {
        FormFieldType.Date -> "(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"
        FormFieldType.Email -> "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b"
        FormFieldType.Url -> "^http(s?)://(w{3}\\.)?[\\w\\.\\-\\/]*\$"
        else -> null
    },
    val validator: (() -> Boolean) = {
        if (validationPattern != null && !defaultValue.isNullOrBlank()) {
            Regex(validationPattern).matches(defaultValue)
        } else if (isRequired) !defaultValue.isNullOrBlank() else true
    },
    val error: FormFieldError? = if (validator.invoke()) null else "This value is invalid",
    val isHidden: () -> Boolean = { false },
) {

    fun formField(form: FORM) {
        val isValid = validator.invoke()
        val validStyle = if (!isValid) "is-invalid" else if (!defaultValue.isNullOrBlank()) "is-valid" else ""
        val isHidden = type == FormFieldType.Hidden || isHidden.invoke()
        if (isHidden) {
            form.hiddenInput {
                this.name = fieldName
                this.value = defaultValue ?: ""
            }
            return
        }
        form.div(classes = "form-group ${if (help != null) "mxt-has-tooltip" else ""}") {
            if (help != null) {
                span(classes = "mxt-tooltip small") { +help }
            }
            if (fieldLabel != null) {
                label(classes = "form-label ${if (isRequired) "mxt-form__field-required" else ""}") {
                    htmlFor = fieldName
                    +fieldLabel
                }
            }
            when (type) {
                FormFieldType.Date, FormFieldType.Text, FormFieldType.Url, FormFieldType.Hidden ->
                    textInput(classes = "form-control $validStyle") {
                        this.required = isRequired
                        this.readonly = isReadonly
                        this.name = fieldName
                        this.value = defaultValue ?: ""
                        this.placeholder = fieldPlaceholder ?: ""
                        if (validationPattern != null) {
                            this.pattern = validationPattern
                        }
                    }

                FormFieldType.Number -> numberInput(classes = "form-control $validStyle") {
                    this.required = isRequired
                    this.name = fieldName
                    this.readonly = isReadonly
                    this.value = defaultValue ?: ""
                    this.placeholder = fieldPlaceholder ?: ""
                    if (validationPattern != null) {
                        this.pattern = validationPattern
                    }
                }

                FormFieldType.Email -> emailInput(classes = "form-control $validStyle") {
                    this.required = isRequired
                    this.name = fieldName
                    this.value = defaultValue ?: ""
                    this.readonly = isReadonly
                    this.placeholder = fieldPlaceholder ?: ""
                    if (validationPattern != null) {
                        this.pattern = validationPattern
                    }
                }

                FormFieldType.Password -> passwordInput(classes = "form-control $validStyle") {
                    this.required = isRequired
                    this.name = fieldName
                    this.readonly = isReadonly
                    this.value = defaultValue ?: ""
                    this.placeholder = fieldPlaceholder ?: ""
                    if (validationPattern != null) {
                        this.pattern = validationPattern
                    }
                }

                FormFieldType.TextArea -> textArea(classes = "form-control $validStyle") {
                    this.required = isRequired
                    this.name = fieldName
                    this.readonly = isReadonly
                    this.rows = textAreaRows
                    this.placeholder = fieldPlaceholder ?: ""
                    this.text(defaultValue ?: "")
                }

                FormFieldType.Checkbox -> div(classes = "form-check $validStyle") {
                    checkBoxInput(classes = "form-check-input $validStyle") {
                        this.required = isRequired
                        this.id = fieldName
                        this.name = fieldName
                        this.readonly = isReadonly
                        this.checked = defaultValue == "true"
                        this.value = "true"
                    }
                    label(classes = "form-check-label") {
                        htmlFor = fieldName
                        +(help ?: "")
                    }
                }

                FormFieldType.Select -> select(classes = "form-select") {
                    this.name = fieldName
                    this.required = isRequired
                    options.forEach { input ->
                        option {
                            value = input.first
                            this.label = input.second
                            selected = (input.first == defaultValue)
                        }
                    }
                }
            }
            if (error != null) {
                div("mxt-form__field-error invalid-feedback") {
                    small {
                        +error
                    }
                }
            }
        }
    }
}
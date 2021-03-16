package bug.domain

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ValueSetDto(
    val code: String,

    @JsonProperty("parameterValueEntries")
    @JsonAlias("valueSetEntries")
    val valueSetEntries: Set<ValueSetEntryDto>,

    val enabled: Boolean,
    val public: Boolean = false,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
    val parameterCode: String,
)

data class SaveValueSetDto(
    @field:Pattern(regexp = "^[A-Za-z0-9_-]+", message = "{valueSet.code.pattern}")
    @field:Length(message = "{valueSet.code.maxLength}", max = 60)
    @field:NotBlank(message = "{valueSet.code.notBlank}")
    val code: String,

    @field:Valid
    @JsonProperty("parameterValueEntries")
    @JsonAlias("valueSetEntries")
    val valueSetEntries: Set<SaveValueSetEntryDto>,

    val enabled: Boolean,
    val public: Boolean = false,

    val parameterCode: String
)

data class UpdateValueSetDto(
    @JsonProperty("parameterValueEntries")
    @JsonAlias("valueSetEntries")
    val valueSetEntries: Set<SaveValueSetEntryDto>,
)

data class ValueSetEntryDto(
    val key: String,
    val value: Value = Value(""),
)

data class SaveValueSetEntryDto(
    @field:Pattern(regexp = "^[A-Za-z0-9_-]+", message = "{valueSetEntry.key.pattern}")
    @field:Length(message = "{valueSetEntry.key.maxLength}", max = 60)
    @field:NotBlank(message = "{valueSetEntry.key.notBlank}")
    val key: String = "",
    val value: Value = Value(""),
)

data class UpdateValueSetEntryDto(
    val value: Value
)

data class ValueResponseDto(
    val content: Any
)

package bug.domain

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import javax.validation.constraints.Pattern

@Document(collection = "parameterValues")
data class ValueSet(
    @field:Pattern(regexp = "^[A-Za-z0-9_-]+", message = "{parameterValueSet.code.pattern}")
    @field:Length(message = "{parameterValueSet.code.maxLength}", max = 60)
    @Indexed
    val code: String,

    @Field("parameterValueEntries")
    var valueSetEntries: Set<ValueSetEntry>,

    var enabled: Boolean,
    var public: Boolean = false,
    val createdOn: LocalDateTime,
    var updatedOn: LocalDateTime,

    @Indexed
    val parameterCode: String,

    @field:Id
    val id: String? = null
)

data class ValueSetEntry(
    @field:Pattern(regexp = "^[A-Za-z0-9_-]+", message = "{valueSetEntry.key.pattern}")
    @field:Length(message = "{valueSetEntry.key.maxLength}", max = 60)

    val key: String,
    var value: Value = Value(""),
)

data class Value(
    var content: Any,
    var mediaType: String? = null,
    var filename: String? = null
)

data class SubSetOfValueSets(
    val valueSetCodes: Set<String>,
    val createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now()
)

enum class ValueSetToggleableField {
    ENABLED,
    PUBLIC
}

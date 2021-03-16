package bug.domain

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.Pattern

@Document(collection = "parameters")
data class Parameter(

    @field:Pattern(regexp = "^[A-Za-z0-9_-]+", message = "{parameter.code.pattern}")
    @field:Length(message = "{parameter.code.maxLength}", max = 60)
    @Indexed(unique = true)
    val code: String,

    var name: String,
    var description: String,
    var enabled: Boolean,
    var subSets: Map<String, SubSetOfValueSets>,
    val parameterSchema: Set<ParameterSchemaEntry>,
    val createdOn: LocalDateTime,
    var updatedOn: LocalDateTime,

    @field:Id
    val id: String? = null

)

data class ParameterSchemaEntry(
    val fieldName: String,
    val parameterValidationType: ParameterValidationType,
    val required: Boolean
)

enum class ParameterValidationType {
    STRING,
    INT,
    FLOAT
}



package bug.service

import bug.domain.*
import bug.exception.AlreadyExistsException
import bug.exception.BadValueException
import bug.exception.NotFoundException
import bug.infrastructure.repository.ParameterRepository
import bug.infrastructure.repository.ValueSetRepository
import bug.toDto
import bug.toModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ValueSetService(
    private val valueSetRepository: ValueSetRepository,
    private val parameterRepository: ParameterRepository,
) {

    fun addValueSet(parameterCode: String, valueSetDto: SaveValueSetDto): Mono<ValueSetDto> {
        val valueSetCode = valueSetDto.code
        return valueSetRepository.findByCodeAndParameterCode(valueSetCode, parameterCode)
            .flatMap { Mono.error<ValueSet>(AlreadyExistsException("Ya existe valueSet con code $valueSetCode para el parámetro con code $parameterCode")) }
            .switchIfEmpty(
                saveValueSetOrError(parameterCode, valueSetDto)
            )
            .map(ValueSet::toDto)
    }

    private fun saveValueSetOrError(
        parameterCode: String,
        valueSet: SaveValueSetDto
    ): Mono<ValueSet> =
        findParameterByCodeOrError(parameterCode)
            .flatMap {
                val schema = it.parameterSchema
                val valuesToBeAdded = valueSet.valueSetEntries

                val valuesAccordingSchema = validateValueSet(schema, valuesToBeAdded)
                if (valuesAccordingSchema) {
                    valueSetRepository.save(valueSet.toModel())
                } else {
                    Mono.error(BadValueException("El valueSet con código ${valueSet.code} no cumple con el esquema registrado"))
                }
            }

    private fun validateValueSet(
        schema: Set<ParameterSchemaEntry>,
        values: Set<SaveValueSetEntryDto>
    ): Boolean {
        val dataWithoutSchema = schema.isEmpty()
        if (dataWithoutSchema) return true

        if (schema.size != values.size) return false

        val sortedValues = values.sortedBy { it.key }
        val sortedSchema = schema.sortedBy { it.fieldName }

        return sortedSchema.zip(sortedValues).all { (schemaEntry, valueSetEntry) ->
            if (schemaEntry.parameterValidationType != ParameterValidationType.STRING) {
                try {
                    valueSetEntry.value.content = valueSetEntry.value.content.toString().toDouble()
                } catch (e: NumberFormatException) {
                    return false
                }
            }
            schemaEntry.fieldName == valueSetEntry.key
        }
    }


    private fun findParameterByCodeOrError(code: String) =
        parameterRepository.findByCode(code)
            .switchIfEmpty(Mono.error(notFoundException(code)))

    private fun notFoundException(code: String) = NotFoundException("No hay valores asociados al parámetro=$code")

}

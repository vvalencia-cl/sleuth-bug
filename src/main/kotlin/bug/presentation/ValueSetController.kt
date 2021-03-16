package bug.presentation

import bug.domain.SaveValueSetDto
import bug.domain.ValueSetDto
import bug.service.ValueSetService
import io.swagger.v3.oas.annotations.Operation
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

private val LOGGER = KotlinLogging.logger {}

@RestController
@RequestMapping("/v1/parameters")
class ValueSetController(
    private val valueSetService: ValueSetService
) {

    @PutMapping("/{parameterCode}/values")
    @Operation(summary = "Agrega un valueSet al parámetro code")
    fun addValueSet(
        @PathVariable parameterCode: String,
        @RequestBody @Valid valueSetDto: SaveValueSetDto
    ): Mono<ValueSetDto> {
        LOGGER.info { "addValueSet|request. parameterCode=$parameterCode, valueSetDto=$valueSetDto" }

        val addedValueSet = valueSetService.addValueSet(parameterCode, valueSetDto)

        LOGGER.info { "addValueSet|response." }

        return addedValueSet
    }

}

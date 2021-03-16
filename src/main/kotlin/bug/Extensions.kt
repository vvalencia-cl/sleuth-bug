package bug

import bug.domain.*
import java.time.LocalDateTime

fun ValueSet.toDto(): ValueSetDto =
    with(this) {
        ValueSetDto(
            code,
            valueSetEntries.toValueSetEntriesDtoList(),
            enabled,
            public,
            createdOn,
            updatedOn,
            parameterCode
        )
    }

fun Set<ValueSetEntry>.toValueSetEntriesDtoList(): Set<ValueSetEntryDto> =
    this.map { it.toDto() }
        .toSet()

fun ValueSetEntry.toDto() =
    ValueSetEntryDto(this.key, this.value)

fun SaveValueSetDto.toModel(): ValueSet =
    with(this) {
        ValueSet(
            code,
            valueSetEntries.toValueSetEntryList(),
            enabled,
            public,
            LocalDateTime.now(),
            LocalDateTime.now(),
            parameterCode
        )
    }

fun Set<SaveValueSetEntryDto>.toValueSetEntryList(): Set<ValueSetEntry> =
    this.map { it.toModel() }
        .toSet()

fun SaveValueSetEntryDto.toModel(): ValueSetEntry =
    ValueSetEntry(this.key, this.value)

package bug.infrastructure.repository

import bug.domain.ValueSet
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ValueSetRepository : ReactiveMongoRepository<ValueSet, String> {

    fun findAllByParameterCode(parameterCode: String, page: Pageable): Flux<ValueSet>

    fun findByCodeAndParameterCode(code: String, parameterCode: String): Mono<ValueSet>

}

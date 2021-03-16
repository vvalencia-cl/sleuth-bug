package bug.infrastructure.repository

import bug.domain.Parameter
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ParameterRepository : ReactiveMongoRepository<Parameter, String> {

    fun findByCode(code: String): Mono<Parameter>

    fun findByIdNotNull(page: Pageable): Flux<Parameter>

    fun countAllByIdNotNull(): Mono<Long>

}

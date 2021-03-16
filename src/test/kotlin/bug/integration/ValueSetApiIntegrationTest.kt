package bug.integration

import bug.infrastructure.repository.ValueSetRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

internal class ValueSetApiIntegrationTest : IntegrationTest() {

    @Autowired
    private lateinit var valueSetRepository: ValueSetRepository

    @AfterEach
    fun clean() {
        valueSetRepository.deleteAll().block()
    }

    @Test
    fun `add parameter value should add and persist the new parameter value`() {
        val code = "p3"

        webTestClient.put().uri("/v1/parameters/{code}/values", code)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
{
      "code": "14c",
      "valueSetEntries": [
        {
          "key": "cantidad",
          "value": {
            "content": "14"
          }
        },
        {
          "key": "interes",
          "value": {
            "content": "0.04"
          }
        },
        {
          "key": "comentario",
          "value": {
            "content": "aplica interés"
          }  
        }
      ],
      "enabled": true,
      "parameterCode": "cuotas"
}
               """
            )
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody()
            .json(
                """
                    {
                       "code":"14c",
                       "parameterValueEntries":[
                          {
                             "key":"cantidad",
                             "value": {
                                "content": 14
                             }
                          },
                          {
                             "key":"interes",
                             "value": {
                                "content": 0.04
                             }
                          },
                          {
                             "key": "comentario",
                             "value": {
                                "content": "aplica interés"
                             }  
                          }
                       ],
                       "enabled":true,
                       "parameterCode":"cuotas"
                    }
                """.trimIndent()
            )
            .jsonPath("$.code").isEqualTo("14c")
            .jsonPath("$.enabled").isEqualTo(true)
            .jsonPath("$.createdOn").isNotEmpty
            .jsonPath("$.updatedOn").isNotEmpty
    }

}

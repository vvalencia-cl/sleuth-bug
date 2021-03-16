package bug

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ParametersApplication

fun main(args: Array<String>) {
    runApplication<ParametersApplication>(*args)
}

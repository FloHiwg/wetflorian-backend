package de.nist.wetflorianbackend.wetflorianbackend

import de.nist.wetflorianbackend.wetflorianbackend.config.ConfigProperties
import de.nist.wetflorianbackend.wetflorianbackend.controller.PlantStatusServiceThread
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(ConfigProperties::class)
class WetflorianBackendApplication

fun main(args: Array<String>) {

	val context = runApplication<WetflorianBackendApplication>(*args)
	val start = context.getBean(PlantStatusServiceThread::class.java)
	start.start()
}

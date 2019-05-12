package de.nist.wetflorianbackend.wetflorianbackend

import de.nist.wetflorianbackend.wetflorianbackend.controller.PlantStatusControllerThread
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class WetflorianBackendApplication

fun main(args: Array<String>) {

	val context = runApplication<WetflorianBackendApplication>(*args)
	val start = context.getBean(PlantStatusControllerThread::class.java)
	start.start()
}

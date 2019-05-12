package de.nist.wetflorianbackend.wetflorianbackend.controller

import de.nist.wetflorianbackend.wetflorianbackend.entity.Plant
import de.nist.wetflorianbackend.wetflorianbackend.entity.PlantStatus
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantRepository
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantStatusRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/plants")
class PlantController {
    lateinit var logger: Logger

    @Autowired
    lateinit var plantRepository: PlantRepository

    @Autowired
    lateinit var plantStatusRepository: PlantStatusRepository

    constructor() {
        logger = LoggerFactory.getLogger(PlantStatusController::class.java)
    }

    @GetMapping
    fun findAll() : List<Plant> {
        logger.info("All Plants requested")
        return plantRepository.findAll()
    }

    @GetMapping("/{plantId}")
    fun retrievePlant(@PathVariable plantId: Long) : Any {
        logger.info("Plant with id: " + plantId + " got requested")
        var plant = plantRepository.getOne(plantId)
        logger.info("Retrieve plant: " + plant)
        return plant
    }

    @GetMapping("/{plantId}/plant-status")
    fun retrievePlantStatusOfPlant(@PathVariable plantId: Long): List<PlantStatus> {
        logger.info("PlantStatus of plant with id: {plantId} got requested",plantId)
        var plantList = plantStatusRepository.findAll().filter{ it.plant!!.id == plantId }
        return plantList
    }

}
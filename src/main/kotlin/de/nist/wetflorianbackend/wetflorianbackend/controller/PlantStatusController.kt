package de.nist.wetflorianbackend.wetflorianbackend.controller

import de.nist.wetflorianbackend.wetflorianbackend.entity.PlantStatus
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantRepository
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantStatusRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plant-status")
class PlantStatusController {
    lateinit var logger: Logger

    @Autowired
    lateinit var plantRepository: PlantRepository

    @Autowired
    lateinit var plantStatusRepository: PlantStatusRepository

    constructor() {
        logger = LoggerFactory.getLogger(PlantStatusController::class.java)
    }

    @GetMapping
    fun findAll() {
        plantStatusRepository.findAll()
        logger.info("All PlantStatus requested")
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) {
        var plantStatus = plantStatusRepository.findById(id)
        logger.info("PlantStatus with id: " + id + " requested and PlantStatus retrieved: " + plantStatus)
    }

    @PostMapping
    fun addPlantStatus(@RequestBody plantStatus: PlantStatus) {
        var plantStatusCreated = plantStatusRepository.save(plantStatus)
        logger.info("New PlantStatus created: " + plantStatusCreated)
    }

    @PutMapping("/{id}")
    fun updatePlantStatus(@PathVariable id: Long, @RequestBody plantStatus: PlantStatus) {
        assert(plantStatus.id == id)
        var plantStatusUpdated = plantStatusRepository.save(plantStatus)
        logger.info("Updated PlantStatus. New status is: " + plantStatusUpdated)
    }

    @DeleteMapping("/{id}")
    fun removePlantStatus(@PathVariable id: Long) {
        var plantStatus = plantStatusRepository.deleteById(id)
        logger.info("Deleted PlantStatus with id : " + id + ", deleted Object: " + plantStatus)
    }
}

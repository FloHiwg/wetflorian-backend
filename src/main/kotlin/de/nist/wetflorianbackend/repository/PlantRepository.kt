package de.nist.wetflorianbackend.repository

import de.nist.wetflorianbackend.entity.Plant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantRepository : JpaRepository<Plant, Long> {
    fun findByIp(ip: String) : Plant?
}
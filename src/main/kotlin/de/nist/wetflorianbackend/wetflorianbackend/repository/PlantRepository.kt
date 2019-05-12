package de.nist.wetflorianbackend.wetflorianbackend.repository

import de.nist.wetflorianbackend.wetflorianbackend.entity.Plant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantRepository : JpaRepository<Plant, Long> {
    fun findByIp(ip: String) : Plant?
}
package de.nist.wetflorianbackend.wetflorianbackend.repository

import de.nist.wetflorianbackend.wetflorianbackend.entity.Plant
import de.nist.wetflorianbackend.wetflorianbackend.entity.PlantStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface PlantStatusRepository : JpaRepository<PlantStatus, Long>
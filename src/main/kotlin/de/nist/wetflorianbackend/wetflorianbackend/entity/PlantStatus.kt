package de.nist.wetflorianbackend.wetflorianbackend.entity;

import java.util.*
import javax.persistence.*

@Entity
data class PlantStatus (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @ManyToOne val plant: Plant?,
    val timestamp: Date,
    val value: String
)
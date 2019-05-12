package de.nist.wetflorianbackend.wetflorianbackend.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Plant(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    val ip: String,

    val name: String)


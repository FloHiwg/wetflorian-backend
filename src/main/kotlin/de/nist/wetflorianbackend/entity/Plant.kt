package de.nist.wetflorianbackend.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
data class Plant(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    val ip: String,

    val name: String) {

        constructor(ip: String, name: String) : this(0, ip, name)
}
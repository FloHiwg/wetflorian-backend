package de.nist.wetflorianbackend.controller

import de.nist.wetflorianbackend.config.ConfigProperties
import de.nist.wetflorianbackend.entity.Plant
import de.nist.wetflorianbackend.entity.PlantStatus
import de.nist.wetflorianbackend.repository.PlantRepository
import de.nist.wetflorianbackend.repository.PlantStatusRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PlantStatusServiceThread: Thread {

    var socket: DatagramSocket? = null
    var logger: Logger

    @Autowired
    lateinit var plantRepository: PlantRepository

    @Autowired
    lateinit var plantStatusRepository: PlantStatusRepository

    @Autowired
    lateinit var properties: ConfigProperties

    @Throws(IOException::class)
    constructor() {
        logger = LoggerFactory.getLogger(PlantStatusServiceThread::class.java)
    }

    override fun run() {
        socket = DatagramSocket(properties.port)
        logger.info("PlantStatusService started and listening on port: " + socket!!.port)
        while (true) {
            try {
                var plant: Plant?
                var plantStatus: PlantStatus?
                var reqBuf = ByteArray(256)

                //Receive Datagrampacket
                var packet = DatagramPacket(reqBuf, reqBuf.size)
                socket!!.receive(packet)

                //Save sender information
                val address = packet.address
                val hostname: String = packet.address.hostName
                val ip: String = packet.address.hostAddress
                val port = packet.port

                //Get payload
                val msg = String(packet.data, packet.offset, packet.length)
                logger.info("Datagram received: " + packet)

                //Check if sender is already known and if not, create a new one
                plant = plantRepository.findByIp(ip)
                if(plant == null) {
                    logger.info("No plant with same ip found. Create new plant entity")
                    plant = plantRepository.save(Plant(ip, hostname))
                    logger.info("New plant entity created: " + plant)
                } else {
                    plant = plantRepository.findByIp(ip)
                    logger.info("Plant entity found: " + plant)
                }

                //Create PlantStatus
                plantStatus = plantStatusRepository.save(PlantStatus(0, plant, Date(), msg))
                logger.info("New PlantStatus entity created: " + plantStatus)



                //
                var responsePayload: String? = "Message received: " + plant.toString()
                var responseBuf = responsePayload!!.toByteArray()

                packet = DatagramPacket(responseBuf, responseBuf.size, address, port)
                socket!!.send(packet)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        socket!!.close()
    }
}

package de.nist.wetflorianbackend.wetflorianbackend.controller

import de.nist.wetflorianbackend.wetflorianbackend.config.ConfigProperties
import de.nist.wetflorianbackend.wetflorianbackend.entity.Plant
import de.nist.wetflorianbackend.wetflorianbackend.entity.PlantStatus
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantRepository
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantStatusRepository
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
                var reqPacket = DatagramPacket(reqBuf, reqBuf.size)
                socket!!.receive(reqPacket)

                //Save sender tracermation
                val senderAddress = reqPacket.address
                val senderHostname: String = reqPacket.address.hostName
                val senderIp: String = reqPacket.address.hostAddress
                val senderPort = reqPacket.port

                //Get payload
                val senderMsg = String(reqPacket.data, reqPacket.offset, reqPacket.length)
                logger.trace("Datagram received: " + reqPacket)

                //Check if sender is already known and if not, create a new one
                plant = plantRepository.findByIp(senderIp)
                if(plant == null) {
                    logger.trace("No plant with same ip found. Create new plant entity")
                    plant = plantRepository.save(Plant(senderIp, senderHostname))
                    logger.trace("New plant entity created: " + plant)
                } else {
                    plant = plantRepository.findByIp(senderIp)
                    logger.trace("Plant entity found: " + plant)
                }

                //Create PlantStatus
                plantStatus = plantStatusRepository.save(PlantStatus(0, plant, Date(), senderMsg))
                logger.trace("New PlantStatus entity created: " + plantStatus)


                // Setup message
                var responsePayload: String? = "Message received: " + plant.toString()
                var responseBuf = responsePayload!!.toByteArray()

                reqPacket = DatagramPacket(responseBuf, responseBuf.size, senderAddress, senderPort)
                socket!!.send(reqPacket)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        socket!!.close()
    }
}

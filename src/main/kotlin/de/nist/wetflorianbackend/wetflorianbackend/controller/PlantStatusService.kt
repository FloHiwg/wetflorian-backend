package de.nist.wetflorianbackend.wetflorianbackend.controller

import de.nist.wetflorianbackend.wetflorianbackend.entity.Plant
import de.nist.wetflorianbackend.wetflorianbackend.entity.PlantStatus
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantRepository
import de.nist.wetflorianbackend.wetflorianbackend.repository.PlantStatusRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.*
import org.dom4j.dom.DOMNodeHelper.getData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.swing.text.html.parser.Parser

@Component
class PlantStatusControllerThread: Thread {

    var socket: DatagramSocket? = null

    @Autowired
    lateinit var plantRepository: PlantRepository

    @Autowired
    lateinit var plantStatusRepository: PlantStatusRepository

    lateinit var logger: Logger

    @Throws(IOException::class)
    constructor() {
        logger = LoggerFactory.getLogger(PlantStatusControllerThread::class.java)
    }

    override fun run() {
        socket = DatagramSocket(4444)
        logger.info("PlantStatusService started and listening on port: " + socket!!.port)
        while (true) {
            try {
                var plant: Plant?
                var plantStatus: PlantStatus?

                var buf = ByteArray(256)

                // receive request
                var packet = DatagramPacket(buf, buf.size)
                socket!!.receive(packet)

                val address = packet.address
                val hostname: String = packet.address.hostName
                val ip: String = packet.address.hostAddress
                val port = packet.port

                val msg = String(packet.data, packet.offset, packet.length)
                logger.info("Datagram received: " + packet)

                plant = plantRepository.findByIp(ip)

                if(plant == null) {
                    logger.info("No plant with same ip found. Create new plant entity")
                    plant = plantRepository.save(Plant(ip, hostname))
                    logger.info("New plant entity created: " + plant)
                } else {
                    plant = plantRepository.findByIp(ip)
                    logger.info("Plant entity found: " + plant)
                }

                plantStatus = plantStatusRepository.save(PlantStatus(0, plant, Date(), msg))
                logger.info("New PlantStatus entity created: " + plantStatus)



                // figure out response
                var dString: String? = "Message received: " + plant.toString()
                buf = dString!!.toByteArray()

                packet = DatagramPacket(buf, buf.size, address, port)
                socket!!.send(packet)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        socket!!.close()
    }
}

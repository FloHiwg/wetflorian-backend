package de.nist.wetflorianbackend.wetflorianbackend.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "wetflorian")
class ConfigProperties {
    var port: Int = 0
}
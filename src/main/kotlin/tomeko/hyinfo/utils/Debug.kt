package tomeko.hyinfo.utils

import tomeko.hyinfo.config.HyInfoConfig

//? if !forge {
import org.slf4j.Logger
import org.slf4j.LoggerFactory
//?}

object Debug {
    //? if !forge {
    private val LOGGER: Logger = LoggerFactory.getLogger(Constants.MOD_ID)
    //?}

    fun log(message: String) {
        if (!HyInfoConfig.debugModeEnabled) return

        forceLog(message)
    }

    fun forceLog(message: String) {
        //? if forge {
        //println("[${Constants.MOD_NAME}] $message")
        //?} else {
        LOGGER.info("[${Constants.MOD_NAME}] $message")
        //?}
    }
}
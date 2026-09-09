package tomeko.hymod.location

import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.clientbound.ClientboundHelloPacket
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket
//? if forge {
/*import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent
*///?} elif ornithe {
//import net.ornithemc.osl.networking.api.client.ClientConnectionEvents
//?} else {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
//?}
import tomeko.hymod.utils.Debug

object HypixelPackets {
    var onHypixel = false
        private set

    var currentServerName: String? = null
        private set

    var inLobby = false
        private set

    var inBedwars = false
        private set
    var inSkywars = false
        private set
    var inDuels = false
        private set
    var inArcade = false
        private set

    var inFarmHunt = false
        private set
    var duelsMode: DuelsMode = DuelsMode.OVERALL
        private set

    fun register() {
        HypixelModAPI.getInstance().createHandler(ClientboundHelloPacket::class.java, { onHypixel = true })
        //? if forge {
        //MinecraftForge.EVENT_BUS.register(this)
        //?} elif ornithe {
        //ClientConnectionEvents.DISCONNECT.register { disableHypixel() }
        //?} else {
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> disableHypixel() }
        //?}
        HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket::class.java, ::onLocationPacket)
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket::class.java)
    }

    //? if forge {
    //@SubscribeEvent
    //?}
    fun disableHypixel(
        //? if forge {
        //event: FMLNetworkEvent.ClientDisconnectionFromServerEvent
        //?}
    ) {
        onHypixel = false
    }

    private fun onLocationPacket(packet: ClientboundLocationPacket) {
        Debug.log("onHypixel: $onHypixel")

        if (!packet.serverType.isPresent) {
            disableAll()
            return
        }

        currentServerName = packet.serverName

        val serverTypeName = packet.serverType.get().name
        Debug.log("serverTypeName: $serverTypeName <")

        inLobby = packet.lobbyName.isPresent

        inBedwars = serverTypeName == "Bed Wars"
        inSkywars = serverTypeName == "SkyWars"
        inDuels = serverTypeName == "Duels"
        inArcade = serverTypeName == "Arcade"

        if (!packet.mode.isPresent) {
            disableModes()
            return
        }

        val modeName = packet.mode.get()
        Debug.log("modeName: $modeName <")

        inFarmHunt = inArcade && modeName == "FARM_HUNT"
        duelsMode = DuelsMode.fromId(modeName)
        inSkywars = inSkywars || duelsMode == DuelsMode.SKYWARS
        inDuels = inDuels || duelsMode != DuelsMode.OVERALL
    }

    private fun disableAll() {
        currentServerName = null
        inLobby = false
        disableServerTypes()
        disableModes()
    }

    private fun disableServerTypes() {
        inBedwars = false
        inDuels = false
        inArcade = false
    }

    private fun disableModes() {
        inFarmHunt = false
        duelsMode = DuelsMode.OVERALL
    }
}
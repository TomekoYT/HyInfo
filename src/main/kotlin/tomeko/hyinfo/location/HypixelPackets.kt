package tomeko.hyinfo.location

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
import tomeko.hyinfo.utils.Debug

object HypixelPackets {
    var onHypixel = false
        private set

    var inBedwars = false
        private set
    var inSkywars = false
        private set
    var inDuels = false
        private set
    var inMurderMystery = false
        private set

    var duelsMode: DuelsMode = DuelsMode.OVERALL
        private set
    var inAssassins = false
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
        disableAll()
    }

    private fun onLocationPacket(packet: ClientboundLocationPacket) {
        Debug.log("onHypixel: $onHypixel")

        if (!packet.serverType.isPresent) {
            disableAll()
            return
        }

        val serverTypeName = packet.serverType.get().name
        Debug.log("serverTypeName: $serverTypeName <")

        inBedwars = serverTypeName == "Bed Wars"
        inSkywars = serverTypeName == "SkyWars"
        inDuels = serverTypeName == "Duels"
        inMurderMystery = serverTypeName == "Murder Mystery"

        if (!packet.mode.isPresent) {
            disableModes()
            return
        }

        val modeName = packet.mode.get()
        Debug.log("modeName: $modeName <")

        duelsMode = DuelsMode.fromId(modeName)
        inSkywars = inSkywars || duelsMode == DuelsMode.SKYWARS
        inDuels = inDuels || duelsMode != DuelsMode.OVERALL
        inAssassins = inMurderMystery && modeName == "MURDER_ASSASSINS"
    }

    private fun disableAll() {
        disableServerTypes()
        disableModes()
    }

    private fun disableServerTypes() {
        inBedwars = false
        inSkywars = false
        inDuels = false
        inMurderMystery = false
    }

    private fun disableModes() {
        duelsMode = DuelsMode.OVERALL
        inAssassins = false
    }
}
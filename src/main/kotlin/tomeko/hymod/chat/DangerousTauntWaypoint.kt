package tomeko.hymod.chat

import net.minecraft.client.Minecraft
//? if 1.8.9 {
/*//? if forge {
//import cc.polyfrost.oneconfig.config.core.OneColor as PolyColor
//?}
import net.minecraft.block.Block
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
//? if ornithe {
//import net.minecraft.util.IChatComponent as Component
//?}
//? if forge {
/*import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
*///?}
*///?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Blocks
//?}
//? if !forge {
import org.polyfrost.compose.render.PolyColor
//?}

import tomeko.hymod.config.HyModConfig
//? if ornithe {
//import tomeko.hymod.event.ClientReceiveMessageEvents
//?}
import tomeko.hymod.location.HypixelPackets
import tomeko.hymod.utils.Waypoint
import tomeko.hymod.utils.WaypointRenderer
import tomeko.hymod.utils.removeFormatting

object DangerousTauntWaypoint {
    private const val SECONDS = 15

    fun register() {
        //? if forge {
        //MinecraftForge.EVENT_BUS.register(this)
        //?} else {
        ClientReceiveMessageEvents.GAME.register(::onChat)
        //?}
    }

    //? if forge {
    //@SubscribeEvent
    //?}

    fun onChat(
        //? if forge {
        //event: ClientChatReceivedEvent
        //?} else {
        component: Component,
        fromActionBar: Boolean
        //?}
    ) {
        //? if forge {
        //if (event.type.toInt() == 2 || event.message == null)
        //?} else {
        if (fromActionBar)
        //?}
            return

        if (!HyModConfig.debugModeEnabled && (!HyModConfig.dangerousTauntWaypointEnabled || !HypixelPackets.inFarmHunt)) return

        val message =
        //? if forge {
        //event.message.unformattedText.removeFormatting()
            //?} elif ornithe {
            //component.unformattedText.removeFormatting()
        //?} else {
        component.string.removeFormatting()
        //?}

        val words = message.split(" ")
        if (words.size != 12
            || (words[0] != "A" && words[0] != "An")
            || words[3] != "from"
            || words[5] != "blocks"
            || words[6] != "away!"
            || words[7] != "They"
            || words[8] != "are"
            || words[9] != "around:"
        ) return

        val animal = words[1]
        val x = words[10].substring(1, words[10].length - 1).toInt()
        val z = words[11].substring(0, words[11].length - 1).toInt()

        val boxColor = when (animal) {
            "Chicken" -> PolyColor(0x80FFFF00.toInt())
            "Sheep" -> PolyColor(0x80FFFFFF.toInt())
            "Pig" -> PolyColor(0x80FFC0CB.toInt())
            "Cow" -> PolyColor(0x80000000.toInt())
            "Horse" -> PolyColor(0x80964B00.toInt())
            "Ocelot" -> PolyColor(0x80F1E2C9.toInt())
            "Wolf" -> PolyColor(0x80D3D3D3.toInt())
            "Donkey" -> PolyColor(0x80808080.toInt())
            else -> PolyColor(0x8000FF00.toInt())
        }

        val beamColor = when (animal) {
            "Chicken" -> PolyColor(0xC0FFFF00.toInt())
            "Sheep" -> PolyColor(0xC0FFFFFF.toInt())
            "Pig" -> PolyColor(0xC0FFC0CB.toInt())
            "Cow" -> PolyColor(0xC0000000.toInt())
            "Horse" -> PolyColor(0xC0964B00.toInt())
            "Ocelot" -> PolyColor(0xC0F1E2C9.toInt())
            "Wolf" -> PolyColor(0xC0D3D3D3.toInt())
            "Donkey" -> PolyColor(0xC0808080.toInt())
            else -> PolyColor(0xC000FF00.toInt())
        }

        WaypointRenderer.waypoints.add(
            Waypoint(
                pos = getPos(x, z),
                boxColor = boxColor,
                beamColor = beamColor,
                owner = animal,
                renderOwner = true,
                ownerColor = PolyColor(0xFFFFFFFF.toInt()),
                text = "",
                renderText = false,
                textColor = PolyColor(0xFFFFFFFF.toInt()),
                renderDistance = true,
                distanceTextColor = PolyColor(0xFFFFFF00.toInt()),
                tickTime = 20 * SECONDS
            )
        )
    }

    private fun getPos(x: Int, z: Int): BlockPos {
        //? if 1.8.9 {
        //val world: WorldClient = Minecraft.getMinecraft().theWorld
        //?} else {
        val world: ClientLevel = Minecraft.getInstance().level!!
        //?}

        var pos = BlockPos(x, 0, z)

        while (world.getBlockState(pos).block ==
            //? if 1.8.9 {
            //Blocks.air
        //?} else {
        Blocks.AIR
        //?}
        ) {
            pos = oneHigher(pos)
        }

        while (world.getBlockState(pos).block !=
            //? if 1.8.9 {
            //Blocks.air
        //?} else {
        Blocks.AIR
        //?}
        ) {
            pos = oneHigher(pos)
        }

        return pos
    }

    private fun oneHigher(pos: BlockPos): BlockPos =
        //? if 1.8.9 {
        //pos.up()
    //?} else {
    pos.above()
    //?}
}
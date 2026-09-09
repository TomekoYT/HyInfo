package tomeko.hymod.chat

//? if forge {
/*import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
*///?} elif ornithe {
//import net.minecraft.util.IChatComponent as Component
//?} else {
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
//?}
import tomeko.hymod.config.HyModConfig
//? if ornithe {
//import tomeko.hymod.event.ClientReceiveMessageEvents
//?}
import tomeko.hymod.utils.removeFormatting

object HideGuildMOTD {
    private var guildMOTD = false

    fun register() {
        //? if forge {
        //MinecraftForge.EVENT_BUS.register(this)
        //?} else {
        ClientReceiveMessageEvents.ALLOW_GAME.register(::onChatReceive)
        //?}
    }

    //? if forge {
    //@SubscribeEvent
    //?}
    fun onChatReceive(
        //? if forge {
        //event: ClientChatReceivedEvent
        //?} else {
        message: Component, fromActionBar: Boolean
        //?}
    )
//? if !forge {
            : Boolean
    //?}
    {
        //? if forge {
        /*if (event.type.toInt() == 2 || event.message == null) return

        if (shouldCancel(event.message.unformattedText.removeFormatting())) {
            event.isCanceled = true
        }
        *///?} else {
        return fromActionBar
                || !shouldCancel(
            //? if ornithe {
            //message.unformattedText.removeFormatting()
            //?} else {
            message.string.removeFormatting()
            //?}
        )
        //?}
    }

    private fun shouldCancel(message: String): Boolean {
        if (!HyModConfig.hideGuildMOTDEnabled) return false

        if (message.startsWith("--------------  Guild: Message Of The Day  --------------")) {
            guildMOTD = true
        }

        if (guildMOTD) {
            if (message.endsWith("-----------------------------------------------------")) {
                guildMOTD = false
            }
            return true
        }

        return false
    }
}
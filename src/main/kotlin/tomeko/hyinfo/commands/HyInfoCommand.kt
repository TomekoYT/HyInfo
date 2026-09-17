package tomeko.hyinfo.commands

//? if forge {
/*import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
*///?} else {
//? if fabric {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
//?}
//? if ornithe {
/*import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents
import org.polyfrost.oneconfig.api.commands.v1.CommandManager.literal
*///?}
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
//? if ornithe {
//import org.polyfrost.oneconfig.internal.legacy.command.ClientCommandRegistrationCallback
//?}
//?}
import tomeko.hyinfo.config.HyInfoConfig
import tomeko.hyinfo.utils.Constants

//? if forge {
//@Command(value = Constants.MOD_ID)
//?}
object HyInfoCommand {
    //? if !forge {
    private var shouldOpenConfig: Boolean = false
    //?}

    fun register() {
        //? if forge {
        //CommandManager.INSTANCE.registerCommand(this)
        //?} else {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal(Constants.MOD_ID)
                    .executes { _ ->
                        shouldOpenConfig = true
                        return@executes 1
                    }
            )
        }

        //? if ornithe {
        //MinecraftClientEvents.TICK_END.register {
            //?} else {
        ClientTickEvents.END_CLIENT_TICK.register {
            //?}
            if (!shouldOpenConfig) return@register

            HyInfoConfig.openUI()

            shouldOpenConfig = false
        }
        //?}
    }

    //? if forge {
    /*@Main
    fun handle() {
        HyInfoConfig.openGui()
    }
    *///?}
}
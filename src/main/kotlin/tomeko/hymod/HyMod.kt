package tomeko.hymod

//? if forge {
/*import cc.polyfrost.oneconfig.events.EventManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
*///?} elif ornithe {
//import net.ornithemc.osl.entrypoints.api.ModInitializer
//?} else {
import net.fabricmc.api.ClientModInitializer
//?}
import tomeko.hymod.chat.*
import tomeko.hymod.commands.*
import tomeko.hymod.config.*
import tomeko.hymod.hud.*
import tomeko.hymod.location.*
import tomeko.hymod.stats.*
import tomeko.hymod.utils.*

//? if forge {
/*@Mod(
    modid = Constants.MOD_ID,
    name = Constants.MOD_NAME,
    version = Constants.MOD_VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter",
    dependencies = "required-after:hypixel_mod_api"
)
*///?}
class HyMod
//? if ornithe {
//: ModInitializer
//?} elif fabric {
    : ClientModInitializer
//?}
{
    //? if forge {
    //@Mod.EventHandler
    //?} else {
    override
    //?}
    fun
    //? if ornithe {
    //init(
    //?} else {
            onInitializeClient(
        //?}
        //? if forge {
        //event: FMLInitializationEvent
        //?}
    ) {
        //? if forge {
        //EventManager.INSTANCE.register(this)
        //?}

        CoordsWaypoints.register()
        DangerousTauntWaypoint.register()
        HideGuildMOTD.register()
        //? if fabric {
        MVPEmoji.register()
        //?}
        WhiteChatMessages.register()

        HyModCommand.register()
        SendCoordsCommand.register()

        HyModConfig.register()

        //? if !forge {
        BedwarsResourceDisplay.register()
        //?}

        HypixelPackets.register()

        NametagStats.register()

        ItemTracker.register()
        WaypointRenderer.register()

        Debug.forceLog("${Constants.MOD_VERSION} Initialized!")
    }
}
package tomeko.hyinfo.config

//? if forge {
/*import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor as PolyColor
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
*///?} else {
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.*
//?}
//? if forge {
//import tomeko.hyinfo.hud.BedwarsResourceDisplay
//?}
import tomeko.hyinfo.utils.Constants

object HyInfoConfig : Config(
    //? if forge {
    /*Mod(
        Constants.MOD_NAME,
        ModType.HYPIXEL,
        Constants.MOD_ICON
    ),
    "${Constants.MOD_ID}.json"
    *///?} else {
    "${Constants.MOD_ID}.json",
    Constants.MOD_ICON,
    Constants.MOD_NAME,
    Category.HYPIXEL
    //?}
) {
    //? if !forge {
    val DEPENDENCIES: List<Pair<String, List<String>>> = listOf(
    )
    //?}

    fun register() {
        //? if forge {
        //initialize()
        //?} else {
        preload()
        for ((condition, dependencies) in DEPENDENCIES) {
            for (dependency in dependencies) {
                addDependency(dependency, condition)
            }
        }
        //?}
    }

    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_BEDWARS = "BedWars"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show BedWars Stars In Tablist",
        description = "Show in tablist stars of every player while in Hypixel BedWars",
        category = CATEGORY_BEDWARS,
    )
    var showBedwarsStarsInTablist = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show BedWars Stars Above Nametag",
        description = "Show above nametag stars of every player while in Hypixel BedWars",
        category = CATEGORY_BEDWARS,
    )
    var showBedwarsStarsAboveNametag = true

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "BedWars Text Above Nametag",
        category = CATEGORY_BEDWARS,
    )
    var bedwarsTextAboveNametag = "§fBed§cWars§f: "

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_BEDWARS,
    )
    private fun bedwarsTextResetButton() {
        bedwarsTextAboveNametag = "§fBed§cWars§f: "
    }
    *///?}


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_SKYWARS = "SkyWars"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show SkyWars Stars In TabList",
        description = "Show in tablist stars of every player while in Hypixel SkyWars",
        category = CATEGORY_SKYWARS,
    )
    var showSkywarsStarsInTablist = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show SkyWars Stars Above Nametag",
        description = "Show above nametag stars of every player while in Hypixel SkyWars",
        category = CATEGORY_SKYWARS,
    )
    var showSkywarsStarsAboveNametag = true

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "SkyWars Text Above Nametag",
        category = CATEGORY_SKYWARS,
    )
    var skywarsTextAboveNametag = "§bSky§aWars§f: "

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_SKYWARS,
    )
    private fun skywarsTextResetButton() {
        skywarsTextAboveNametag = "§bSky§aWars§f: "
    }
    *///?}


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_DUELS = "Duels"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Duels Division In Tablist",
        description = "Show in tablist division of every player while in Hypixel Duels",
        category = CATEGORY_DUELS,
    )
    var showDuelsDivisionInTablist = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Duels Division Above Nametag",
        description = "Show above nametag division of every player while in Hypixel Duels",
        category = CATEGORY_DUELS,
    )
    var showDuelsDivisionAboveNametag = true

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var duelsTextAboveNametag = " §3Duels§f: "

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Overall Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var overallDuelsTextAboveNametag = "§eOverall"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "SkyWars Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var skywarsDuelsTextAboveNametag = "§bSky§aWars"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "The Bridge Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var theBridgeDuelsTextAboveNametag = "§5The Bridge"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
        "BedWars Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var bedwarsDuelsTextAboveNametag = "§fBed§cWars"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Classic Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var classicDuelsTextAboveNametag = "§fClassic"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "UHC Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var uhcDuelsTextAboveNametag = "§6UHC"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Sumo Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var sumoDuelsTextAboveNametag = "§bSumo"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Bow Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var bowDuelsTextAboveNametag = "§6Bow"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Mega Walls Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var megaWallsDuelsTextAboveNametag = "§8Mega Walls"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Parkour Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var parkourDuelsTextAboveNametag = "§eParkour"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Quakecraft Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var quakecraftDuelsTextAboveNametag = "§7Quakecraft"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Spleef Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var spleefDuelsTextAboveNametag = "§9Spleef"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "OP Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var opDuelsTextAboveNametag = "§5OP"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Blitz Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var blitzDuelsTextAboveNametag = "§6Blitz"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Combo Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var comboDuelsTextAboveNametag = "§cCombo"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Boxing Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var boxingDuelsTextAboveNametag = "§4Boxing"

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "NoDebuff Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var noDebuffDuelsTextAboveNametag = "§dNoDebuff"

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_DUELS,
    )
    private fun duelsTextResetButton() {
        duelsTextAboveNametag = " §3Duels§f: "
        overallDuelsTextAboveNametag = "§eOverall"
        skywarsDuelsTextAboveNametag = "§bSky§aWars"
        theBridgeDuelsTextAboveNametag = "§5The Bridge"
        bedwarsDuelsTextAboveNametag = "§fBed§cWars"
        classicDuelsTextAboveNametag = "§fClassic"
        uhcDuelsTextAboveNametag = "§6UHC"
        sumoDuelsTextAboveNametag = "§bSumo"
        bowDuelsTextAboveNametag = "§6Bow"
        megaWallsDuelsTextAboveNametag = "§8Mega Walls"
        parkourDuelsTextAboveNametag = "§eParkour"
        quakecraftDuelsTextAboveNametag = "§7Quakecraft"
        spleefDuelsTextAboveNametag = "§9Spleef"
        opDuelsTextAboveNametag = "§5OP"
        blitzDuelsTextAboveNametag = "§6Blitz"
        comboDuelsTextAboveNametag = "§cCombo"
        boxingDuelsTextAboveNametag = "§4Boxing"
        noDebuffDuelsTextAboveNametag = "§dNoDebuff"
    }
    *///?}

    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_NETWORK = "Network"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_NETWORK_STATS = "Stats"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Network Level Above Nametag",
        description = "Show above nametag network level of every player while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var showNetworkLevelAboveNametag = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Network Level with Other Nametag Stats",
        description = "Show Hypixel network level when other Hypixel stats are shown",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var showNetworkLevelWithOtherNametagStats = true

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Network Level Text Above Nametag",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var networkLevelTextAboveNametag = "§9Level§f: §e"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_NICKED_INDICATOR = "Nicked Indicator"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Nicked Indicator In Tablist",
        description = "Show indicator of a nicked player in tablist while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var showNickedIndicatorInTablist = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Nicked Indicator Above Nametag",
        description = "Show indicator of a nicked player above nametag while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var showNickedIndicatorAboveNametag = true

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Nicked Indicator Text",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var nickedIndicatorText = "§5[NICKED]"

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    private fun networkTextResetButton() {
        networkLevelTextAboveNametag = "§9Level§f: §e"
        nickedIndicatorText = "§5[NICKED]"
    }
    *///?}

    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_SETTINGS = "Settings"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_NAMETAGS = "Nametags"

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Nametag Position Offset",
        min = 0.1f,
        max = 1f,
        //? if !forge {
        step = 0.1f,
        //?}
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_NAMETAGS
    )
    var nametagOffset = 0.1f

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Nametag Background Color",
        //? if forge {
        //allowAlpha = true,
        //?}
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_NAMETAGS
    )
    var nametagBackgroundColor = PolyColor(0x50000000.toInt())

    @Text(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Rate Limited Indicator Text",
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_NAMETAGS
    )
    var rateLimitedIndicatorText = "§4[RATE LIMITED]"


    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_DEBUG = "Debug"

    @Info(
        //? if forge {
        //text =
        //?} else {
        title =
        //?}
        "Probably should stay disabled",
        //? if forge {
        //type = InfoType.WARNING,
        //?}
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_DEBUG
    )
    var debugModeInfo: Nothing? = null

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Debug Mode",
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_DEBUG
    )
    var debugModeEnabled = false
}
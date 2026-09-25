package tomeko.hyinfo.config

import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import tomeko.hyinfo.utils.Constants

object HyInfoConfig : Config(
    "${Constants.MOD_ID}.json",
    Constants.MOD_ICON,
    Constants.MOD_NAME,
    Category.HYPIXEL
) {
    val DEPENDENCIES: List<Pair<String, List<String>>> = listOf(
    )

    fun register() {
        preload()
        for ((condition, dependencies) in DEPENDENCIES) {
            for (dependency in dependencies) {
                addDependency(dependency, condition)
            }
        }
    }

    private const val CATEGORY_BEDWARS = "BedWars"

    @Switch(
        title = "Show BedWars Stars In Tablist",
        description = "Show in tablist stars of every player while in Hypixel BedWars",
        category = CATEGORY_BEDWARS,
    )
    var showBedwarsStarsInTablist = true

    @Switch(
        title = "Show BedWars Stars Above Nametag",
        description = "Show above nametag stars of every player while in Hypixel BedWars",
        category = CATEGORY_BEDWARS,
    )
    var showBedwarsStarsAboveNametag = true

    @Text(
        title = "BedWars Text Above Nametag",
        category = CATEGORY_BEDWARS,
    )
    var bedwarsTextAboveNametag = "§fBed§cWars§f: "


    private const val CATEGORY_SKYWARS = "SkyWars"

    @Switch(
        title = "Show SkyWars Stars In TabList",
        description = "Show in tablist stars of every player while in Hypixel SkyWars",
        category = CATEGORY_SKYWARS,
    )
    var showSkywarsStarsInTablist = true

    @Switch(
        title = "Show SkyWars Stars Above Nametag",
        description = "Show above nametag stars of every player while in Hypixel SkyWars",
        category = CATEGORY_SKYWARS,
    )
    var showSkywarsStarsAboveNametag = true

    @Text(
        title = "SkyWars Text Above Nametag",
        category = CATEGORY_SKYWARS,
    )
    var skywarsTextAboveNametag = "§bSky§aWars§f: "


    private const val CATEGORY_DUELS = "Duels"

    @Switch(
        title = "Show Duels Division In Tablist",
        description = "Show in tablist division of every player while in Hypixel Duels",
        category = CATEGORY_DUELS,
    )
    var showDuelsDivisionInTablist = true

    @Switch(
        title = "Show Duels Division Above Nametag",
        description = "Show above nametag division of every player while in Hypixel Duels",
        category = CATEGORY_DUELS,
    )
    var showDuelsDivisionAboveNametag = true

    @Text(
        title = "Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var duelsTextAboveNametag = " §3Duels§f: "

    @Text(
        title = "Overall Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var overallDuelsTextAboveNametag = "§eOverall"

    @Text(
        title = "SkyWars Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var skywarsDuelsTextAboveNametag = "§bSky§aWars"

    @Text(
        title = "The Bridge Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var theBridgeDuelsTextAboveNametag = "§5The Bridge"

    @Text(
        title = "BedWars Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var bedwarsDuelsTextAboveNametag = "§fBed§cWars"

    @Text(
        title = "Classic Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var classicDuelsTextAboveNametag = "§fClassic"

    @Text(
        title = "UHC Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var uhcDuelsTextAboveNametag = "§6UHC"

    @Text(
        title = "Sumo Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var sumoDuelsTextAboveNametag = "§bSumo"

    @Text(
        title = "Bow Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var bowDuelsTextAboveNametag = "§6Bow"

    @Text(
        title = "Mega Walls Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var megaWallsDuelsTextAboveNametag = "§8Mega Walls"

    @Text(
        title = "Parkour Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var parkourDuelsTextAboveNametag = "§eParkour"

    @Text(
        title = "Quakecraft Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var quakecraftDuelsTextAboveNametag = "§7Quakecraft"

    @Text(
        title = "Spleef Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var spleefDuelsTextAboveNametag = "§9Spleef"

    @Text(
        title = "OP Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var opDuelsTextAboveNametag = "§5OP"

    @Text(
        title = "Blitz Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var blitzDuelsTextAboveNametag = "§6Blitz"

    @Text(
        title = "Combo Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var comboDuelsTextAboveNametag = "§cCombo"

    @Text(
        title = "Boxing Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var boxingDuelsTextAboveNametag = "§4Boxing"

    @Text(
        title = "NoDebuff Duels Text Above Nametag",
        category = CATEGORY_DUELS,
    )
    var noDebuffDuelsTextAboveNametag = "§dNoDebuff"


    private const val CATEGORY_NETWORK = "Network"

    private const val SUBCATEGORY_NETWORK_STATS = "Stats"

    @Switch(
        title = "Show Network Level Above Nametag",
        description = "Show above nametag network level of every player while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var showNetworkLevelAboveNametag = true

    @Switch(
        title = "Show Network Level with Other Nametag Stats",
        description = "Show Hypixel network level when other Hypixel stats are shown",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var showNetworkLevelWithOtherNametagStats = true

    @Text(
        title = "Network Level Text Above Nametag",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var networkLevelTextAboveNametag = "§9Level§f: §e"


    private const val SUBCATEGORY_NICKED_INDICATOR = "Nicked Indicator"

    @Switch(
        title = "Show Nicked Indicator In Tablist",
        description = "Show indicator of a nicked player in tablist while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var showNickedIndicatorInTablist = true

    @Switch(
        title = "Show Nicked Indicator Above Nametag",
        description = "Show indicator of a nicked player above nametag while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var showNickedIndicatorAboveNametag = true

    @Text(
        title = "Nicked Indicator Text",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NICKED_INDICATOR
    )
    var nickedIndicatorText = "§5[NICKED]"


    private const val CATEGORY_SETTINGS = "Settings"

    private const val SUBCATEGORY_NAMETAGS = "Nametags"

    @Text(
        title = "Rate Limited Indicator Text",
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_NAMETAGS
    )
    var rateLimitedIndicatorText = "§4[RATE LIMITED]"


    private const val SUBCATEGORY_DEBUG = "Debug"

    @Info(
        title = "Probably should stay disabled",
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_DEBUG
    )
    var debugModeInfo: Nothing? = null

    @Switch(
        title = "Debug Mode",
        category = CATEGORY_SETTINGS,
        subcategory = SUBCATEGORY_DEBUG
    )
    var debugModeEnabled = false
}
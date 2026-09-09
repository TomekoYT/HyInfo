package tomeko.hymod.config

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
//import tomeko.hymod.hud.BedwarsResourceDisplay
//?}
import tomeko.hymod.utils.Constants

object HyModConfig : Config(
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
        "coordsWaypointsEnabled" to listOf(
            "coordsWaypointsBoxColor",
            "coordsWaypointsBeamColor",
            "coordsWaypointsRenderOwner",
            "coordsWaypointsOwnerColor",
            "coordsWaypointsRenderText",
            "coordsWaypointsTextColor",
            "coordsWaypointsRenderDistance",
            "coordsWaypointsDistanceTextColor",
            "coordsWaypointsTime"
        ),
        "showNetworkLevelAboveNametag" to listOf(
            "showNetworkLevelWithOtherNametagStats"
        )
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

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_SHOP = "Shop"

    @Switch(
        //? if forge {
        //name =
            //?} else {
            title =
                //?}
        "Use Middle Click in Shop",
        description = "Replace middle click with left click in BedWars item shop",
        category = CATEGORY_BEDWARS,
        subcategory = SUBCATEGORY_SHOP
    )
    var middleClickInBedwarsShop = true


    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_BEDWARS_STATS = "Stats"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show BedWars Stars In Tablist",
        description = "Show in tablist stars of every player while in Hypixel BedWars",
        category = CATEGORY_BEDWARS,
        subcategory = SUBCATEGORY_BEDWARS_STATS,
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
        subcategory = SUBCATEGORY_BEDWARS_STATS,
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
        subcategory = SUBCATEGORY_BEDWARS_STATS,
    )
    var bedwarsTextAboveNametag = "§fBed§cWars§f: "

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_BEDWARS,
        subcategory = SUBCATEGORY_BEDWARS_STATS
    )
    private fun bedwarsTextResetButton() {
        bedwarsTextAboveNametag = "§fBed§cWars§f: "
    }
    *///?}


    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_BEDWARS_RESOURCE_DISPLAY = "Resource Display"

    //? if forge {
    /*@HUD(
        name = SUBCATEGORY_BEDWARS_RESOURCE_DISPLAY,
        category = CATEGORY_BEDWARS
    )
    var bedwarsResourceDisplay = BedwarsResourceDisplay()
    *///?} else {
    @Info(
        title = "Resource Display can be edited by clicking Edit HUD in the top left corner",
        category = CATEGORY_BEDWARS,
        subcategory = SUBCATEGORY_BEDWARS_RESOURCE_DISPLAY
    )
    var bedwarsResourceDisplayInfo: Nothing? = null
    //?}


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_SKYWARS = "SkyWars"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_SKYWARS_STATS = "Stats"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show SkyWars Stars In TabList",
        description = "Show in tablist stars of every player while in Hypixel SkyWars",
        category = CATEGORY_SKYWARS,
        subcategory = SUBCATEGORY_SKYWARS_STATS,
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
        subcategory = SUBCATEGORY_SKYWARS_STATS,
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
        subcategory = SUBCATEGORY_SKYWARS_STATS,
    )
    var skywarsTextAboveNametag = "§bSky§aWars§f: "

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_SKYWARS,
        subcategory = SUBCATEGORY_SKYWARS_STATS
    )
    private fun skywarsTextResetButton() {
        skywarsTextAboveNametag = "§bSky§aWars§f: "
    }
    *///?}


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_DUELS = "Duels"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_DUELS_STATS = "Stats"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Duels Division In Tablist",
        description = "Show in tablist division of every player while in Hypixel Duels",
        category = CATEGORY_DUELS,
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
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
        subcategory = SUBCATEGORY_DUELS_STATS,
    )
    var noDebuffDuelsTextAboveNametag = "§dNoDebuff"

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_DUELS,
        subcategory = SUBCATEGORY_DUELS_STATS
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
    private const val CATEGORY_ARCADE = "Arcade"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_FARM_HUNT = "Farm Hunt"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Dangerous Taunt Waypoint",
        description = "Show waypoint on dangerous taunt message in Farm Hunt",
        category = CATEGORY_ARCADE,
        subcategory = SUBCATEGORY_FARM_HUNT
    )
    var dangerousTauntWaypointEnabled = true


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

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Nicked Indicator In Tablist",
        description = "Show indicator of a nicked player in tablist while on Hypixel",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
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
        subcategory = SUBCATEGORY_NETWORK_STATS
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
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    var nickedIndicatorText = "§5[NICKED]"

    //? if forge {
    /*@Button(
        name = "",
        text = "Reset Text",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_NETWORK_STATS
    )
    private fun networkTextResetButton() {
        networkLevelTextAboveNametag = "§9Level§f: §e"
        nickedIndicatorText = "§5[NICKED]"
    }
    *///?}


    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_MIDDLE_CLICK_GUI_ITEMS = "Middle Click GUI Items in Lobby"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Middle Click GUI Items",
        description = "Replace left click with middle click in GUIs in Hypixel lobbies",
        category = CATEGORY_NETWORK,
        subcategory = SUBCATEGORY_MIDDLE_CLICK_GUI_ITEMS
    )
    var middleClickInLobby = true


    //? if forge {
    //@Exclude
    //?}
    private const val CATEGORY_CHAT = "Chat"

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_WHITE_CHAT_MESSAGES = "White Chat Messages"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "White Private Messages",
        description = "Color private messages white instead of gray on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_WHITE_CHAT_MESSAGES
    )
    var whitePrivateMessagesEnabled = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "White No Rank Messages",
        description = "Color messages from players with no rank white instead of gray on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_WHITE_CHAT_MESSAGES
    )
    var whiteNoRankMessagesEnabled = true

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_HIDE_GUILD_MOTD = "Hide Guild MOTD"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Hide Guild MOTD",
        description = "Hide guild message of the day on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_HIDE_GUILD_MOTD
    )
    var hideGuildMOTDEnabled = false

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_MVP_EMOJIS = "MVP++ Emojis"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "MVP++ Emojis",
        description = "Replace emojis like <3 with ❤ on Hypixel",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_MVP_EMOJIS
    )
    var mvpEmojisEnabled = true

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_SENDCOORDS_COMMAND = "/sendcoords Command"

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Default Mode",
        description = "Set default /sendcoords command mode",
        options = [
            "All",
            "Party",
            "Guild"
        ],
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_SENDCOORDS_COMMAND
    )
    var sendcoordsMode = 1

    //? if forge {
    //@Exclude
    //?}
    private const val SUBCATEGORY_COORDS_WAYPOINTS = "Coords Waypoints"

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Coords Waypoints",
        description = "Show waypoint on coords from chat",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsEnabled = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Box Color",
        //? if forge {
        //allowAlpha = true,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsBoxColor = PolyColor(0xFFFFFFFF.toInt())

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Box Color",
        //? if forge {
        //allowAlpha = true,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsBeamColor = PolyColor(0xC0FFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Owner",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderOwner = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Owner Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsOwnerColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Text",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderText = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Text Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsTextColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Render Distance",
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsRenderDistance = true

    @Color(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Distance Text Color",
        //? if forge {
        //allowAlpha = false,
        //?} else {
        alpha = false,
        //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsDistanceTextColor = PolyColor(0xFFFFFF00.toInt())

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Time",
        min = 0f,
        max = 120f,
        step =
            //? if forge {
            //1,
        //?} else {
        1f,
    //?}
        category = CATEGORY_CHAT,
        subcategory = SUBCATEGORY_COORDS_WAYPOINTS
    )
    var coordsWaypointsTime = 60

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
package tomeko.hyinfo.location

import tomeko.hyinfo.config.HyInfoConfig

enum class DuelsModeType {
    OVERALL,
    SHORT,
    LONG
}

enum class DuelsMode(
    val modeId: String,
    val modeName: String,
    val modeType: DuelsModeType
) {
    OVERALL("OVERALL", HyInfoConfig.overallDuelsTextAboveNametag, DuelsModeType.OVERALL),
    SKYWARS("DUELS_SW_\\w+", HyInfoConfig.skywarsDuelsTextAboveNametag, DuelsModeType.SHORT),
    THE_BRIDGE("DUELS_BRIDGE_\\w+", HyInfoConfig.theBridgeDuelsTextAboveNametag, DuelsModeType.LONG),
    BEDWARS("BEDWARS_TWO_ONE_DUELS(?:_RUSH)?", HyInfoConfig.bedwarsDuelsTextAboveNametag, DuelsModeType.SHORT),
    CLASSIC("DUELS_CLASSIC_\\w+", HyInfoConfig.classicDuelsTextAboveNametag, DuelsModeType.SHORT),
    UHC("DUELS_UHC_\\w+", HyInfoConfig.uhcDuelsTextAboveNametag, DuelsModeType.SHORT),
    SUMO("DUELS_SUMO_DUEL", HyInfoConfig.sumoDuelsTextAboveNametag, DuelsModeType.SHORT),
    BOW("DUELS_BOW_DUEL", HyInfoConfig.bowDuelsTextAboveNametag, DuelsModeType.SHORT),
    MEGA_WALLS("DUELS_MW_DUEL", HyInfoConfig.megaWallsDuelsTextAboveNametag, DuelsModeType.LONG),
    PARKOUR("DUELS_PARKOUR_EIGHT", HyInfoConfig.parkourDuelsTextAboveNametag, DuelsModeType.LONG),
    QUAKECRAFT("DUELS_QUAKE_DUEL", HyInfoConfig.quakecraftDuelsTextAboveNametag, DuelsModeType.SHORT),
    SPLEEF("DUELS_(?:BOW)?SPLEEF_DUEL", HyInfoConfig.spleefDuelsTextAboveNametag, DuelsModeType.SHORT),
    OP("DUELS_OP_\\w+", HyInfoConfig.opDuelsTextAboveNametag, DuelsModeType.SHORT),
    BLITZ("DUELS_BLITZ_DUEL", HyInfoConfig.blitzDuelsTextAboveNametag, DuelsModeType.SHORT),
    COMBO("DUELS_COMBO_DUEL", HyInfoConfig.comboDuelsTextAboveNametag, DuelsModeType.SHORT),
    BOXING("DUELS_BOXING_DUEL", HyInfoConfig.boxingDuelsTextAboveNametag, DuelsModeType.LONG),
    NO_DEBUFF("DUELS_POTION_DUEL", HyInfoConfig.noDebuffDuelsTextAboveNametag, DuelsModeType.LONG);

    companion object {
        fun fromId(id: String): DuelsMode =
            entries.firstOrNull { it != OVERALL && Regex(it.modeId).matches(id) } ?: OVERALL
    }
}
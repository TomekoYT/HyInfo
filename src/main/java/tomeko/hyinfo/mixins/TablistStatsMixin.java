package tomeko.hyinfo.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import tomeko.hyinfo.utils.FormattedTextCache;

import java.util.Map;
import java.util.WeakHashMap;
*///?} else {
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tomeko.hyinfo.config.HyInfoConfig;
import tomeko.hyinfo.location.HypixelPackets;
import tomeko.hyinfo.stats.HypixelStatsFetcher;

import java.util.UUID;

@Mixin(
        //? if 1.8.9
        //GuiPlayerTabOverlay.class
        //? else
        PlayerTabOverlay.class
)
abstract class TablistStatsMixin {
    //? if 1.8.9 {
    /*@Unique
    private static final Map<NetworkPlayerInfo, FormattedTextCache> hyinfo$formattedNames = new WeakHashMap<>();

    *///?}
    @Inject(
            method =
                    //? if 1.8.9
                    //"getPlayerName",
                    //? else
                    "getNameForDisplay",
            at = @At("RETURN"),
            cancellable = true
    )
    private void hyinfo$tablistStats(
            //? if 1.8.9 {
            /*NetworkPlayerInfo info,
            CallbackInfoReturnable<String> cir
            *///?} else {
            PlayerInfo info,
            CallbackInfoReturnable<Component> cir
            //?}
    ) {
        if (!HypixelPackets.INSTANCE.getOnHypixel()) return;

        //? if 1.8.9
        //String original = cir.getReturnValue();
        //? else
        Component original = cir.getReturnValue();

        UUID uuid =
                //? if 1.8.9
                //info.getGameProfile().getId();
                //? else
                info.getProfile().id();

        if (uuid.version() != 1 && uuid.version() != 4) return;

        boolean modifyPrefixWithStats = (HypixelPackets.INSTANCE.getInBedwars() && HyInfoConfig.INSTANCE.getShowBedwarsStarsInTablist())
                || (HypixelPackets.INSTANCE.getInSkywars() && HyInfoConfig.INSTANCE.getShowSkywarsStarsInTablist())
                || (HypixelPackets.INSTANCE.getInDuels() && HyInfoConfig.INSTANCE.getShowDuelsDivisionInTablist());

        String text = null;
        //? if 1.8.9 {
        /*IChatComponent bedwars = null;
        IChatComponent skywars = null;
        IChatComponent duels = null;
        *///?} else {
        Component bedwars = null;
        Component skywars = null;
        Component duels = null;
        //?}
        if (uuid.version() == 1) {
            if (HyInfoConfig.INSTANCE.getShowNickedIndicatorInTablist() && !HypixelPackets.INSTANCE.getInAssassins()) {
                text = HyInfoConfig.INSTANCE.getNickedIndicatorText();
            }
        } else if (modifyPrefixWithStats) {
            String id = uuid.toString();
            HypixelStatsFetcher.INSTANCE.requestStats(id);
            HypixelStatsFetcher.CachedStats stats = HypixelStatsFetcher.INSTANCE.getCachedStats(id);

            if (HypixelStatsFetcher.INSTANCE.getRateLimitedIndicators().contains(id)) {
                if (HyInfoConfig.INSTANCE.getShowRateLimitedIndicatorInTablist()) {
                    text = HyInfoConfig.INSTANCE.getRateLimitedIndicatorText();
                }
            } else {
                if (HypixelPackets.INSTANCE.getInBedwars() && HyInfoConfig.INSTANCE.getShowBedwarsStarsInTablist()) {
                    bedwars = stats.getBedwars();
                }

                if (HypixelPackets.INSTANCE.getInSkywars() && HyInfoConfig.INSTANCE.getShowSkywarsStarsInTablist()) {
                    skywars = stats.getSkywars();
                }

                if (HypixelPackets.INSTANCE.getInDuels() && HyInfoConfig.INSTANCE.getShowDuelsDivisionInTablist()) {
                    duels = stats.getDuels();
                }
            }
        }

        if (text == null && bedwars == null && skywars == null && duels == null) return;

        //? if 1.8.9 {
        /*FormattedTextCache cache = hyinfo$formattedNames.computeIfAbsent(info, key -> new FormattedTextCache(6))
                .key(0, original)
                .key(1, text)
                .key(2, bedwars)
                .key(3, skywars)
                .key(4, duels)
                .key(5, HyInfoConfig.INSTANCE.getShowMultipleStatsInTablist());
        String formatted = cache.get();
        if (formatted == null) {
            formatted = cache.put(hyinfo$prefix(text, bedwars, skywars, duels).createCopy().appendSibling(new ChatComponentText(" ")).appendText(original).getFormattedText());
        }
        cir.setReturnValue(formatted);
        *///?} else {
        cir.setReturnValue(hyinfo$prefix(text, bedwars, skywars, duels).copy().append(Component.literal(" ")).append(original));
        //?}
    }

    @Unique
    //? if 1.8.9 {
    /*private IChatComponent hyinfo$prefix(String text, IChatComponent bedwars, IChatComponent skywars, IChatComponent duels) {
        IChatComponent prefix = text == null ? null : new ChatComponentText(text);
    *///?} else {
    private Component hyinfo$prefix(String text, Component bedwars, Component skywars, Component duels) {
        Component prefix = text == null ? null : Component.literal(text);
    //?}
        prefix = hyinfo$append(prefix, bedwars);
        prefix = hyinfo$append(prefix, skywars);
        return hyinfo$append(prefix, duels);
    }

    //? if 1.8.9
    //private IChatComponent hyinfo$append(
    //? else
    private Component hyinfo$append(
            //? if 1.8.9 {
            /*IChatComponent prefix,
            IChatComponent component
            *///?} else {
            Component prefix,
            Component component
            //?}
    ) {
        // stats for this player may not be fetched yet
        if (component == null) return prefix;
        if (prefix == null || !HyInfoConfig.INSTANCE.getShowMultipleStatsInTablist()) return component;

        //? if 1.8.9
        //return prefix.createCopy().appendSibling(new ChatComponentText(" ")).appendSibling(component);
        //? else
        return prefix.copy().append(Component.literal(" ")).append(component);
    }
}
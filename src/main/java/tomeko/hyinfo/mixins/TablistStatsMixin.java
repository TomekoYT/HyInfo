package tomeko.hyinfo.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
*///?} else {
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
//?}
import org.spongepowered.asm.mixin.Mixin;
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

        //? if 1.8.9
        //IChatComponent prefix = null;
        //? else
        Component prefix = null;
        if (uuid.version() == 1) {
            if (HyInfoConfig.INSTANCE.getShowNickedIndicatorInTablist() && !HypixelPackets.INSTANCE.getInAssassins()) {
                prefix = hyinfo$append(
                        prefix,
                        //? if 1.8.9
                        //new ChatComponentText(HyInfoConfig.INSTANCE.getNickedIndicatorText())
                        //? else
                        Component.literal(HyInfoConfig.INSTANCE.getNickedIndicatorText())
                );
            }
        } else if (modifyPrefixWithStats) {
            HypixelStatsFetcher.INSTANCE.requestStats(uuid.toString());
            HypixelStatsFetcher.CachedStats stats = HypixelStatsFetcher.INSTANCE.getCachedStats(uuid.toString());

            if (HypixelStatsFetcher.INSTANCE.getRateLimitedIndicators().contains(uuid.toString())) {
                if (HyInfoConfig.INSTANCE.getShowRateLimitedIndicatorInTablist()) {
                    prefix = hyinfo$append(
                            prefix,
                            //? if 1.8.9
                            //new ChatComponentText(HyInfoConfig.INSTANCE.getRateLimitedIndicatorText())
                            //? else
                            Component.literal(HyInfoConfig.INSTANCE.getRateLimitedIndicatorText())
                    );
                }
            } else {
                if (HypixelPackets.INSTANCE.getInBedwars() && HyInfoConfig.INSTANCE.getShowBedwarsStarsInTablist()) {
                    prefix = hyinfo$append(prefix, stats.getBedwars());
                }

                if (HypixelPackets.INSTANCE.getInSkywars() && HyInfoConfig.INSTANCE.getShowSkywarsStarsInTablist()) {
                    prefix = hyinfo$append(prefix, stats.getSkywars());
                }

                if (HypixelPackets.INSTANCE.getInDuels() && HyInfoConfig.INSTANCE.getShowDuelsDivisionInTablist()) {
                    prefix = hyinfo$append(prefix, stats.getDuels());
                }
            }
        }

        if (prefix != null) {
            cir.setReturnValue(
                    //? if 1.8.9
                    //prefix.createCopy().appendSibling(new ChatComponentText(" ")).appendText(original).getFormattedText()
                    //? else
                    prefix.copy().append(Component.literal(" ")).append(original)
            );
        }
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
        if (prefix == null) return component;

        //? if 1.8.9
        //return prefix.createCopy().appendSibling(new ChatComponentText(" ")).appendSibling(component);
        //? else
        return prefix.copy().append(Component.literal(" ")).append(component);
    }
}
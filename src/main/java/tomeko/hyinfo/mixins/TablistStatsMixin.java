package tomeko.hyinfo.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
*///?} else {
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
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
        //? if 1.8.9 {
        //GuiPlayerTabOverlay.class
        //?} else {
        PlayerTabOverlay.class
        //?}
)
public abstract class TablistStatsMixin {
    @Inject(
            method =
                    //? if 1.8.9 {
                    //"getPlayerName",
                    //?} else {
                    "getNameForDisplay",
            //?}
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
        //EntityPlayer localPlayer = Minecraft.getMinecraft().thePlayer;
        //? else
        Player localPlayer = Minecraft.getInstance().player;
        
        if (localPlayer == null) return;

        HypixelStatsFetcher.INSTANCE.requestStats(
                //? if 1.8.9
                //localPlayer.getUniqueID().toString()
                //? else
                localPlayer.getUUID().toString()
        );

        //? if 1.8.9 {
        //String original
        //?} else {
        Component original
                //?}
                = cir.getReturnValue();

        UUID uuid =
                //? if 1.8.9 {
                //info.getGameProfile().getId();
                //?} else {
                info.getProfile().id();
        //?}

        HypixelStatsFetcher.CachedStats stats = HypixelStatsFetcher.INSTANCE.getCachedStats(uuid.toString());

        //? if 1.8.9 {
        //IChatComponent prefix
        //?} else {
        Component prefix
                //?}
                = null;
        if (uuid.version() == 1) {
            if (HyInfoConfig.INSTANCE.getShowNickedIndicatorInTablist() && !HypixelPackets.INSTANCE.getInAssassins()) {
                prefix =
                        //? if 1.8.9 {
                        //new ChatComponentText(HyInfoConfig.INSTANCE.getNickedIndicatorText());
                        //?} else {
                        Component.literal(HyInfoConfig.INSTANCE.getNickedIndicatorText());
                //?}
            }
        } else {
            boolean modifyPrefix = (HypixelPackets.INSTANCE.getInBedwars() && HyInfoConfig.INSTANCE.getShowBedwarsStarsInTablist())
                    || (HypixelPackets.INSTANCE.getInSkywars() && HyInfoConfig.INSTANCE.getShowSkywarsStarsInTablist())
                    || (HypixelPackets.INSTANCE.getInDuels() && HyInfoConfig.INSTANCE.getShowDuelsDivisionInTablist());

            if (modifyPrefix && HypixelStatsFetcher.INSTANCE.getRateLimitedIndicators().contains(uuid.toString())) {
                prefix =
                        //? if 1.8.9 {
                        //new ChatComponentText(HyInfoConfig.INSTANCE.getRateLimitedIndicatorText());
                        //?} else {
                        Component.literal(HyInfoConfig.INSTANCE.getRateLimitedIndicatorText());
                //?}
            } else {
                if (HypixelPackets.INSTANCE.getInBedwars() && HyInfoConfig.INSTANCE.getShowBedwarsStarsInTablist()) {
                    prefix = stats.getBedwars();
                } else if (HypixelPackets.INSTANCE.getInSkywars() && HyInfoConfig.INSTANCE.getShowSkywarsStarsInTablist()) {
                    prefix = stats.getSkywars();
                } else if (HypixelPackets.INSTANCE.getInDuels() && HyInfoConfig.INSTANCE.getShowDuelsDivisionInTablist()) {
                    prefix = stats.getDuels();
                }
            }
        }

        if (prefix != null) cir.setReturnValue(
                //? if 1.8.9 {
                //prefix.createCopy().appendSibling(new ChatComponentText(" ")).appendText(original).getFormattedText()
                //?} else {
                prefix.copy().

                        append(Component.literal(" ")).

                        append(original)
                //?}
        );
    }
}
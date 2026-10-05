package tomeko.hyinfo.stats

//? if 1.8.9 {
/*import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import java.util.function.BiConsumer
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.state.AvatarRenderState
import net.minecraft.client.renderer.state.level.CameraRenderState
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import net.minecraft.world.scores.DisplaySlot
import net.minecraft.world.scores.Team
//?}
import tomeko.hyinfo.config.HyInfoConfig
import tomeko.hyinfo.location.HypixelPackets

object NametagStats {
    fun render(
        //? if 1.8.9 {
        /*player: EntityPlayer,
        label: BiConsumer<String, Double>
        *///?} else {
        player: Player,
        state: AvatarRenderState,
        poseStack: PoseStack,
        collector: SubmitNodeCollector,
        cameraRenderState: CameraRenderState
        //?}
    ) {
        val shouldCheckNetworkLevelWithOtherNametagStats =
            (HypixelPackets.inBedwars && HyInfoConfig.showBedwarsStarsAboveNametag)
                    || (HypixelPackets.inSkywars && HyInfoConfig.showSkywarsStarsAboveNametag)
                    || (HypixelPackets.inDuels && HyInfoConfig.showDuelsDivisionAboveNametag)

        val shouldRenderAnyStatsNametag =
            HyInfoConfig.showNetworkLevelAboveNametag || shouldCheckNetworkLevelWithOtherNametagStats
        if (!shouldRenderAnyStatsNametag) return

        val uuid =
        //? if 1.8.9
        //player.uniqueID
            //? else
            player.uuid

        if (uuid.version() != 4 && uuid.version() != 1) return

        val lines = mutableListOf<
                //? if 1.8.9
                //IChatComponent
                //? else
                Component
                >()

        if (uuid.version() == 1) {
            return
        } else if (shouldRenderAnyStatsNametag) {
            HypixelStatsFetcher.requestStats(uuid.toString())
            val cached = HypixelStatsFetcher.getCachedStats(uuid.toString())

            if (HypixelStatsFetcher.rateLimitedIndicators.contains(uuid.toString())) {
                lines.add(
                    //? if 1.8.9 {
                    //ChatComponentText(HyInfoConfig.rateLimitedIndicatorText)
                    //?} else {
                    Component.literal(HyInfoConfig.rateLimitedIndicatorText)
                    //?}
                )
            } else {
                if (HypixelPackets.inDuels && HyInfoConfig.showDuelsDivisionAboveNametag) {
                    cached.duels?.let { division ->
                        lines.add(
                            //? if 1.8.9 {
                            /*ChatComponentText(HypixelPackets.duelsMode.modeName + HyInfoConfig.duelsTextAboveNametag).appendSibling(
                                division
                            )
                            *///?} else {
                            Component.literal(HypixelPackets.duelsMode.modeName + HyInfoConfig.duelsTextAboveNametag)
                                .append(division)
                            //?}
                        )
                    }
                }

                if (HypixelPackets.inBedwars && HyInfoConfig.showBedwarsStarsAboveNametag) {
                    cached.bedwars?.let { bedwars ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyInfoConfig.bedwarsTextAboveNametag).appendSibling(bedwars)
                            //?} else {
                            Component.literal(HyInfoConfig.bedwarsTextAboveNametag).append(bedwars)
                            //?}
                        )
                    }
                }

                if (HypixelPackets.inSkywars && HyInfoConfig.showSkywarsStarsAboveNametag) {
                    cached.skywars?.let { skywars ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyInfoConfig.skywarsTextAboveNametag).appendSibling(skywars)
                            //?} else {
                            Component.literal(HyInfoConfig.skywarsTextAboveNametag).append(skywars)
                            //?}
                        )
                    }
                }

                if (HypixelPackets.onHypixel
                    && HyInfoConfig.showNetworkLevelAboveNametag
                    && (!shouldCheckNetworkLevelWithOtherNametagStats || HyInfoConfig.showNetworkLevelWithOtherNametagStats)
                ) {
                    cached.level?.let { networkLevel ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyInfoConfig.networkLevelTextAboveNametag + networkLevel)
                            //?} else {
                            Component.literal(HyInfoConfig.networkLevelTextAboveNametag + networkLevel)
                            //?}
                        )
                    }
                }
            }
        }

        if (lines.isEmpty()) return

        //? if 1.8.9 {
        /*for ((index, line) in lines.withIndex()) {
            label.accept(
                line.formattedText,
                ((index + 1) * 10 + HyInfoConfig.nametagsHeightOffset.toInt()) * 0.02666667
            )
        }
        *///?} else {
        var offset = -10 - HyInfoConfig.nametagsHeightOffset.toInt()
        if (Minecraft.getInstance().level?.scoreboard?.getDisplayObjective(DisplaySlot.BELOW_NAME) != null) offset *= 2

        for (line in lines) {
            //? if >= 26.2 {
            collector.submitNameTag(
                poseStack,
                state.nameTagAttachment,
                offset,
                line,
                !state.isDiscrete,
                state.lightCoords,
                cameraRenderState
            )
            //?} else {
            /*collector.submitNameTag(
                poseStack,
                state.nameTagAttachment,
                offset,
                line,
                !state.isDiscrete,
                state.lightCoords,
                state.distanceToCameraSq,
                cameraRenderState
            )
            *///?}

            offset -= 10
        }
        //?}
    }
}
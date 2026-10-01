package tomeko.hyinfo.stats

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import org.lwjgl.opengl.GL11
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
        x: Double,
        y: Double,
        z: Double,
        maxDistance: Int
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
        val shouldRenderNickedNametag = HyInfoConfig.showNickedIndicatorAboveNametag && !HypixelPackets.inAssassins
        if (!shouldRenderAnyStatsNametag && !shouldRenderNickedNametag) return

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
            if (shouldRenderNickedNametag) {
                lines.add(
                    //? if 1.8.9 {
                    //ChatComponentText(
                    //?} else {
                    Component.literal(
                        //?}
                        HyInfoConfig.nickedIndicatorText
                    )
                )
            }
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
        /*val mc = Minecraft.getMinecraft()
        val renderManager = mc.renderManager
        val fontRenderer = mc.fontRendererObj

        val distanceSq = player.getDistanceSqToEntity(renderManager.livingPlayer)
        val maxDistanceSq = maxDistance.toDouble() * maxDistance.toDouble()

        if (distanceSq > maxDistanceSq) return

        val f = 1.6F
        val g = 0.016666668F * f

        for ((index, line) in lines.withIndex()) {
            val text = line.formattedText
            val textWidth = fontRenderer.getStringWidth(text)
            val halfWidth = textWidth / 2

            val lineY = -(index + 1) * 10 - HyInfoConfig.nametagsHeightOffset.toInt()

            GlStateManager.pushMatrix()

            GlStateManager.translate(
                x.toFloat() + 0.0F,
                y.toFloat() + player.height + 0.5F,
                z.toFloat()
            )

            GL11.glNormal3f(
                0.0F,
                1.0F,
                0.0F
            )

            GlStateManager.rotate(
                -renderManager.playerViewY,
                0.0F,
                1.0F,
                0.0F
            )

            GlStateManager.rotate(
                renderManager.playerViewX,
                1.0F,
                0.0F,
                0.0F
            )

            GlStateManager.scale(
                -g,
                -g,
                g
            )

            GlStateManager.disableLighting()
            GlStateManager.depthMask(false)
            GlStateManager.disableDepth()
            GlStateManager.enableBlend()

            GlStateManager.tryBlendFuncSeparate(
                770,
                771,
                1,
                0
            )

            val tessellator = Tessellator.getInstance()
            val worldRenderer = tessellator.worldRenderer

            GlStateManager.disableTexture2D()

            worldRenderer.begin(
                7,
                DefaultVertexFormats.POSITION_COLOR
            )

            worldRenderer
                .pos(
                    (-halfWidth - 1).toDouble(),
                    (lineY - 1).toDouble(),
                    0.0
                )
                .color(
                    0.0F,
                    0.0F,
                    0.0F,
                    0.25F
                )
                .endVertex()

            worldRenderer
                .pos(
                    (-halfWidth - 1).toDouble(),
                    (lineY + 8).toDouble(),
                    0.0
                )
                .color(
                    0.0F,
                    0.0F,
                    0.0F,
                    0.25F
                )
                .endVertex()

            worldRenderer
                .pos(
                    (halfWidth + 1).toDouble(),
                    (lineY + 8).toDouble(),
                    0.0
                )
                .color(
                    0.0F,
                    0.0F,
                    0.0F,
                    0.25F
                )
                .endVertex()

            worldRenderer
                .pos(
                    (halfWidth + 1).toDouble(),
                    (lineY - 1).toDouble(),
                    0.0
                )
                .color(
                    0.0F,
                    0.0F,
                    0.0F,
                    0.25F
                )
                .endVertex()

            tessellator.draw()

            GlStateManager.enableTexture2D()

            fontRenderer.drawString(
                text,
                -halfWidth,
                lineY,
                553648127
            )

            GlStateManager.enableDepth()
            GlStateManager.depthMask(true)

            fontRenderer.drawString(
                text,
                -halfWidth,
                lineY,
                -1
            )

            GlStateManager.enableLighting()
            GlStateManager.disableBlend()

            GlStateManager.color(
                1.0F,
                1.0F,
                1.0F,
                1.0F
            )

            GlStateManager.popMatrix()
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
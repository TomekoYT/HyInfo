package tomeko.hymod.stats

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.scoreboard.Team
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent as Component
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.common.MinecraftForge
import org.lwjgl.opengl.GL11
*///?} else {
import com.mojang.math.Axis
//? if >= 26.1 {
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext as LevelRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents as LevelRenderEvents
*///?}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.scores.DisplaySlot
import net.minecraft.world.scores.Team
//?}
import tomeko.hymod.config.HyModConfig
import tomeko.hymod.location.HypixelPackets
import kotlin.math.sqrt

object NametagStats {
    fun register() {
        //? if 1.8.9 {
        //MinecraftForge.EVENT_BUS.register(this)
        //?} elif >= 26.1 {
        LevelRenderEvents.COLLECT_SUBMITS.register(::render)
        //?} else {
        //LevelRenderEvents.AFTER_ENTITIES.register(::render)
        //?}
    }

    private const val MAX_DISTANCE = 32.0
    private const val BASE_SCALE = 0.025f
    private const val MIN_DISTANCE = 1.0
    private const val NAMETAG_OFFSET = -15f

    //? if 1.8.9 {
    //@SubscribeEvent
    //?}
    fun render(
        //? if 1.8.9 {
        //event: RenderWorldLastEvent,
        //?} else {
        context: LevelRenderContext
        //?}
    ) {
        if (!HypixelPackets.onHypixel
            || (!HyModConfig.showNetworkLevelAboveNametag
                    && !HyModConfig.showBedwarsStarsAboveNametag && !HyModConfig.showBedwarsStarsInTablist
                    && !HyModConfig.showSkywarsStarsAboveNametag && !HyModConfig.showSkywarsStarsInTablist
                    && !HyModConfig.showDuelsDivisionAboveNametag && !HyModConfig.showDuelsDivisionInTablist
                    && !HyModConfig.showNickedIndicatorAboveNametag && !HyModConfig.showNickedIndicatorInTablist
                    )
        ) return

        val mc =
        //? if 1.8.9 {
                //Minecraft.getMinecraft()
            //?} else {
            Minecraft.getInstance()
        //?}
        val level =
        //? if 1.8.9 {
                //mc.theWorld
                //?} else {
            mc.level
            //?}
                ?: return

        val players =
        //? if 1.8.9 {
                //level.playerEntities
            //?} else {
            level.players()
        //?}

        for (player in players) {
            if (//? if 1.8.9 {
            //!player.isEntityAlive
            //?} else {
                !player.isAlive
                //?}
                || player.isInvisible
            ) continue

            val uuid =
            //? if 1.8.9 {
                    //player.uniqueID
                //?} else {
                player.uuid
            //?}

            if (uuid.version() != 4 && uuid.version() != 1) continue

            if (uuid.version() == 4) HypixelStatsFetcher.requestStats(uuid.toString())

            val localPlayer =
            //? if 1.8.9 {
                    //mc.thePlayer
                //?} else {
                mc.player
            //?}


            if (
                isNametagHidden(player, localPlayer) ||
                //? if 1.8.9 {
                //mc.gameSettings.hideGUI
                //?} else if >= 26.2 {
                //mc.gui.hud.isHidden
                //?} else {
                mc.options.hideGui
            //?}
            ) continue


            val isFirstPerson =
            //? if 1.8.9 {
                    //mc.gameSettings.thirdPersonView == 0
                //?} else {
                mc.options.cameraType.isFirstPerson
            //?}
            if (player == localPlayer && isFirstPerson) continue


            val playerX =
            //? if 1.8.9 {
                    //player.posX
                //?} else {
                player.x
            //?}

            val playerY =
            //? if 1.8.9 {
                    //player.posY
                //?} else {
                player.y
            //?}

            val playerZ =
            //? if 1.8.9 {
                    //player.posZ
                //?} else {
                player.z
            //?}

            val playerLastX =
            //? if 1.8.9 {
                    //player.lastTickPosX
                //?} else {
                player.xo
            //?}

            val playerLastY =
            //? if 1.8.9 {
                    //player.lastTickPosY
                //?} else {
                player.yo
            //?}

            val playerLastZ =
            //? if 1.8.9 {
                    //player.lastTickPosZ
                //?} else {
                player.zo
            //?}

            val camera =
            //? if 1.8.9 {
                    //mc.renderManager
                //?} elif >= 26.1 {
                context.levelState().cameraRenderState
            //?} else {
            //context.worldState().cameraRenderState
            //?}

            val cameraX =
            //? if 1.8.9 {
                    //camera.viewerPosX
                //?} else {
                camera.pos.x
            //?}

            val cameraY =
            //? if 1.8.9 {
                    //camera.viewerPosY
                //?} else {
                camera.pos.y
            //?}

            val cameraZ =
            //? if 1.8.9 {
                    //camera.viewerPosZ
                //?} else {
                camera.pos.z
            //?}

            val tickDelta =
            //? if 1.8.9 {
                    //event.partialTicks
                //?} else {
                mc.deltaTracker.getGameTimeDeltaPartialTick(false)
            //?}

            val x = playerLastX + (playerX - playerLastX) * tickDelta - cameraX
            val relativeY = playerLastY + (playerY - playerLastY) * tickDelta - cameraY
            val z = playerLastZ + (playerZ - playerLastZ) * tickDelta - cameraZ

            val distanceSquared = x * x + relativeY * relativeY + z * z
            if (distanceSquared > MAX_DISTANCE * MAX_DISTANCE) continue

            val distance = sqrt(distanceSquared)

            val playerHeight =
            //? if 1.8.9 {
                    //player.height
                //?} else {
                player.bbHeight
            //?}

            val hasBelowNameObjective =
            //? if 1.8.9 {
                    //player.worldScoreboard.getObjectiveInDisplaySlot(2) != null
                //?} else {
                level.scoreboard.getDisplayObjective(DisplaySlot.BELOW_NAME) != null
            //?}

            var objectiveCorrection = 0.3
            if (hasBelowNameObjective && distanceSquared < 100.0) objectiveCorrection *= 2

            val y = relativeY + HyModConfig.nametagOffset + playerHeight + objectiveCorrection

            val scale =
                (BASE_SCALE * (0.75 + 0.25 * (1.0 - 1.0.coerceAtMost(0.0.coerceAtLeast((distance - MIN_DISTANCE) / (MAX_DISTANCE - MIN_DISTANCE)))))).toFloat()

            val cached = HypixelStatsFetcher.getCachedStats(uuid.toString())
            val lines = mutableListOf<Component>()

            if (uuid.version() == 1) {
                if (HyModConfig.showNickedIndicatorAboveNametag) {
                    lines.add(
                        //? if 1.8.9 {
                        //ChatComponentText(
                            //?} else {
                        Component.literal(
                            //?}
                            HyModConfig.nickedIndicatorText
                        )
                    )
                }
            } else {
                if (HypixelPackets.inDuels && HyModConfig.showDuelsDivisionAboveNametag) {
                    cached.duels?.let { division ->
                        lines.add(
                            //? if 1.8.9 {
                            /*ChatComponentText(HypixelPackets.duelsMode.modeName + HyModConfig.duelsTextAboveNametag).appendSibling(
                                division
                            )
                            *///?} else {
                            Component.literal(HypixelPackets.duelsMode.modeName + HyModConfig.duelsTextAboveNametag)
                                .append(division)
                            //?}
                        )
                    }
                }

                if (HypixelPackets.inBedwars && HyModConfig.showBedwarsStarsAboveNametag) {
                    cached.bedwars?.let { bedwars ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyModConfig.bedwarsTextAboveNametag).appendSibling(bedwars)
                            //?} else {
                            Component.literal(HyModConfig.bedwarsTextAboveNametag).append(bedwars)
                            //?}
                        )
                    }
                }

                if (HypixelPackets.inSkywars && HyModConfig.showSkywarsStarsAboveNametag) {
                    cached.skywars?.let { skywars ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyModConfig.skywarsTextAboveNametag).appendSibling(skywars)
                            //?} else {
                            Component.literal(HyModConfig.skywarsTextAboveNametag).append(skywars)
                            //?}
                        )
                    }
                }

                val shouldCheckNetworkLevelWithOtherNametagStats =
                    (HypixelPackets.inBedwars && HyModConfig.showBedwarsStarsAboveNametag)
                            || (HypixelPackets.inSkywars && HyModConfig.showSkywarsStarsAboveNametag)
                            || (HypixelPackets.inDuels && HyModConfig.showDuelsDivisionAboveNametag)

                if (HypixelPackets.onHypixel
                    && HyModConfig.showNetworkLevelAboveNametag
                    && (!shouldCheckNetworkLevelWithOtherNametagStats || HyModConfig.showNetworkLevelWithOtherNametagStats)
                ) {
                    cached.level?.let { networkLevel ->
                        lines.add(
                            //? if 1.8.9 {
                            //ChatComponentText(HyModConfig.networkLevelTextAboveNametag + networkLevel)
                            //?} else {
                            Component.literal(HyModConfig.networkLevelTextAboveNametag + networkLevel)
                            //?}
                        )
                    }
                }
            }

            //? if 1.8.9 {
            /*GlStateManager.pushMatrix()
            GlStateManager.translate(x, y, z)

            GlStateManager.rotate(-camera.playerViewY, 0.0f, 1.0f, 0.0f)
            GlStateManager.rotate(camera.playerViewX, 1.0f, 0.0f, 0.0f)
            GlStateManager.scale(-scale, -scale, scale)

            GlStateManager.disableDepth()
            GlStateManager.depthMask(false)
            GlStateManager.enableBlend()
            GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA,
                GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE,
                GL11.GL_ZERO
            )
            *///?} else {
            val matrices =
                //? if >= 26.1 {
                context.poseStack()
            //?} else {
            //context.matrices()
        //?}

            matrices.pushPose()

            matrices.translate(x, y, z)
            matrices.mulPose(camera.orientation)
            matrices.mulPose(Axis.YP.rotationDegrees(180.0f))
            matrices.scale(-scale, -scale, scale)
            val submitNodeCollector =
                //? if >= 26.1 {
                context.submitNodeCollector().order(1)
            //?} else {
            //context.commandQueue()
        //?}
            //?}

            val isCrouching =
            //? if 1.8.9 {
                    //player.isSneaking
                    //?} else {
                player.isCrouching
            //?}

            val normalColor = -0x1
            val dimColor = 0x80FFFFFF.toInt()
            val backgroundColor = 0x50000000

            var offset = 0
            for (text in lines) {
                val width =
                //? if 1.8.9 {
                        //mc.fontRendererObj.getStringWidth(text.formattedText)
                    //?} else {
                    mc.font.width(text)
                //?}

                //? if 1.8.9 {
                /*val paddingX = 2
                val paddingY = 1
                val textHeight = 9

                GlStateManager.depthMask(false)
                GlStateManager.disableDepth()

                Gui.drawRect(
                    -width / 2 - paddingX,
                    (NAMETAG_OFFSET + offset - paddingY).toInt(),
                    width / 2 + paddingX,
                    (NAMETAG_OFFSET + offset + textHeight + paddingY).toInt(),
                    0x50000000
                )

                mc.fontRendererObj.drawString(
                    text.formattedText,
                    -width / 2.0f,
                    NAMETAG_OFFSET + offset,
                    0x66FFFFFF,
                    false
                )

                GlStateManager.enableDepth()
                GlStateManager.depthMask(true)

                mc.fontRendererObj.drawString(
                    text.formattedText,
                    -width / 2.0f,
                    NAMETAG_OFFSET + offset,
                    0xFFFFFFFF.toInt(),
                    true
                )
                *///?} else {
                if (isCrouching) {
                    submitNodeCollector.submitText(
                        matrices,
                        -width / 2.0f,
                        NAMETAG_OFFSET + offset,
                        text.visualOrderText,
                        true,
                        Font.DisplayMode.SEE_THROUGH,
                        0xF000F0,
                        dimColor,
                        backgroundColor,
                        0
                    )
                } else {
                    submitNodeCollector.submitText(
                        matrices,
                        -width / 2.0f,
                        NAMETAG_OFFSET + offset,
                        text.visualOrderText,
                        true,
                        Font.DisplayMode.SEE_THROUGH,
                        0xF000F0,
                        dimColor,
                        backgroundColor,
                        0
                    )

                    submitNodeCollector.submitText(
                        matrices,
                        -width / 2.0f,
                        NAMETAG_OFFSET + offset,
                        text.visualOrderText,
                        false,
                        Font.DisplayMode.NORMAL,
                        0xF000F0,
                        normalColor,
                        0,
                        0
                    )
                }
                //?}

                offset -= 11
            }

            //? if 1.8.9 {
            /*GlStateManager.depthMask(true)
            GlStateManager.disableBlend()

            GlStateManager.popMatrix()
            *///?} else {
            matrices.popPose()
            //?}
        }
    }

    private fun isNametagHidden(
        //? if 1.8.9 {
        /*player: EntityPlayer,
        localPlayer: EntityPlayerSP?,
        *///?} else {
        player: Player,
        localPlayer: LocalPlayer?,
        //?}
    ): Boolean {
        val team = player.team ?: return false
        val teamNametagVisibility = team.nameTagVisibility

        if (teamNametagVisibility ==
            //? if 1.8.9 {
            //Team.EnumVisible.ALWAYS
        //?} else {
            Team.Visibility.ALWAYS
        //?}
        ) return false

        if (teamNametagVisibility ==
            //? if 1.8.9 {
            //Team.EnumVisible.NEVER
        //?} else {
            Team.Visibility.NEVER
        //?}
        ) return true

        if (localPlayer == null) return false

        if (teamNametagVisibility ==
            //? if 1.8.9 {
            //Team.EnumVisible.HIDE_FOR_OWN_TEAM
            //?} else {
            Team.Visibility.HIDE_FOR_OWN_TEAM
            //?}
            && team == localPlayer.team
        ) return true

        if (teamNametagVisibility ==
            //? if 1.8.9 {
            //Team.EnumVisible.HIDE_FOR_OTHER_TEAMS
            //?} else {
            Team.Visibility.HIDE_FOR_OTHER_TEAMS
            //?}
            && team != localPlayer.team
        ) return true

        return false
    }
}
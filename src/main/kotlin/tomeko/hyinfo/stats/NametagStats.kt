package tomeko.hyinfo.stats

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.state.AvatarRenderState
import net.minecraft.client.renderer.state.level.CameraRenderState
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.decoration.ArmorStand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraft.world.scores.DisplaySlot
import net.minecraft.world.scores.Team
//?}
import tomeko.hyinfo.config.HyInfoConfig
import tomeko.hyinfo.location.HypixelPackets
import java.lang.ref.WeakReference
import java.util.function.BiConsumer

object NametagStats {
    private val armorStandCache = HashMap<Int, Boolean>()
    private var armorStandCachePlayer = WeakReference<Any>(null)
    private var armorStandCacheTick = -1

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
        val shouldRenderAnyStatsNametag =
            HyInfoConfig.showNetworkLevelAboveNametag || (HypixelPackets.inBedwars && HyInfoConfig.showBedwarsStarsAboveNametag)
                    || (HypixelPackets.inSkywars && HyInfoConfig.showSkywarsStarsAboveNametag)
                    || (HypixelPackets.inDuels && HyInfoConfig.showDuelsDivisionAboveNametag)
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
            val id = uuid.toString()
            HypixelStatsFetcher.requestStats(id)
            val cached = HypixelStatsFetcher.getCachedStats(id)

            if (HypixelStatsFetcher.rateLimitedIndicators.contains(id)) {
                if (HyInfoConfig.showRateLimitedIndicatorAboveNametag) {
                    lines.add(
                        //? if 1.8.9 {
                        //ChatComponentText(HyInfoConfig.rateLimitedIndicatorText)
                        //?} else {
                        Component.literal(HyInfoConfig.rateLimitedIndicatorText)
                        //?}
                    )
                }
            } else {
                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inDuels && HyInfoConfig.showDuelsDivisionAboveNametag) {
                    cached.duels?.let { division ->
                        lines.add(
                            //? if 1.8.9 {
                            /*ChatComponentText(HypixelPackets.duelsMode.modeName + HyInfoConfig.duelsTextAboveNametag)
                                .appendSibling(division)
                            *///?} else {
                            Component.literal(HypixelPackets.duelsMode.modeName + HyInfoConfig.duelsTextAboveNametag)
                                .append(division)
                            //?}
                        )
                    }
                }

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inBedwars && HyInfoConfig.showBedwarsStarsAboveNametag) {
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

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inSkywars && HyInfoConfig.showSkywarsStarsAboveNametag) {
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

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.onHypixel && HyInfoConfig.showNetworkLevelAboveNametag) {
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
        /*var offset = HyInfoConfig.nametagsHeightOffset.toDouble() / 100 + if (hasArmorStand(player)) 0.526 else 0.226
        for (line in lines) {
            label.accept(
                line.formattedText,
                offset
            )

            offset += HyInfoConfig.nametagsHeightOffset.toDouble() / 100 + 0.226
        }
        *///?} else {
        var offset = -HyInfoConfig.nametagsHeightOffset.toInt() + if (hasArmorStand(player)) -25 else -10
        if (Minecraft.getInstance().level?.scoreboard?.getDisplayObjective(DisplaySlot.BELOW_NAME) != null) offset -= 10

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

            offset += -HyInfoConfig.nametagsHeightOffset.toInt() - 10
        }
        //?}
    }

    private fun hasArmorStand(
        //? if 1.8.9
        //target: EntityPlayer
        //? else
        target: Player
    ): Boolean {
        val self =
        //? if 1.8.9
        //Minecraft.getMinecraft().thePlayer
            //? else
            Minecraft.getInstance().player
        val tick =
        //? if 1.8.9
        //self?.ticksExisted ?: -1
            //? else
            self?.tickCount ?: -1
        if (self !== armorStandCachePlayer.get() || tick != armorStandCacheTick) {
            armorStandCache.clear()
            armorStandCachePlayer = WeakReference(self)
            armorStandCacheTick = tick
        }
        return armorStandCache.getOrPut(
            //? if 1.8.9
            //target.entityId
            //? else
            target.id
        ) { scanForArmorStand(target) }
    }

    private fun scanForArmorStand(
        //? if 1.8.9
        //target: EntityPlayer
        //? else
        target: Player
    ): Boolean {
        //? if 1.8.9 {
        /*val world = target.worldObj ?: return false
        val box = AxisAlignedBB(
            target.posX - 0.8, target.posY + 1.5, target.posZ - 0.8,
            target.posX + 0.8, target.posY + 4.5, target.posZ + 0.8
        ).expand(0.01, 0.01, 0.01)
        return world.getEntitiesWithinAABB(EntityArmorStand::class.java, box) {
            val dx = it!!.posX - target.posX
            val dz = it.posZ - target.posZ
            it.hasCustomName() && dx * dx + dz * dz <= 0.8 * 0.8 && it.posY - target.posY in 1.5..4.5
        }.isNotEmpty()
        *///?} else {
        val level = Minecraft.getInstance().level ?: return false
        val box = AABB(
            target.x - 0.8, target.y + 1.5, target.z - 0.8,
            target.x + 0.8, target.y + 4.5, target.z + 0.8
        ).inflate(0.01)
        return level.getEntitiesOfClass(ArmorStand::class.java, box) {
            val dx = it.x - target.x
            val dz = it.z - target.z
            it.hasCustomName() && dx * dx + dz * dz <= 0.8 * 0.8 && it.y - target.y in 1.5..4.5
        }.isNotEmpty()
        //?}
    }
}
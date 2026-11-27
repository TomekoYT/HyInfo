package tomeko.hyinfo.stats

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent as Component
import tomeko.hyinfo.utils.FormattedTextCache
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
import java.util.WeakHashMap
import java.util.function.BiConsumer

object NametagStats {
    private const val MAX_LINES = 4

    private val armorStandCache = HashMap<Int, Boolean>()
    private var armorStandCachePlayer = WeakReference<Any>(null)
    private var armorStandCacheTick = -1
    private val nametagLines = WeakHashMap<Any, NametagLines>()

    private class NametagLines {
        val items = ArrayList<
                //? if 1.8.9
                //String
                //? else
                Component
                >(MAX_LINES)

        //? if 1.8.9
        //private val caches = Array(MAX_LINES) { FormattedTextCache(3) }

        fun isEmpty() = items.isEmpty()

        fun clear() = items.clear()

        fun add(text: String, suffix: String? = null, component: Component? = null) {
            //? if 1.8.9 {
            /*val cache = caches.getOrNull(items.size)?.key(0, text)?.key(1, suffix)?.key(2, component)
            items.add(cache?.get() ?: build(text, suffix, component).formattedText.also { cache?.put(it) })
            *///?} else {
            items.add(build(text, suffix, component))
            //?}
        }

        private fun build(text: String, suffix: String?, component: Component?): Component {
            val literal =
            //? if 1.8.9
            //ChatComponentText(if (suffix == null) text else text + suffix)
                //? else
                Component.literal(if (suffix == null) text else text + suffix)
            if (component == null) return literal
            //? if 1.8.9
            //return literal.appendSibling(component)
            //? else
            return literal.append(component)
        }
    }

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

        val lines = nametagLines.getOrPut(player) { NametagLines() }
        lines.clear()

        if (uuid.version() == 1) {
            if (shouldRenderNickedNametag) {
                lines.add(HyInfoConfig.nickedIndicatorText)
            }
        } else if (shouldRenderAnyStatsNametag) {
            val id = uuid.toString()
            HypixelStatsFetcher.requestStats(id)
            val cached = HypixelStatsFetcher.getCachedStats(id)

            if (HypixelStatsFetcher.rateLimitedIndicators.contains(id)) {
                if (HyInfoConfig.showRateLimitedIndicatorAboveNametag) {
                    lines.add(HyInfoConfig.rateLimitedIndicatorText)
                }
            } else {
                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inDuels && HyInfoConfig.showDuelsDivisionAboveNametag) {
                    cached.duels?.let { division ->
                        lines.add(HypixelPackets.duelsMode.modeName, HyInfoConfig.duelsTextAboveNametag, division)
                    }
                }

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inBedwars && HyInfoConfig.showBedwarsStarsAboveNametag) {
                    cached.bedwars?.let { bedwars ->
                        lines.add(HyInfoConfig.bedwarsTextAboveNametag, component = bedwars)
                    }
                }

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.inSkywars && HyInfoConfig.showSkywarsStarsAboveNametag) {
                    cached.skywars?.let { skywars ->
                        lines.add(HyInfoConfig.skywarsTextAboveNametag, component = skywars)
                    }
                }

                if ((lines.isEmpty() || HyInfoConfig.showMultipleStatsAboveNametag) && HypixelPackets.onHypixel && HyInfoConfig.showNetworkLevelAboveNametag) {
                    cached.level?.let { networkLevel ->
                        lines.add(HyInfoConfig.networkLevelTextAboveNametag, networkLevel)
                    }
                }
            }
        }

        if (lines.isEmpty()) return

        //? if 1.8.9 {
        /*var offset = HyInfoConfig.nametagsHeightOffset.toDouble() / 100 + if (hasArmorStand(player) && HypixelPackets.inLobby) 0.526 else 0.226
        for (line in lines.items) {
            label.accept(
                line,
                offset
            )

            offset += HyInfoConfig.nametagsHeightOffset.toDouble() / 100 + 0.226
        }
        *///?} else {
        var offset = -HyInfoConfig.nametagsHeightOffset.toInt() + if (hasArmorStand(player) && HypixelPackets.inLobby) -25 else -10
        if (Minecraft.getInstance().level?.scoreboard?.getDisplayObjective(DisplaySlot.BELOW_NAME) != null && (Minecraft.getInstance().player?.distanceTo(player) ?: 0f) <= 10f) offset -= 10

        val attachment = state.nameTagAttachment ?: return
        val pixelScale = 0.025 * polyNametagScale()

        for (line in lines.items) {
            val raised = attachment.add(0.0, -offset * pixelScale, 0.0)
            //? if >= 26.2 {
            collector.submitNameTag(
                poseStack,
                raised,
                0,
                line,
                !state.isDiscrete,
                state.lightCoords,
                cameraRenderState
            )
            //?} else {
            /*collector.submitNameTag(
                poseStack,
                raised,
                0,
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

    //? if fabric {
    private val polyNametagScaleGetter: (() -> Float)? by lazy {
        try {
            val config = Class.forName("org.polyfrost.polynametag.client.PolyNametagConfig")
            val enabled = config.getMethod("isEnabled")
            val scale = config.getMethod("getScale")
            ({
                if (enabled.invoke(null) as Boolean) scale.invoke(null) as Float else 1.0f
            })
        } catch (_: Throwable) {
            null
        }
    }

    private fun polyNametagScale(): Float = try {
        polyNametagScaleGetter?.invoke() ?: 1.0f
    } catch (_: Throwable) {
        1.0f
    }
    //?}

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
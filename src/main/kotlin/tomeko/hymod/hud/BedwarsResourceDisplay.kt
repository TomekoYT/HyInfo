package tomeko.hymod.hud

import net.minecraft.client.Minecraft
//? if 1.8.9 {
/*//? if ornithe {
/*import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
*///?}
//? if forge {
/*import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UGraphics
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.renderer.TextRenderer
*///?}
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
//?}
//? if !forge {
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.hud.v1.HudManager
import org.polyfrost.oneconfig.api.hud.v1.LegacyHud
//?}
import tomeko.hymod.config.HyModConfig
import tomeko.hymod.utils.Constants
import tomeko.hymod.location.HypixelPackets
import tomeko.hymod.utils.ItemTracker

class BedwarsResourceDisplay
//? if forge {
//: BasicHud(true)
//?} else {
    : LegacyHud("${Constants.MOD_ID}_bedwars_resource_display.json", "BedWars Resource Display", Category.COMBAT)
//?}
{
    //? if forge {
    //@Exclude
    //?}
    companion object {
        //? if !forge {
        fun register() {
            HudManager.register(BedwarsResourceDisplay(), Constants.MOD_ID, Constants.MOD_ICON)
        }
        //?}

        //? if !forge {
        private const val CATEGORY_GENERAL = "General"
        private const val SUBCATEGORY_GENERAL = "General"
        private const val SUBCATEGORY_RESOURCES = "Resources"
        private const val CATEGORY_BACKGROUND = "Background"
        //?}
    }

    //? if forge {
    /*@Dropdown(
        name = "Text Type",
        options = ["No Shadow", "Shadow", "Full Shadow"]
    )
    var textType = 0

    @Color(name = "Text Color")
    var textColor: OneColor = OneColor(255, 255, 255)
    *///?} else {
    @Color(
        title = "Text Color",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var txtColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        title = "Text Shadow",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadow = true

    @Color(
        title = "Text Shadow Color",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadowColor = PolyColor(0xFF000000.toInt())
    //?}

    @Slider(
        //? if forge {
        //name =
            //?} else {
        title =
            //?}
        "Item Padding",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var itemPadding = 5f

    @Slider(
        //? if forge {
        //name =
            //?} else {
        title =
            //?}
        "Icon Padding",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var iconPadding = 5f

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Iron",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showIron = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Gold"
        //? if !forge {
        , category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showGold = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Diamond",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showDiamond = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Emerald",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showEmerald = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Inventory",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showInventory = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Ender Chest",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showEnderChest = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Total",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showTotal = true

    //? if !forge {
    @Switch(
        title = "Show Background",
        category = CATEGORY_BACKGROUND
    )
    var background = false

    @Color(
        title = "Background Color",
        category = CATEGORY_BACKGROUND
    )
    var backgroundColor = PolyColor(0x80000000.toInt())

    @Slider(
        title = "Background Radius",
        min = 0f,
        max = 10f,
        step = 0.1f,
        category = CATEGORY_BACKGROUND
    )
    var backgroundRadius = 5f
    //?}

    //? if forge {
    //@Exclude
    //?}
    private var actualWidth = 1f

    //? if forge {
    //@Exclude
    //?}
    private var actualHeight = 1f

    //? if forge {
    /*override fun draw(
        matrices: UMatrixStack,
        x: Float,
        y: Float,
        scale: Float,
        example: Boolean
    )
    *///?} else {
    override fun render(
        //? if fabric {
        mcCtx: GuiGraphicsExtractor
        //?}
    )
    //?}
    {
        if (
        //? if forge {
        //!example
        //?} else {
            !HudManager.isEditing
            //?}
            && !HyModConfig.debugModeEnabled
            && !HypixelPackets.inBedwars
        ) return

        val mc =
        //? if 1.8.9 {
                //Minecraft.getMinecraft()
            //?} else {
            Minecraft.getInstance()
        //?}

        val iconSize = 16f
        val offset = iconSize + itemPadding

        var longestWidth = 0

        val IRON: Item =
        //? if 1.8.9 {
        //Items.iron_ingot
            //?} else {
            Items.IRON_INGOT
        //?}

        val GOLD: Item =
        //? if 1.8.9 {
        //Items.gold_ingot
            //?} else {
            Items.GOLD_INGOT
        //?}

        val DIAMOND: Item =
        //? if 1.8.9 {
        //Items.diamond
            //?} else {
            Items.DIAMOND
        //?}

        val EMERALD: Item =
        //? if 1.8.9 {
        //Items.emerald
            //?} else {
            Items.EMERALD
        //?}

        val items = mutableListOf(
            IRON,
            GOLD,
            DIAMOND,
            EMERALD
        )

        fun showItem(item: Item): Boolean = when (item) {
            IRON -> showIron
            GOLD -> showGold
            DIAMOND -> showDiamond
            EMERALD -> showEmerald
            else -> false
        }

        fun getText(item: Item): String {
            val inventoryAmount = ItemTracker.inventory[item] ?: 0
            val enderChestAmount = ItemTracker.enderChest[item] ?: 0

            var text = ""
            if (showInventory) text += inventoryAmount.toString()

            if (showEnderChest) {
                if (showInventory) text += " + "
                text += enderChestAmount.toString()
            }

            if (showTotal) {
                if (showInventory || showEnderChest) text += " "
                text += "(${inventoryAmount + enderChestAmount})"
            }

            return text
        }

        for (item in items) {
            if (!showItem(item)) continue

            longestWidth = maxOf(
                longestWidth,
                //? if 1.8.9 {
                //mc.fontRendererObj.getStringWidth(
                    //?} else {
                mc.font.width(
                    //?}
                    getText(item)
                )
            )
        }

        var size = 0
        for (item in items) {
            if (showItem(item)) size++
        }

        //? if forge {
        /*UGraphics.GL.pushMatrix()
        UGraphics.GL.scale(scale, scale, 1f)
        UGraphics.GL.translate(x / scale, y / scale, 0f)
        *///? elif ornithe {
        //GlStateManager.pushMatrix()
        //?} else {
        mcCtx.pose().pushMatrix()
        //?}

        //? if !forge {
        if (background) {
            //? if ornithe {
            //Gui.drawRect(
                //?} else {
            mcCtx.fill(
                //?}
                -backgroundRadius.toInt(),
                -backgroundRadius.toInt(),
                (longestWidth + iconPadding + iconSize).toInt() + backgroundRadius.toInt(),
                (size * offset - itemPadding).toInt() + backgroundRadius.toInt(),
                backgroundColor.argb
            )
        }
        //?}

        var i = 0
        for (item in items) {
            if (!showItem(item)) continue

            val stack = ItemStack(item)
            val itemY = (i * offset).toInt()
            val iconX = 0
            val textX = (iconSize + iconPadding).toInt()

            //? if 1.8.9 {
            /*RenderHelper.enableGUIStandardItemLighting()
            mc.renderItem.zLevel = 200f
            *///?}

            //? if 1.8.9 {
            /*mc.renderItem.renderItemAndEffectIntoGUI(stack, iconX, itemY)
            mc.renderItem.renderItemOverlayIntoGUI(
                mc.fontRendererObj,
                stack,
                0,
                0,
                ""
            )
            *///?} else {
            mcCtx.item(
                stack,
                iconX,
                itemY
            )
            //?}

            //? if 1.8.9 {
            //RenderHelper.disableStandardItemLighting()
            //?}

            //? if forge {
            /*TextRenderer.drawScaledString(
                getText(item),
                textX.toFloat(),
                itemY + mc.fontRendererObj.FONT_HEIGHT / 2f,
                textColor.rgb,
                TextRenderer.TextType.toType(textType),
                1f
            )
            *///?} else {
            val textY = itemY + (
                    16 -
                            //? if 1.8.9 {
                            //mc.fontRendererObj.FONT_HEIGHT
                            //?} else {
                            mc.font.lineHeight
                    //?}
                    ) / 2

            if (textShadow) {
                //? if ornithe {
                /*mc.fontRendererObj.drawString(
                    getText(item),
                    (textX + 1).toFloat(),
                    (textY + 1).toFloat(),
                    textShadowColor.argb,
                    false
                )
                *///?} else {
                mcCtx.text(
                    mc.font,
                    getText(item),
                    textX + 1,
                    textY + 1,
                    textShadowColor.argb,
                    false
                )
                //?}
            }

            //? if ornithe {
            /*mc.fontRendererObj.drawString(
                getText(item),
                textX.toFloat(),
                textY.toFloat(),
                txtColor.argb,
                false
            )
            *///?} else {
            mcCtx.text(
                mc.font,
                getText(item),
                textX,
                textY,
                txtColor.argb,
                false
            )
            //?}

            //?}

            i++
        }

        //? if forge {
        //UGraphics.GL.popMatrix()
        //?} elif ornithe {
        //GlStateManager.popMatrix()
        //?} else {
        mcCtx.pose().popMatrix()
        //?}

        actualWidth = longestWidth + iconPadding + iconSize
        actualHeight = size * offset - itemPadding
    }

    //? if forge {
    /*override fun getWidth(scale: Float, example: Boolean): Float =
        actualWidth * scale

    override fun getHeight(scale: Float, example: Boolean): Float =
        actualHeight * scale

    override fun shouldShow(): Boolean =
        super.shouldShow() && (HyModConfig.debugModeEnabled || HypixelPackets.inBedwars)
    *///?} else {
    override val width: Float = actualWidth
    override val height: Float = actualHeight
    override fun minimumSize(): Pair<Float, Float> = actualWidth to actualHeight
    override fun defaultPosition(): Pair<Float, Float> = 0f to 0f
    override fun update(): Boolean = true
    override fun multipleInstancesAllowed(): Boolean = true
    override fun deletable(): Boolean = true
    //?}
}
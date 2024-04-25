@file:Suppress("UnstableAPIUsage")

package me.imtoggle.armorcrafter

import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.libs.universal.UResolution
import cc.polyfrost.oneconfig.platform.Platform
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import cc.polyfrost.oneconfig.utils.dsl.mc
import cc.polyfrost.oneconfig.utils.dsl.nanoVGHelper
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class CraftButton(width: Int, height: Int, colorPalette: ColorPalette, item: Item) : BasicButton(width, height, "", 2, colorPalette) {
    data class RenderInfo(val x: Float, val y: Float)

    var renderInfo: RenderInfo? = null
    val stack = ItemStack(item)
    val itemSize = 16f
    var mcScaling = false

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    fun draw(vg: Long, x: Float, y: Float, inputHandler: InputHandler, mcScaling: Boolean) {
        this.mcScaling = mcScaling
        this.x = x
        this.y = y
        this.update(x, y, inputHandler)
        nanoVGHelper.drawRoundedRect(vg, x, y, width.toFloat(), height.toFloat(), currentColor, 4f)
        renderInfo = RenderInfo(x + (width - itemSize) / 2f, y + (height - itemSize) / 2f)
    }

    override fun update(x: Float, y: Float, inputHandler: InputHandler) {
        val scale = if (mcScaling) UResolution.scaleFactor.toFloat() else 1f

        hovered = inputHandler.isAreaHovered(x * scale, y * scale, width * scale, height * scale)
        pressed = hovered && Platform.getMousePlatform().isButtonDown(0)
        clicked = inputHandler.isClicked(false) && hovered

        if (clicked) {
            toggled = !toggled
            onClick()
        }

        currentColor = if (hoverFx) colorAnimation.getColor(hovered, pressed)
        else colorAnimation.getColor(false, false)
    }

    @SubscribeEvent
    fun draw(e: GuiScreenEvent.DrawScreenEvent.Post) {
        val (x, y) = renderInfo ?: return
        GlStateManager.pushMatrix()
        if (!mcScaling) {
            val unScale = 1 / UResolution.scaleFactor
            GlStateManager.scale(unScale, unScale, 1.0)
        }
        GlStateManager.translate(x, y, 0f)
        RenderHelper.enableGUIStandardItemLighting()
        mc.renderItem.renderItemAndEffectIntoGUI(stack, 0, 0)
        RenderHelper.disableStandardItemLighting()
        GlStateManager.popMatrix()
        renderInfo = null
    }

}
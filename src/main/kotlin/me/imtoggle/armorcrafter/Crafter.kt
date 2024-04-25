package me.imtoggle.armorcrafter

import cc.polyfrost.oneconfig.utils.dsl.mc
import me.imtoggle.armorcrafter.mixin.GuiContainerAccessor
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.gui.inventory.GuiCrafting
import net.minecraft.init.Items
import net.minecraft.item.Item

object Crafter {

    val materials = listOf(Items.diamond, Items.iron_ingot, Items.gold_ingot, Items.leather)
    val HELMET = listOf(1, 2, 3, 4, 6)
    val CHESTPLATE = listOf(1, 3, 4, 5, 6, 7, 8, 9)
    val LEGGING = listOf(1, 2, 3, 4, 6, 7, 9)
    val BOOTS = listOf(4, 6, 7, 9)

    fun craft(slots: List<Int>) {
        if (mc.currentScreen !is GuiCrafting) return
        val container = mc.currentScreen as GuiContainer
        val accessor = container as GuiContainerAccessor
        val needed = slots.size
        val material = getMaterial(needed) ?: return
        for (i in 1..9) {
            accessor.clickSlot(null, i, 0, 1)
        }
        for (i in container.inventorySlots.inventorySlots) {
            if (i.hasStack && i.stack.item == material) {
                accessor.clickSlot(i, 0, 0, 0)
                accessor.clickSlot(i, 0, 0, 6)
                while (mc.thePlayer.inventory.itemStack.stackSize > slots.size) {
                    accessor.clickSlot(i, 0, 1, 0)
                }
                break
            }
        }
        for (i in slots) {
            accessor.clickSlot(null, i, 1, 0)
        }
        accessor.clickSlot(null, 0, 0, 1)
        accessor.draggedStack = null
        mc.thePlayer.inventory.itemStack = null
        container.updateScreen()
    }

    fun getMaterial(needed: Int): Item? {
        for (i in materials) {
            if (getItemAmount(i) >= needed) return i
        }
        return null
    }

    private fun getItemAmount(item: Item): Int {
        return mc.thePlayer.inventory.mainInventory.toMutableList().filter {
            it?.item == item
        }.sumOf {
            it.stackSize
        }
    }
}
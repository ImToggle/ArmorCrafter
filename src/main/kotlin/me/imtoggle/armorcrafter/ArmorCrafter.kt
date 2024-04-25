package me.imtoggle.armorcrafter

import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import net.minecraft.init.Items
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = ArmorCrafter.MODID, name = ArmorCrafter.NAME, version = ArmorCrafter.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object ArmorCrafter {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    private val helmet = CraftButton(18, 18, ColorPalette.SECONDARY, Items.iron_helmet)
    private val chestplate = CraftButton(18, 18, ColorPalette.SECONDARY, Items.iron_chestplate)
    private val leggings = CraftButton(18, 18, ColorPalette.SECONDARY, Items.iron_leggings)
    private val boots = CraftButton(18, 18, ColorPalette.SECONDARY, Items.iron_boots)
    val buttons = listOf(helmet, chestplate, leggings, boots)

    init {
        helmet.setClickAction {
            Crafter.craft(Crafter.HELMET)
        }
        chestplate.setClickAction {
            Crafter.craft(Crafter.CHESTPLATE)
        }
        leggings.setClickAction {
            Crafter.craft(Crafter.LEGGING)
        }
        boots.setClickAction {
            Crafter.craft(Crafter.BOOTS)
        }
    }

    val inputHandler = InputHandler()

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig
    }

}
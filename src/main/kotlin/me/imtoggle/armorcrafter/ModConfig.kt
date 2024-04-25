package me.imtoggle.armorcrafter

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*

object ModConfig : Config(Mod(ArmorCrafter.NAME, ModType.UTIL_QOL), "${ArmorCrafter.MODID}.json") {

    init {
        initialize()
    }
}
package me.olios.plugins.areaLock.utils

import org.bukkit.Location
import org.bukkit.Material

object Validator {

    fun validateArea(loc1: Location?, loc2: Location?, blockType: String): Boolean {
        return validateLocation(loc1) &&
                validateLocation(loc2) &&
                validateLocationsWorld(loc1!!, loc2!!) &&
                validateBlock(blockType)
    }

    private fun validateLocation(location: Location?): Boolean {
        return location != null
    }

    private fun validateLocationsWorld(loc1: Location, loc2: Location): Boolean {
        return loc1.world == loc2.world
    }

    private fun validateBlock(blockType: String): Boolean {
        val validBlocks = Material.getMaterial(blockType)
        return validBlocks != null
    }

}
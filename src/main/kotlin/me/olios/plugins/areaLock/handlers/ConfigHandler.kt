package me.olios.plugins.areaLock.handlers

import me.olios.plugins.areaLock.AreaLock
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ConfigHandler {
    private val plugin = AreaLock.getInstance()
    private val dataFile = File(plugin.dataFolder, "data.yml")
    private val config = YamlConfiguration.loadConfiguration(dataFile)

    init {
        // creating the dat.yml file if not existing
        if (!dataFile.exists()) {
            try {
                plugin.saveResource("data.yml", false)
            } catch (e: IOException) {
                plugin.logger.severe("Could not create data.yml! ${e.message}")
            }
        }
    }

    fun saveArea(name: String, world: String, blockType: String, loc1: Location, loc2: Location) {
        val areaPath = "area.$name"

        config.set("$areaPath.world", world)
        config.set("$areaPath.type", blockType)
        saveLocation(loc1, "$areaPath.pos1")
        saveLocation(loc2, "$areaPath.pos2")
    }

    private fun saveLocation(loc: Location, path: String) {
        val x = loc.x.toInt()
        val y = loc.x.toInt()
        val z = loc.x.toInt()

        config.set(path, x)
        config.set(path, y)
        config.set(path, z)
    }
}
package me.olios.plugins.areaLock.configs

import me.olios.plugins.areaLock.AreaLock
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

object DataManager {
    private val plugin = AreaLock.getInstance()
    private val dataFile = File(plugin.dataFolder, "data.yml")
    private val data = YamlConfiguration.loadConfiguration(dataFile)

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

        data.set("$areaPath.world", world)
        data.set("$areaPath.type", blockType)
        saveLocation(loc1, "$areaPath.pos1")
        saveLocation(loc2, "$areaPath.pos2")

        saveConfig()
    }

    private fun saveLocation(loc: Location, path: String) {
        val x = loc.x.toInt()
        val y = loc.y.toInt()
        val z = loc.z.toInt()

        data.set("$path.x", x)
        data.set("$path.y", y)
        data.set("$path.z", z)
    }

    private fun saveConfig() {
        try {
            data.save(dataFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save data.yml! ${e.message}")
        }
    }

}
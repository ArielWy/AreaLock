package me.olios.plugins.areaLock.configs

import me.olios.plugins.areaLock.AreaLock
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

object DataConfigManager {
    private val plugin = AreaLock.getInstance()
    private val dataFile = File(plugin.dataFolder, "data.yml")
    private val config = YamlConfiguration.loadConfiguration(dataFile)

    init {
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }
    }

    fun saveArea(name: String, world: String, blockType: String, loc1: Location, loc2: Location) {
        val areaPath = "area.$name"

        config.set("$areaPath.world", world)
        config.set("$areaPath.type", blockType)
        saveLocation(loc1, "$areaPath.pos1")
        saveLocation(loc2, "$areaPath.pos2")

        saveConfig()
    }

    fun getConfig(): FileConfiguration = config

    private fun saveLocation(loc: Location, path: String) {
        config.set("$path.x", loc.blockX)
        config.set("$path.y", loc.blockY)
        config.set("$path.z", loc.blockZ)
    }

    private fun saveConfig() {
        try {
            config.save(dataFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save data.yml! ${e.message}")
        }
    }

    fun getRegion(key: String): RegionData? {
        val base = "area.$key"

        val world = config.getString("$base.world") ?: return null
        val type = config.getString("$base.type") ?: return null

        val pos1 = getLocation("$base.pos1", world) ?: return null
        val pos2 = getLocation("$base.pos2", world) ?: return null

        return RegionData(world, type, pos1, pos2)
    }


    fun getAllRegions(): Map<String, RegionData> {
        val regions = mutableMapOf<String, RegionData>()

        val areaSection = config.getConfigurationSection("area") ?: return regions
        for (key in areaSection.getKeys(false)) {
            val base = "area.$key"
            val world = config.getString("$base.world") ?: continue
            val type = config.getString("$base.type") ?: continue

            val pos1 = getLocation("$base.pos1", world) ?: continue
            val pos2 = getLocation("$base.pos2", world) ?: continue

            regions[key] = RegionData(world, type, pos1, pos2)
        }

        return regions
    }

    private fun getLocation(path: String, world: String): Location? {
        val x = config.getInt("$path.x")
        val y = config.getInt("$path.y")
        val z = config.getInt("$path.z")
        return Bukkit.getWorld(world)?.let { Location(it, x.toDouble(), y.toDouble(), z.toDouble()) }
    }

    fun renameRegion(regionName: String, newValue: String): Boolean {
        val region = getRegion(regionName) ?: return false

        // Save the region under the new name
        saveArea(newValue, region.world, region.type, region.pos1, region.pos2)

        // Remove the old entry
        config.set("area.$regionName", null)
        saveConfig()

        return true
    }

    fun editRegion(
        name: String,
        world: String? = null,
        type: String? = null,
        pos1: Location? = null,
        pos2: Location? = null
    ): Boolean {
        val region = getRegion(name) ?: return false

        val finalWorld = world ?: region.world
        val finalType = type ?: region.type
        val finalPos1 = pos1 ?: region.pos1
        val finalPos2 = pos2 ?: region.pos2

        saveArea(name, finalWorld, finalType, finalPos1, finalPos2)
        return true
    }

    fun deleteRegion(name: String): Boolean {
        if (!config.contains("area.$name")) return false
        config.set("area.$name", null)
        try {
            config.save(dataFile)
            return true
        } catch (e: IOException) {
            plugin.logger.severe("Failed to delete region '$name': ${e.message}")
        }
        return false
    }


}
package me.olios.plugins.areaLock.handlers

import me.olios.plugins.areaLock.AreaLock
import me.olios.plugins.areaLock.configs.DataConfigManager
import me.olios.plugins.areaLock.configs.RegionData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

object RegionChunkHandler {
    val plugin = AreaLock.getInstance()

    fun handleChunkLoad(player: Player, chunkX: Int, chunkZ: Int) {
        val configManager = DataConfigManager
        val allRegions = configManager.getAllRegions()
        val world = player.world.name

        for ((name, area) in allRegions) {
            if (area.world != world) continue

            // If the area intersects with this chunk
            if (area.containsChunk(chunkX, chunkZ)) {
                val permission = "arealock.region.$name"
                if (player.hasPermission(permission)) return // don't send fake blocks if it has permission

                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    sendFakeBlocks(player, area) }, 2L) // Delay by 2 ticks

                player.world.getChunkAt(chunkX, chunkZ).load() // load the chunk if not loaded
                sendFakeBlocks(player, area) // send fake blocks
            }
        }
    }

    private fun sendFakeBlocks(player: Player, area: RegionData) {
        // iterate over all blocks in the region and send block change packets
        val world = Bukkit.getWorld(area.world) ?: return

        for (x in area.minX..area.maxX) {
            for (y in area.minY..area.maxY) {
                for (z in area.minZ..area.maxZ) {
                    val loc = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
                    player.sendBlockChange(loc, Material.valueOf(area.type).createBlockData())
                }
            }
        }
    }
}

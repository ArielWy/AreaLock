package me.olios.plugins.areaLock.handlers

import me.olios.plugins.areaLock.AreaLock
import me.olios.plugins.areaLock.configs.DataConfigManager
import me.olios.plugins.areaLock.configs.RegionData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

object RegionChunkHandler {
    private val plugin = AreaLock.getInstance()

    fun checkLoadedChunks(player: Player) {
        val chunkRadius = Bukkit.getViewDistance() // or use a fixed value if needed

        val centerChunkX = player.location.chunk.x
        val centerChunkZ = player.location.chunk.z

        for (dx in -chunkRadius..chunkRadius) {
            for (dz in -chunkRadius..chunkRadius) {
                val chunkX = centerChunkX + dx
                val chunkZ = centerChunkZ + dz

                handleChunkLoad(player, chunkX, chunkZ)
            }
        }
    }

    fun handleChunkLoad(player: Player, chunkX: Int, chunkZ: Int) {
        val configManager = DataConfigManager
        val allRegions = configManager.getAllRegions()
        val world = player.world.name

        for ((name, area) in allRegions) {
            if (area.world != world) continue

            // If the area intersects with this chunk
            if (area.containsChunk(chunkX, chunkZ)) {
                val permission = "arealock.region.$name"
                if (player.hasPermission(permission)) {
                    // don't send fake blocks if player has permission and viewMode is off
                    if (!ViewModeHandler.isEnabled(player.uniqueId))
                        return
                }

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

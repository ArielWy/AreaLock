package me.olios.plugins.regionLock.selection

import me.olios.plugins.regionLock.RegionLock
import me.olios.plugins.regionLock.configs.DataConfigManager
import me.olios.plugins.regionLock.data.Region
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

object ChunkRegionHandler {
    private val plugin = RegionLock.getInstance()

    fun reloadChunks(player: Player) {
        val chunkRadius = Bukkit.getViewDistance() // radius to load

        val centerChunkX = player.location.chunk.x
        val centerChunkZ = player.location.chunk.z

        for (dx in -chunkRadius..chunkRadius) {
            for (dz in -chunkRadius..chunkRadius) {
                val chunkX = centerChunkX + dx
                val chunkZ = centerChunkZ + dz

                val world = player.world
                val chunk = world.getChunkAt(chunkX, chunkZ)

                if (chunk.isLoaded) {
                    chunk.unload(true) // unload with save
                }

                chunk.load(true) // reload
            }
        }
    }

    fun handleChunkLoad(player: Player, chunkX: Int, chunkZ: Int) {
        val configManager = DataConfigManager
        val allRegions = configManager.getAllRegions()
        val world = player.world.name

        for ((name, region) in allRegions) {
            if (region.world != world) continue

            // If the region intersects with this chunk
            if (region.containsChunk(chunkX, chunkZ)) {
                val permission = "RegionLock.region.$name"
                if (player.hasPermission(permission)) {
                    // don't send fake blocks if player has permission and viewMode is off
                    if (!ViewModeHandler.isEnabled(player.uniqueId))
                        return
                }

                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    sendFakeBlocks(player, region) }, 2L) // Delay by 2 ticks

                player.world.getChunkAt(chunkX, chunkZ).load() // load the chunk if not loaded
                sendFakeBlocks(player, region) // send fake blocks
            }
        }
    }

    private fun sendFakeBlocks(player: Player, region: Region) {
        // iterate over all blocks in the region and send block change packets
        val world = Bukkit.getWorld(region.world) ?: return

        for (x in region.minX..region.maxX) {
            for (y in region.minY..region.maxY) {
                for (z in region.minZ..region.maxZ) {
                    val loc = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
                    player.sendBlockChange(loc, Material.valueOf(region.type).createBlockData())
                }
            }
        }
    }
}

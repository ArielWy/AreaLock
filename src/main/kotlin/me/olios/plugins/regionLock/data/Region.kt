package me.olios.plugins.regionLock.data

import org.bukkit.Location

data class Region(
    val world: String,
    val type: String,
    val pos1: Location,
    val pos2: Location
) {
    val minX = minOf(pos1.blockX, pos2.blockX)
    val maxX = maxOf(pos1.blockX, pos2.blockX)
    val minY = minOf(pos1.blockY, pos2.blockY)
    val maxY = maxOf(pos1.blockY, pos2.blockY)
    val minZ = minOf(pos1.blockZ, pos2.blockZ)
    val maxZ = maxOf(pos1.blockZ, pos2.blockZ)

    fun containsChunk(chunkX: Int, chunkZ: Int): Boolean {
        // Calculate the chunk's block boundaries
        val chunkMinBlockX = chunkX shl 4       // chunkX * 16
        val chunkMaxBlockX = (chunkX shl 4) + 15 // chunkX * 16 + 15

        val chunkMinBlockZ = chunkZ shl 4       // chunkZ * 16
        val chunkMaxBlockZ = (chunkZ shl 4) + 15 // chunkZ * 16 + 15

        val intersectsX = (minX <= chunkMaxBlockX) && (maxX >= chunkMinBlockX)

        val intersectsZ = (minZ <= chunkMaxBlockZ) && (maxZ >= chunkMinBlockZ)

        return intersectsX && intersectsZ
    }
}
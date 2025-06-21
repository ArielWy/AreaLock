package me.olios.plugins.areaLock.configs

import org.bukkit.Location

data class RegionData(
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

    /**
     *     fun containsChunks(chunkX: Int, chunkZ: Int): Boolean {
     *         val blockX = chunkX shl 4
     *         val blockZ = chunkZ shl 4
     *
     *         val intersectsX = blockX in minX..maxX || (blockX + 15) in minX..maxX ||
     *                 minX in blockX..(blockX + 15) || maxX in blockX..(blockX + 15)
     *         val intersectsZ = blockZ in minZ..maxZ || (blockZ + 15) in minZ..maxZ ||
     *                 minZ in blockZ..(blockZ + 15) || maxZ in blockZ..(blockZ + 15)
     *
     *         return intersectsX && intersectsZ
     *     }
     */
}
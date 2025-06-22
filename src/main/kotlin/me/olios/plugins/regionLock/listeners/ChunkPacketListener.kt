package me.olios.plugins.regionLock.listeners

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import me.olios.plugins.regionLock.RegionLock
import me.olios.plugins.regionLock.selection.ChunkRegionHandler
import org.bukkit.entity.Player

class ChunkPacketListener {

    fun register() {
        val plugin = RegionLock.getInstance()

        // listen for chunk load by the player
        ProtocolLibrary.getProtocolManager().addPacketListener(
            object : PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.MAP_CHUNK) {
                override fun onPacketSending(event: PacketEvent) {
                    val player: Player = event.player
                    val packet = event.packet
                    val chunkX = packet.integers.read(0)
                    val chunkZ = packet.integers.read(1)

                    ChunkRegionHandler.handleChunkLoad(
                        player,
                        chunkX,
                        chunkZ,
                    )
                }
            }
        )
    }
}
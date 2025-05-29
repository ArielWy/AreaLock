package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.handlers.SelectionHandler
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class AreaSelectCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val pos = sender.location.subtract(0.0, 1.0, 0.0)
        val playerUUID = sender.uniqueId

        if (args.isEmpty()) {
            return true
        }

        if (args[0] == "1") {
            DataHandler.setSelectionPos1(playerUUID, pos)

            // debug messages
            sender.sendMessage("selection 1: [${sender.location.x.toInt()},${sender.location.y.toInt()}, ${sender.location.z.toInt()}]")
            debugMessage(sender)

            return true
        }

        if (args[0] == "2") {
            DataHandler.setSelectionPos2(playerUUID, pos)

            // debug messages
            sender.sendMessage("selection 2: [${sender.location.x.toInt()},${sender.location.y.toInt()}, ${sender.location.z.toInt()}]")
            debugMessage(sender)

            return true
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return listOf("1", "2")
    }



    fun debugMessage(sender: Player) {
        val handler = DataHandler
        val uuid = sender.uniqueId
        sender.sendMessage("pos1 = ${handler.getSelectionPos1(uuid)}")
        sender.sendMessage("pos2 = ${handler.getSelectionPos2(uuid)}")
    }

}
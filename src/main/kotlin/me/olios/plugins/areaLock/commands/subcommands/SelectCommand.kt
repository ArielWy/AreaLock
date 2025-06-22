package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.handlers.SelectionHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val pos = sender.location.subtract(0.0, 1.0, 0.0)
        val playerUUID = sender.uniqueId

        if (args.isEmpty()) {
            SelectionHandler.setLatestSelection(playerUUID, pos)
            return true
        }

        if (args[0] == "1") {
            SelectionHandler.setPos1(playerUUID, pos)
            return true
        }

        if (args[0] == "2") {
            SelectionHandler.setPos2(playerUUID, pos)
            return true
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.size == 1) listOf("1", "2") else emptyList()
    }
}
package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.handler.SelectionHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectionCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (args.isEmpty()) return false

        val pos = sender.location.subtract(0.0, 1.0, 0.0)
        val playerUUID = sender.uniqueId

        if (args[1] == "1") {
            SelectionHandler().setSelectionPos1(playerUUID, pos)
            return true
        } else if (args[1] == "2") {
            SelectionHandler().setSelectionPos2(playerUUID, pos)
            return true
        } else return false
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return listOf("1", "2")
    }
}
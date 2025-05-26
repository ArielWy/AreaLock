package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.handlers.SelectionHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AreaSelectCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        val handler = SelectionHandler()
        val pos = sender.location.subtract(0.0, 1.0, 0.0)
        val playerUUID = sender.uniqueId

        if (args.isEmpty()) {
            handler.setLatestSelection(playerUUID, pos)
            return true }

        if (args[1] == "1") {
            SelectionHandler().setSelectionPos1(playerUUID, pos)
            return true }

        if (args[1] == "2") {
            SelectionHandler().setSelectionPos2(playerUUID, pos)
            return true }

        handler.setLatestSelection(playerUUID, pos)
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return listOf("1", "2")
    }
}
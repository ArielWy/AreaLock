package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.selection.ChunkRegionHandler
import me.olios.plugins.areaLock.selection.ViewModeHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ViewCommand : SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /al view <true|false>")
            return true
        }

        val enable = args[0].toBooleanStrictOrNull()
        if (enable == null) {
            sender.sendMessage("§cPlease provide either true or false.")
            return true
        }

        ViewModeHandler.toggle(sender.uniqueId, enable)
        sender.sendMessage("§aView mode: ${if (enable) "enabled" else "disabled"}")
        ChunkRegionHandler.reloadChunks(sender)
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.size == 1) listOf("true", "false") else emptyList()
    }
}
package me.olios.plugins.regionLock.commands.subcommands

import me.olios.plugins.regionLock.commands.SubCommand
import me.olios.plugins.regionLock.configs.DataConfigManager
import org.bukkit.command.CommandSender

class ListCommand() : SubCommand {
    private val configManager = DataConfigManager
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (!sender.hasPermission("RegionLock.admin")) {
            sender.sendMessage("§cYou don't have permission to do that.")
            return true
        }

        val regions = configManager.getAllRegions().keys
        if (regions.isEmpty()) {
            sender.sendMessage("§7No regions found.")
        } else {
            sender.sendMessage("§aDefined regions:")
            regions.forEach { sender.sendMessage("§e- $it") }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>) = emptyList<String>()
}

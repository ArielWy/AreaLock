package me.olios.plugins.regionLock.commands.subcommands

import me.olios.plugins.regionLock.RegionLock
import me.olios.plugins.regionLock.commands.SubCommand
import org.bukkit.command.CommandSender

class ReloadCommand() : SubCommand {
    private val plugin = RegionLock.getInstance()
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        plugin.reloadConfig()
        sender.sendMessage("§aRegionLock configuration reloaded.")
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> = emptyList()
}

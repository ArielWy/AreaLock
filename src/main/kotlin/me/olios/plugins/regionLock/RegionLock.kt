package me.olios.plugins.regionLock

import me.olios.plugins.regionLock.commands.RegionLockCommand
import me.olios.plugins.regionLock.commands.SubCommandManager
import me.olios.plugins.regionLock.commands.subcommands.*
import me.olios.plugins.regionLock.listeners.ChunkPacketListener
import org.bukkit.plugin.java.JavaPlugin

class RegionLock : JavaPlugin() {

    companion object {
        // define the plugin instance
        private lateinit var instance: RegionLock
        fun getInstance() = instance
    }

    override fun onEnable() {
        instance = this

        registerCommands()

        ChunkPacketListener().register()
    }

    private fun registerCommands() {
        getCommand("RegionLock")?.setExecutor(RegionLockCommand())

        SubCommandManager.registerCommand("select", SelectCommand())
        SubCommandManager.registerCommand("create", CreateCommand())
        SubCommandManager.registerCommand("list", ListCommand())
        SubCommandManager.registerCommand("view", ViewCommand())
        SubCommandManager.registerCommand("reload", ReloadCommand())
        SubCommandManager.registerCommand("edit", EditCommand())
        SubCommandManager.registerCommand("delete", DeleteCommand())

    }
}

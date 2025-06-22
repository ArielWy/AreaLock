package me.olios.plugins.areaLock

import me.olios.plugins.areaLock.commands.AreaLockCommand
import me.olios.plugins.areaLock.commands.SubCommandManager
import me.olios.plugins.areaLock.commands.subcommands.*
import me.olios.plugins.areaLock.listeners.ChunkPacketListener
import org.bukkit.plugin.java.JavaPlugin

class AreaLock : JavaPlugin() {

    companion object {
        // define the plugin instance
        private lateinit var instance: AreaLock
        fun getInstance() = instance
    }

    override fun onEnable() {
        instance = this

        registerCommands()

        ChunkPacketListener().register()
    }

    private fun registerCommands() {
        getCommand("arealock")?.setExecutor(AreaLockCommand())

        SubCommandManager.registerCommand("select", SelectCommand())
        SubCommandManager.registerCommand("create", CreateCommand())
        SubCommandManager.registerCommand("list", ListCommand())
        SubCommandManager.registerCommand("view", ViewCommand())
        SubCommandManager.registerCommand("reload", ReloadCommand())
        SubCommandManager.registerCommand("edit", EditCommand())
        SubCommandManager.registerCommand("delete", DeleteCommand())

    }
}

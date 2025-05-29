package me.olios.plugins.areaLock

import me.olios.plugins.areaLock.commands.AreaLockCommand
import me.olios.plugins.areaLock.commands.SubCommandManager
import me.olios.plugins.areaLock.commands.subcommands.AreaCreateCommand
import me.olios.plugins.areaLock.commands.subcommands.AreaSelectCommand
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
    }

    private fun registerCommands() {
        getCommand("arealock")?.setExecutor(AreaLockCommand())

        SubCommandManager.registerCommand("select", AreaSelectCommand())
        SubCommandManager.registerCommand("create", AreaCreateCommand())
    }
}

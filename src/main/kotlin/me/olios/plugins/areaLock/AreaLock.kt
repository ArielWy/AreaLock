package me.olios.plugins.areaLock

import me.olios.plugins.areaLock.commands.AreaLockCommand
import me.olios.plugins.areaLock.commands.SubCommandManager
import me.olios.plugins.areaLock.commands.subcommands.AreaCreateCommand
import me.olios.plugins.areaLock.commands.subcommands.AreaSelectCommand
import me.olios.plugins.areaLock.utils.chat.LangManager
import org.bukkit.plugin.java.JavaPlugin

class AreaLock : JavaPlugin() {

    companion object {
        // define the plugin instance
        private lateinit var instance: AreaLock
        fun getInstance() = instance // get command for the plugin instance
    }

    override fun onEnable() {
        instance = this // set plugin instance

        LangManager.init() // init for lang.json
        registerCommands() // registering all commands
    }

    private fun registerCommands() {
        getCommand("arealock")?.setExecutor(AreaLockCommand()) // registering main commands

        SubCommandManager.registerCommand("select", AreaSelectCommand()) // registering select sub-commands
        SubCommandManager.registerCommand("create", AreaCreateCommand()) // registering create sub-commands
    }
}

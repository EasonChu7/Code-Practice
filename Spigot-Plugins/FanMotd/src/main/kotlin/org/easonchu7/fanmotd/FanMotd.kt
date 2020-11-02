package org.easonchu7.fanmotd

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


class FanMotd : JavaPlugin(), Listener {
    var version = description.version

    @EventHandler(priority = EventPriority.HIGH)
    fun onPing(event: ServerListPingEvent) {
        val modlist = config.getStringList("main.motd")
        var mod: String
        val random = Random()
        mod = modlist[random.nextInt(modlist.size)]
        mod = mod.replace("&", "§")
        mod = mod.replace("%VERSION%".toRegex(), Bukkit.getServer().bukkitVersion)
        event.motd = mod
    }

    //Below are the onEnable and onDisable methods.
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        saveDefaultConfig()
    }

    override fun onDisable() {
        saveDefaultConfig()
    }


}

package io.github.arkery

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class TicketHub: JavaPlugin(), Listener {

    override fun onEnable() {
        Bukkit.getLogger().info("[TicketHub]: Launching TicketHub")
    }

    override fun onDisable() {
        Bukkit.getLogger().info("[TicketHub]: Stopping TicketHub")
    }

}

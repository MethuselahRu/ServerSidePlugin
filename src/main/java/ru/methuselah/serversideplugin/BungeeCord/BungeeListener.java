package ru.methuselah.serversideplugin.BungeeCord;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.methuselah.serversideplugin.BungeeCordMain;

public class BungeeListener implements Listener
{
	private final BungeeCordMain plugin;
	public BungeeListener(BungeeCordMain plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onServerConnected(final ServerConnectedEvent event)
	{
		event.getPlayer().sendMessage(new ComponentBuilder("Welcome to " + event.getServer().getInfo().getName() + "!").color(ChatColor.GREEN).create());
	}
}

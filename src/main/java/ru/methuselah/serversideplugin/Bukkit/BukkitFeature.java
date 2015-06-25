package ru.methuselah.serversideplugin.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.simsonic.rscUtilityLibrary.Bukkit.Commands.CommandAnswerException;

public abstract class BukkitFeature
{
	protected final BukkitMain plugin;
	protected BukkitFeature(BukkitMain parent)
	{
		this.plugin = parent;
	}
	public abstract void onLoad();
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onConnected();
	public abstract void onDisconnected();
	public abstract void onCommand(CommandSender sender, Command command, String[] args) throws CommandAnswerException;
}

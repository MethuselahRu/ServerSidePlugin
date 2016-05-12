package ru.methuselah.serversideplugin.Bukkit;

import org.bukkit.command.CommandSender;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.simsonic.rscMinecraftLibrary.Bukkit.CommandAnswerException;

public abstract class BukkitFeature
{
	protected final BukkitMain plugin;
	protected BukkitFeature(BukkitMain plugin)
	{
		this.plugin = plugin;
	}
	public abstract void onLoad();
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onCommand(CommandSender sender, String command, String[] args) throws CommandAnswerException;
}

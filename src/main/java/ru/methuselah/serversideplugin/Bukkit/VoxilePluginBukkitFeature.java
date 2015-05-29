package ru.methuselah.serversideplugin.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.methuselah.serversideplugin.API.PluginComponent;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.simsonic.rscUtilityLibrary.Bukkit.Commands.CommandAnswerException;

public abstract class VoxilePluginBukkitFeature extends PluginComponent
{
	protected final BukkitMain plugin;
	protected VoxilePluginBukkitFeature(BukkitMain parent)
	{
		this.plugin = parent;
	}
	public abstract void onCommand(CommandSender sender, Command command, String[] args) throws CommandAnswerException;
}

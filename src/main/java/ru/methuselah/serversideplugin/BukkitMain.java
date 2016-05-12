package ru.methuselah.serversideplugin;

import java.util.HashSet;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.methuselah.securitylibrary.ProGuardKeep;
import ru.methuselah.serversideplugin.API.BukkitServerSideAPI;
import ru.methuselah.serversideplugin.API.ServerSideAPI;
import ru.methuselah.serversideplugin.API.Settings;
import ru.methuselah.serversideplugin.Bukkit.BukkitFeature;
import ru.methuselah.serversideplugin.Bukkit.BukkitSettings;
import ru.methuselah.serversideplugin.Bukkit.FeatureSetupLinks;
import ru.methuselah.serversideplugin.Bukkit.FeaturePlayerInfo;
import ru.methuselah.serversideplugin.Bukkit.FeatureUpdater;
import ru.simsonic.rscMinecraftLibrary.Bukkit.CommandAnswerException;
import ru.simsonic.rscMinecraftLibrary.Bukkit.GenericChatCodes;

@ProGuardKeep
public final class BukkitMain extends JavaPlugin
{
	public  final static Logger consoleLog = Bukkit.getLogger();
	public  final ServerSideAPI api        = new BukkitServerSideAPI(this);
	private final HashSet<BukkitFeature> features = new HashSet<>();
	@Override
	public void onLoad()
	{
		BukkitSettings.onLoad(this);
		features.add(new FeatureSetupLinks(this));
		features.add(new FeaturePlayerInfo(this));
		features.add(new FeatureUpdater(this));
		for(BukkitFeature feature : features)
			feature.onLoad();
		consoleLog.info("[Methuselah] Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		for(BukkitFeature feature : features)
			feature.onEnable();
		consoleLog.info("[Methuselah] Plugin has been enabled.");
	}
	@Override
	public void onDisable()
	{
		for(BukkitFeature feature : features)
			feature.onDisable();
		getServer().getScheduler().cancelTasks(this);
		getServer().getServicesManager().unregisterAll(this);
		consoleLog.info("[Methuselah] Plugin has been disabled.");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		for(BukkitFeature feature : features)
		{
			try
			{
				feature.onCommand(sender, command.getName(), args);
			} catch(CommandAnswerException ex) {
				for(String answer : ex.getMessageArray())
					sender.sendMessage(GenericChatCodes.processStringStatic(Settings.CHAT_PREFIX + answer));
			}
		}
		return true;
	}
}

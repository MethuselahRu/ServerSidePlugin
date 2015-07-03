package ru.methuselah.serversideplugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.authlib.links.Links;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.securitylibrary.Data.MessagesPlugin.AnswerPluginPlayerInfo;
import ru.methuselah.securitylibrary.ProGuardKeep;
import ru.methuselah.serversideplugin.API.BukkitServerSideAPI;
import ru.methuselah.serversideplugin.API.ServerSideAPI;
import ru.methuselah.serversideplugin.API.Settings;
import ru.methuselah.serversideplugin.Bukkit.BukkitFeature;
import ru.methuselah.serversideplugin.Bukkit.BukkitListener;
import ru.methuselah.serversideplugin.Bukkit.BukkitSettings;
import ru.methuselah.serversideplugin.Engine.Utilities;
import ru.simsonic.rscUtilityLibrary.Bukkit.Commands.CommandAnswerException;
import ru.simsonic.rscUtilityLibrary.TextProcessing.GenericChatCodes;

@ProGuardKeep
public final class BukkitMain extends JavaPlugin
{
	public static final Logger consoleLog = Bukkit.getLogger();
	public final ServerSideAPI api = new BukkitServerSideAPI(this);
	public final HashMap<String, AnswerPluginPlayerInfo> playerInfo = new HashMap<>();
	public final BukkitListener listener = new BukkitListener(this);
	private final HashSet<BukkitFeature> features = new HashSet<>();
	@Override
	public void onLoad()
	{
		BukkitSettings.onLoad(this);
		for(BukkitFeature feature : features)
			feature.onLoad();
		consoleLog.info("[Methuselah] Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(listener, this);
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
			try
			{
				feature.onCommand(sender, command, args);
			} catch(CommandAnswerException ex) {
				for(String answer : ex.getMessageArray())
					sender.sendMessage(GenericChatCodes.processStringStatic(Settings.chatPrefix + answer));
			}
		return true;
	}
}

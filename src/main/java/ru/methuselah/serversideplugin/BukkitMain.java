package ru.methuselah.serversideplugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.AnswerPluginPlayerInfo;
import ru.fourgotten.VoxileSecurity.ProGuardKeep;
import ru.methuselah.authlib.GlobalReplacementList;
import ru.methuselah.serversideplugin.API.BukkitServerSideAPI;
import ru.methuselah.serversideplugin.API.PluginComponent;
import ru.methuselah.serversideplugin.API.ServerSideAPI;
import ru.methuselah.serversideplugin.API.Settings;
import ru.methuselah.serversideplugin.Bukkit.BukkitConfigValidator;
import ru.methuselah.serversideplugin.Bukkit.FeatureBans;
import ru.methuselah.serversideplugin.Bukkit.GenericListener;
import ru.methuselah.serversideplugin.Bukkit.VoxilePluginBukkitFeature;
import ru.methuselah.serversideplugin.Engine.Utilities;
import ru.simsonic.rscUtilityLibrary.Bukkit.Commands.CommandAnswerException;
import ru.simsonic.rscUtilityLibrary.TextProcessing.GenericChatCodes;

@ProGuardKeep
public final class BukkitMain extends JavaPlugin
{
	public static final Logger consoleLog = Bukkit.getLogger();
	public final ServerSideAPI api = new BukkitServerSideAPI(this);
	public final HashMap<String, AnswerPluginPlayerInfo> playerInfo = new HashMap<>();
	public final GenericListener listener = new GenericListener(this);
	private final HashSet<VoxilePluginBukkitFeature> features = new HashSet<>();
	@Override
	public void onLoad()
	{
		BukkitConfigValidator.onLoad(this);
		features.add(new FeatureBans(this));
		for(PluginComponent feature : features)
			feature.onLoad();
		consoleLog.info("[Methuselah] Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(listener, this);
		for(VoxilePluginBukkitFeature feature : features)
			feature.onEnable();
		consoleLog.info("[Methuselah] Plugin has been enabled.");
		Utilities.processReplacements(new GlobalReplacementList());
		consoleLog.info("[Methuselah] Authentication scheme has been applied.");
	}
	@Override
	public void onDisable()
	{
		for(VoxilePluginBukkitFeature feature : features)
			feature.onDisable();
		getServer().getScheduler().cancelTasks(this);
		getServer().getServicesManager().unregisterAll(this);
		consoleLog.info("[Methuselah] Plugin has been disabled.");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		for(VoxilePluginBukkitFeature feature : features)
			try
			{
				feature.onCommand(sender, command, args);
			} catch(CommandAnswerException ex) {
				for(String answer : ex.getMessageArray())
					sender.sendMessage(GenericChatCodes.processStringStatic(Settings.chatPrefix + answer));
			}
		return true;
	}
	public void printDebugStackTrace()
	{
		final StringBuilder sb = new StringBuilder(Settings.chatPrefix);
		sb.append("[DEBUG] An API method was invoked from the path:").append(System.lineSeparator());
		for(StackTraceElement ste : Thread.currentThread().getStackTrace())
		{
			final String className = ste.getClassName();
			if(!className.equals(this.getClass().getName())
				&& !className.equals(Thread.class.getName())
				)
				sb.append(Settings.chatPrefix).append("[DEBUG] ")
					.append(className.startsWith(BukkitMain.class.getPackage().getName()) ? "{_LG}" : "{_LS}")
					.append(ste.toString())
					.append(System.lineSeparator());
		}
		getServer().getConsoleSender().sendMessage(GenericChatCodes.processStringStatic(sb.toString()));
	}
}

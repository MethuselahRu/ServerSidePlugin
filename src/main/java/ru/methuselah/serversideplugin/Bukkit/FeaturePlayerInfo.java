package ru.methuselah.serversideplugin.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.methuselah.securitylibrary.Data.MessagesPlugin.AnswerPluginPlayerInfo;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.simsonic.rscMinecraftLibrary.Bukkit.CommandAnswerException;

public class FeaturePlayerInfo extends BukkitFeature implements Listener
{
	public final HashMap<String, AnswerPluginPlayerInfo> playerInfo = new HashMap<>();
	public FeaturePlayerInfo(BukkitMain plugin)
	{
		super(plugin);
	}
	@Override
	public void onLoad()
	{
	}
	@Override
	public void onEnable()
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		/*
		final OfflinePlayer[] allPlayers = plugin.getServer().getOfflinePlayers();
		final ArrayList<UUID> allUUIDs = new ArrayList<>();
		for(OfflinePlayer player : allPlayers)
			allUUIDs.add(player.getUniqueId());
		// Проверить массив allUUIDs на корректность и удалить неверные
		*/
	}
	@Override
	public void onDisable()
	{
	}
	@Override
	public void onCommand(CommandSender sender, Command command, String[] args) throws CommandAnswerException
	{
		if(args.length > 0 && args[0] != null && !"".equals(args[0]))
		{
			final String subcommand = args[0].toLowerCase();
			switch(subcommand)
			{
				case "whois":
					break;
			}
		}
	}
	@org.bukkit.event.EventHandler
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event)
	{
		/*
		if(plugin.connection.isConnected() == false)
		{
			event.disallow(Result.KICK_OTHER, "Подождите, сервер ещё не готов");
			return;
		}
		final UUID uniqueId = event.getUniqueId();
		if(uniqueId == null)
		{
			event.disallow(Result.KICK_OTHER, "Ошибка определения Вашего UniqueId");
			return;
		}
		final String uuid = uniqueId.toString().replace("-", "").toLowerCase();
		final AnswerPluginPlayerInfo playerInfo = plugin.connection.onPlayerOnline(uuid);
		if(playerInfo == null)
		{
			event.disallow(Result.KICK_OTHER, "Подключение неизвестного UniqueId");
			return;
		}
		plugin.playerInfo.put(uuid, playerInfo);
		plugin.getServer().getScheduler().runTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				BukkitMain.consoleLog.log(Level.INFO, "[Voxile] Подключение игрока: provider = {0}, using launcher = {1}", new Object[]
				{
					playerInfo.authProvider,
					playerInfo.secureLauncher
				});
			}
		});
		*/
	}
	@org.bukkit.event.EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
	}
	@org.bukkit.event.EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
	}
	@org.bukkit.event.EventHandler
	public void onPlayerKick(PlayerKickEvent event)
	{
		final UUID uuid = event.getPlayer().getUniqueId();
		// plugin.connection.onPlayerOffline(uuid);
		playerInfo.remove(uuid.toString().replace("-", "").toLowerCase());
	}
	@org.bukkit.event.EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		final UUID uuid = event.getPlayer().getUniqueId();
		// plugin.connection.onPlayerOffline(uuid);
		playerInfo.remove(uuid.toString().replace("-", "").toLowerCase());
	}
}

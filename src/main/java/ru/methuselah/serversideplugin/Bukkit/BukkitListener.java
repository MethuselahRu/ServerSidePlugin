package ru.methuselah.serversideplugin.Bukkit;

import java.util.UUID;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.methuselah.serversideplugin.BukkitMain;

public class BukkitListener implements Listener
{
	private final BukkitMain plugin;
	public BukkitListener(BukkitMain plugin)
	{
		this.plugin = plugin;
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
		plugin.playerInfo.remove(uuid.toString().replace("-", "").toLowerCase());
	}
	@org.bukkit.event.EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		final UUID uuid = event.getPlayer().getUniqueId();
		// plugin.connection.onPlayerOffline(uuid);
		plugin.playerInfo.remove(uuid.toString().replace("-", "").toLowerCase());
	}
}

package ru.methuselah.serversideplugin.Bukkit;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import ru.methuselah.serversideplugin.BukkitMain;

public class BukkitConfigValidator
{
	public static final String versionPath = "internal.version";
	public static void onLoad(BukkitMain plugin)
	{
		plugin.saveDefaultConfig();
		FileConfiguration config = plugin.getConfig();
		switch(config.getInt(versionPath, 0))
		{
			case 0:
				new File(plugin.getDataFolder(), "config.yml").delete();
				plugin.saveDefaultConfig();
			case 1:
				updateFrom_v1_to_v2(config);
				plugin.saveConfig();
				plugin.reloadConfig();
			case 2:
				// config = plugin.getConfig();
				// updateFrom_v2_to_v3(config);
				// plugin.saveConfig();
				// plugin.reloadConfig();
			default:
				// Default behaviour
				break;
		}
	}
	public static void updateFrom_v1_to_v2(FileConfiguration config)
	{
		setIfNotContains(config, "general.not-so-secret-keyword", "<Enter your no-so-secret keyword>");
		setIfNotContains(config, "players.allow-guests",  true);
		setIfNotContains(config, "players.allow-license", true);
		setIfNotContains(config, "players.allow-scripts", true);
		setIfNotContains(config, "players.allow-voxile",  true);
		setIfNotContains(config, "players.special-guest-group", "Guests");
		setIfNotContains(config, "players.use-special-guest-group", true);
		config.set(versionPath, 2);
		BukkitMain.consoleLog.info("[Methuselah] Configuration version has been updated to version 2.");
	}
	/*
	public static void updateFrom_v2_to_v3(FileConfiguration config)
	{
		config.set(versionPath, 3);
		BukkitMain.consoleLog.info("[Methuselah] Configuration version has been updated to version 3.");
	}
	public static void updateFrom_v3_to_v4(FileConfiguration config)
	{
		config.set(versionPath, 4);
		BukkitMain.consoleLog.info("[Methuselah] Configuration version has been updated to version 4.");
	}
	*/
	public static void setIfNotContains(FileConfiguration config, String path, Object value)
	{
		if(!config.contains(path))
			config.set(path, value);
	}
}

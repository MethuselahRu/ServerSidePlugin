package ru.methuselah.serversideplugin.Bukkit;
import java.util.LinkedList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.fourgotten.VoxileSecurity.Hacks.HacksApplicator;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.AnswerPluginEnabled;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.MessagePluginEnabled;
import ru.methuselah.authlib.GlobalReplacementList;
import ru.methuselah.serversideplugin.Engine.Utilities;
import ru.simsonic.rscUtilityLibrary.Bukkit.Commands.CommandAnswerException;
import ru.simsonic.rscUtilityLibrary.RestartableThread;

public class FeatureIntegration extends VoxilePluginBukkitFeature
{
	private static final int authenticationTimeoutShort = 500;
	private static final int authenticationTimeoutLong = 300000;
	private volatile boolean authenticationApplied;
	private final RestartableThread integration = new RestartableThread()
	{
		@Override
		public void run()
		{
			try
			{
				final MessagePluginEnabled message = new MessagePluginEnabled();
				message.projectCode = plugin.getConfig().getString("general.code", "00000");
				message.notSoSecretKeyword = plugin.getConfig().getString("general.not-so-secret-keyword", "");
				message.serverId = plugin.getServer().getServerId();
				message.serverVersion = plugin.getServer().getVersion();
				message.onlinePlayers = plugin.getServer().getOnlinePlayers().size();
				final AnswerPluginEnabled answer = plugin.connection.onPluginEnable(message);
				for(; !Thread.interrupted(); Thread.sleep(authenticationApplied ? authenticationTimeoutLong : authenticationTimeoutShort))
					if(plugin.isEnabled())
					{
						plugin.getServer().getScheduler().runTask(plugin, new Runnable()
						{
							@Override
							public void run()
							{
								Utilities.processReplacements(answer.replacementList);
								authenticationApplied = true;
								BukkitMain.consoleLog.info("[Methuselah] Authentication scheme has been applied.");
							}
						});
						break;
					}
			} catch(InterruptedException ex) {
			}
		}
	};
	public FeatureIntegration(BukkitMain plugin)
	{
		super(plugin);
	}
	@Override
	public void onLoad()
	{
		Utilities.processReplacements(new GlobalReplacementList());
		BukkitMain.consoleLog.info("[Methuselah] Temporal authentication scheme has been applied.");
	}
	@Override
	public void onEnable()
	{
	}
	@Override
	public void onDisable()
	{
		integration.stop();
	}
	@Override
	public void onConnected()
	{
		
		integration.start();
	}
	@Override
	public void onDisconnected()
	{
	}
	@Override
	public void onCommand(CommandSender sender, Command command, String[] args) throws CommandAnswerException
	{
	}
}

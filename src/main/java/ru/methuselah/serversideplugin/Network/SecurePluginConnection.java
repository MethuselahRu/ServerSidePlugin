package ru.methuselah.serversideplugin.Network;
import java.util.UUID;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.AnswerPluginEnabled;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.AnswerPluginPlayerInfo;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.MessagePluginEnabled;
import ru.fourgotten.VoxileSecurity.SecureConnection.ConnectionEstablishedNotification;
import ru.fourgotten.VoxileSecurity.GenericSecureClient;
import ru.fourgotten.VoxileSecurity.SecureSocketWrapper;

public final class SecurePluginConnection extends GenericSecureClient
{
	public SecurePluginConnection(ConnectionEstablishedNotification notification, boolean autoStart)
	{
		super(notification, autoStart);
	}
	public synchronized AnswerPluginEnabled onPluginEnable(MessagePluginEnabled message)
	{
		final SecureSocketWrapper wrapper = getWorkingWrapper();
		if(wrapper != null)
		{
			wrapper.writeLine("plugin-enable");
			wrapper.writeObject(message, MessagePluginEnabled.class);
			return wrapper.readObject(AnswerPluginEnabled.class);
		}
		return new AnswerPluginEnabled();
	}
	public synchronized void onPluginDisable()
	{
		final SecureSocketWrapper wrapper = getWorkingWrapper();
		if(wrapper != null)
		{
			wrapper.writeLine("plugin-server-shutdown");
		}
	}
	public synchronized AnswerPluginPlayerInfo onPlayerOnline(String uuid)
	{
		final SecureSocketWrapper wrapper = getWorkingWrapper();
		if(wrapper != null)
		{
			wrapper.writeLine("plugin-player-online");
			wrapper.writeLine(uuid);
			return wrapper.readObject(AnswerPluginPlayerInfo.class);
		}
		return null;
	}
	public synchronized void onPlayerOffline(UUID uniqueId)
	{
		final String uuid = uniqueId.toString().replace("-", "").toLowerCase();
		final SecureSocketWrapper wrapper = getWorkingWrapper();
		if(wrapper != null)
		{
			wrapper.writeLine("plugin-player-offline");
			wrapper.writeLine(uuid);
		}
	}
}

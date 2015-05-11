package ru.methuselah.serversideplugin.API;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import ru.fourgotten.VoxileSecurity.Data.Methuselah.ProfileInfoPayload;
import ru.fourgotten.VoxileSecurity.Data.Methuselah.ProfileInfoResponse;
import ru.fourgotten.VoxileSecurity.Methuselah.MethuselahPrivate;
import ru.methuselah.authlib.exceptions.ResponseException;
import ru.methuselah.serversideplugin.BukkitMain;

public final class BukkitServerSideAPI extends ServerSideAPI
{
	private final BukkitMain plugin;
	public BukkitServerSideAPI(BukkitMain plugin)
	{
		this.plugin = plugin;
	}
	private final Map<UUID, PlayerInformation> cachedInfos = new ConcurrentHashMap<>();
	@Override
	public PlayerInformation getPlayerInfo(UUID uuid) throws ResponseException
	{
		if(cachedInfos.containsKey(uuid))
			return cachedInfos.get(uuid);
		final ProfileInfoPayload payload = new ProfileInfoPayload();
		payload.uuid = uuid.toString().replace("-", "");
		final ProfileInfoResponse response = MethuselahPrivate.getProfileInfo(payload);
		cachedInfos.put(uuid, response);
		return response;
	}
}

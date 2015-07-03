package ru.methuselah.serversideplugin.API;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.authlib.methods.ResponseException;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoPayload;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoResponse;
import ru.methuselah.securitylibrary.MethuselahPrivate;
import ru.methuselah.securitylibrary.ProGuardKeep;
import ru.methuselah.serversideplugin.BukkitMain;

@ProGuardKeep
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
		final ProfileInfoResponse response = new MethuselahPrivate(new LinksMethuselah()).profileInfo(payload);
		cachedInfos.put(uuid, response);
		return response;
	}
}

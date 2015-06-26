package ru.methuselah.serversideplugin.API;

import java.util.UUID;
import ru.methuselah.authlib.methods.ResponseException;

public class SpongeServerSideAPI extends ServerSideAPI
{
	@Override
	public PlayerInformation getPlayerInfo(UUID uuid) throws ResponseException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}

package ru.methuselah.serversideplugin.API;

import java.io.File;
import java.io.IOException;
import ru.methuselah.authlib.links.Links;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.simsonic.rscUtilityLibrary.HashAndCipherUtilities;

public final class CustomLinksBuilder
{
	public static class LinksCustomContainer
	{
		public String urlBase;
		public String methodAuthenticate;
		public String methodRefresh;
		public String methodValidate;
		public String methodInvalidate;
		public String methodSignout;
		public String methodJoin;
		public String methodHasJoined;
		public String legacyJoin;
		public String legacyHasJoined;
		public String apiNameToUUID;
		public String apiNameHistory;
		public String apiBulkNames;
		public String apiProfile;
		public LinksCustomContainer()
		{
		}
	}
	public static LinksCustomContainer load(File file) throws IOException
	{
		if(!file.isFile())
		{
			final LinksCustomContainer lccWrite = new LinksCustomContainer();
			final Links defaultLinks = new LinksMethuselah();
			lccWrite.urlBase            = defaultLinks.getBaseURL();
			lccWrite.methodAuthenticate = defaultLinks.getAuthenticate();
			lccWrite.methodRefresh      = defaultLinks.getRefresh();
			lccWrite.methodValidate     = defaultLinks.getValidate();
			lccWrite.methodInvalidate   = defaultLinks.getInvalidate();
			lccWrite.methodSignout      = defaultLinks.getSignout();
			lccWrite.methodJoin         = defaultLinks.getJoin();
			lccWrite.methodHasJoined    = defaultLinks.getHasJoined();
			lccWrite.legacyJoin         = defaultLinks.getLegacyJoin();
			lccWrite.legacyHasJoined    = defaultLinks.getLegacyHasJoined();
			lccWrite.apiNameHistory     = defaultLinks.getNameHistory();
			lccWrite.apiNameToUUID      = defaultLinks.getNameToUUID();
			lccWrite.apiBulkNames       = defaultLinks.getBulkNames();
			lccWrite.apiProfile         = defaultLinks.getProfile();
			try
			{
				HashAndCipherUtilities.saveObject(file, lccWrite, LinksCustomContainer.class);
				System.err.println("[Methuselah] Demonstration file has been created: " + file.getAbsolutePath());
			} catch(IOException | NullPointerException ex) {
				System.err.println("[Methuselah] Cannot save file: " + file.getAbsolutePath() + "\n" + ex);
			}
			return lccWrite;
		}
		try
		{
			return HashAndCipherUtilities.loadObject(file, LinksCustomContainer.class);
		} catch(IOException | NullPointerException ex) {
			System.err.println("[Methuselah] Cannot read file: " + file.getAbsolutePath() + "\n" + ex);
			throw ex;
		}
	}
}

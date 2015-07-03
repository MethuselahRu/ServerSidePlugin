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
			lccWrite.methodAuthenticate = defaultLinks.getAuthenticate().replace(lccWrite.urlBase, "");
			lccWrite.methodRefresh      = defaultLinks.getRefresh().replace(lccWrite.urlBase, "");
			lccWrite.methodValidate     = defaultLinks.getValidate().replace(lccWrite.urlBase, "");
			lccWrite.methodInvalidate   = defaultLinks.getInvalidate().replace(lccWrite.urlBase, "");
			lccWrite.methodSignout      = defaultLinks.getSignout().replace(lccWrite.urlBase, "");
			lccWrite.methodJoin         = defaultLinks.getJoin().replace(lccWrite.urlBase, "");
			lccWrite.methodHasJoined    = defaultLinks.getHasJoined().replace(lccWrite.urlBase, "");
			lccWrite.legacyJoin         = defaultLinks.getLegacyJoin().replace(lccWrite.urlBase, "");
			lccWrite.legacyHasJoined    = defaultLinks.getLegacyHasJoined().replace(lccWrite.urlBase, "");
			lccWrite.apiNameHistory     = defaultLinks.getNameHistory().replace(lccWrite.urlBase, "");
			lccWrite.apiNameToUUID      = defaultLinks.getNameToUUID().replace(lccWrite.urlBase, "");
			lccWrite.apiBulkNames       = defaultLinks.getBulkNames().replace(lccWrite.urlBase, "");
			lccWrite.apiProfile         = defaultLinks.getProfile().replace(lccWrite.urlBase, "");
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

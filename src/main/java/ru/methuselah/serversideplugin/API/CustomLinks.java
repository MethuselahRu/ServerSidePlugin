package ru.methuselah.serversideplugin.API;

import ru.methuselah.authlib.links.Links;

public final class CustomLinks extends Links
{
	public CustomLinks(CustomLinksBuilder.LinksCustomContainer container)
	{
		super(
			container.urlBase,
			container.methodAuthenticate,
			container.methodRefresh,
			container.methodValidate,
			container.methodInvalidate,
			container.methodSignout,
			container.methodJoin,
			container.methodHasJoined,
			container.legacyJoin,
			container.legacyHasJoined,
			container.apiNameToUUID,
			container.apiNameHistory,
			container.apiBulkNames,
			container.apiProfile);
		super.setProvider(Links.LinksProvider.custom);
	}
}

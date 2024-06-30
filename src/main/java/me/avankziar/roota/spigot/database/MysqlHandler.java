package main.java.me.avankziar.roota.spigot.database;

import main.java.me.avankziar.roota.general.database.MysqlBaseHandler;
import main.java.me.avankziar.roota.spigot.RootA;

public class MysqlHandler extends MysqlBaseHandler
{	
	public MysqlHandler(RootA plugin)
	{
		super(plugin.getLogger(), plugin.getMysqlSetup());
	}
}
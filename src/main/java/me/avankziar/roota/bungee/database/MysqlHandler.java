package main.java.me.avankziar.roota.bungee.database;

import main.java.me.avankziar.roota.bungee.RootA;
import main.java.me.avankziar.roota.general.database.MysqlBaseHandler;

public class MysqlHandler extends MysqlBaseHandler
{	
	public MysqlHandler(RootA plugin)
	{
		super(plugin.getLogger(), plugin.getMysqlSetup());
	}
}
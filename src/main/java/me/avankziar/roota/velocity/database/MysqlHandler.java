package main.java.me.avankziar.roota.velocity.database;

import main.java.me.avankziar.roota.general.database.MysqlBaseHandler;
import main.java.me.avankziar.roota.velocity.RootA;

public class MysqlHandler extends MysqlBaseHandler
{	
	public MysqlHandler(RootA plugin)
	{
		super(plugin.getLogger(), plugin.getMysqlSetup());
	}
}

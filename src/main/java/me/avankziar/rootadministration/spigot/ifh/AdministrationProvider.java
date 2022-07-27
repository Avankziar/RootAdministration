package main.java.me.avankziar.rootadministration.spigot.ifh;

import main.java.me.avankziar.ifh.spigot.administration.Administration;
import main.java.me.avankziar.rootadministration.spigot.RootA;

public class AdministrationProvider implements Administration
{
	private RootA plugin;
	
	public AdministrationProvider(RootA plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public String getLanguage()
	{
		return plugin.getYamlHandler().getConfig().getString("Language", "ENG");
	}
	
	@Override
	public String getSpigotServerName()
	{
		return plugin.getYamlHandler().getConfig().getString("Server", null);
	}
	
	private String get(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getString("MySQL."+pathName, null);
	}
	
	private boolean getb(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getBoolean("MySQL."+pathName, false);
	}
	
	@Override
	public String getHost(String pathName)
	{
		return get(pathName+".Host");
	}
	
	@Override
	public int getPort(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getInt("MySQL."+pathName+".Port", 3306);
	}
	
	@Override
	public String getDatabase(String pathName)
	{
		return get(pathName+".DatabaseName");
	}
	
	@Override
	public String getUsername(String pathName)
	{
		return get(pathName+".User");
	}
	
	@Override
	public String getPassword(String pathName)
	{
		return get(pathName+".Password");
	}
	
	@Override
	public boolean isAutoReconnect(String pathName)
	{
		return getb(pathName+".AutoReconnect");
	}
	
	@Override
	public boolean isVerifyServerCertificate(String pathName)
	{
		return getb(pathName+".VerifyServerCertificate");
	}
	
	@Override
	public boolean useSSL(String pathName)
	{
		return getb(pathName+".SSLEnabled");
	}
	
	@Override
	public boolean isRequireSSL(String pathName)
	{
		return getb(pathName+".SSLEnabled");
	}
}

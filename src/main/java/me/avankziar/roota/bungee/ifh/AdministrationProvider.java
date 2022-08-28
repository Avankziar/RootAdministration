package main.java.me.avankziar.roota.bungee.ifh;

import java.util.LinkedHashMap;

import main.java.me.avankziar.ifh.bungee.administration.Administration;
import main.java.me.avankziar.roota.bungee.RootA;

public class AdministrationProvider implements Administration
{
	private RootA plugin;
	private LinkedHashMap<String, String> forwarding = new LinkedHashMap<>();
	
	public AdministrationProvider(RootA plugin)
	{
		this.plugin = plugin;
		for(String entry : plugin.getYamlHandler().getConfig().getStringList("PluginForwarding"))
		{
			String[] s = entry.split(">>>");
			if(s.length != 2)
			{
				continue;
			}
			String k = s[0];
			String v = s[1];
			forwarding.put(k, v);
		}
	}
	
	@Override
	public String getLanguage()
	{
		return plugin.getYamlHandler().getConfig().getString("Language", "ENG");
	}
	
	private String getPath(String pathName)
	{
		String s = forwarding.get(pathName);
		return s != null ? s : pathName;
	}
	
	private String get(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getString("Mysql."+getPath(pathName), null);
	}
	
	private boolean getb(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getBoolean("Mysql."+getPath(pathName), false);
	}
	
	@Override
	public boolean isMysqlPathActive(String pathName)
	{
		return getb(pathName+".IsActive");
	}
	
	@Override
	public String getHost(String pathName)
	{
		return get(pathName+".Host");
	}
	
	@Override
	public int getPort(String pathName)
	{
		return plugin.getYamlHandler().getConfig().getInt("Mysql."+getPath(pathName)+".Port", 3306);
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
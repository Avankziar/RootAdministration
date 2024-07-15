package main.java.me.avankziar.roota.velocity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import main.java.me.avankziar.roota.general.database.YamlHandler;
import main.java.me.avankziar.roota.general.database.YamlManager;
import main.java.me.avankziar.roota.velocity.database.MysqlHandler;
import main.java.me.avankziar.roota.velocity.database.MysqlSetup;
import main.java.me.avankziar.roota.velocity.ifh.AdministrationProvider;
import main.java.me.avankziar.roota.velocity.listener.PlayerObserverListener;
import main.java.me.avankziar.roota.velocity.metric.Metrics;
import me.avankziar.ifh.velocity.IFH;
import me.avankziar.ifh.velocity.plugin.ServicePriority;

@Plugin(
		id = "rootadministration", 
		name = "RootAdministration", 
		version = "1-6-0",
		url = "https://www.spigotmc.org/resources/rootadministration.104833/",
		dependencies = {
				@Dependency(id = "interfacehub")
		},
		description = "A administration plugin for server and mysql data",
		authors = {"Avankziar"}
)
public class RootA
{
	private static RootA plugin;
    private final ProxyServer server;
    private Logger logger = null;
    private Path dataDirectory;
    public String pluginName = "RootAdministration";
    private final Metrics.Factory metricsFactory;
    private YamlHandler yamlHandler;
    private YamlManager yamlManager;
    private MysqlSetup mysqlSetup;
    private MysqlHandler mysqlHandler;
    
    private AdministrationProvider administrationProvider;
    
    @Inject
    public RootA(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) 
    {
    	RootA.plugin = this;
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;
    }
    
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) 
    {
    	logger = Logger.getLogger("RootA");
    	PluginDescription pd = server.getPluginManager().getPlugin(pluginName.toLowerCase()).get().getDescription();
        List<String> dependencies = new ArrayList<>();
        pd.getDependencies().stream().allMatch(x -> dependencies.add(x.getId()));
        //https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=BM
		logger.info("  ██████╗  ██████╗  ██████╗ ████████╗ █████╗ | Id: "+pd.getId());
		logger.info(" ██╔══██╗██╔═══██╗██╔═══██╗╚══██╔══╝██╔══██╗ | Version: "+pd.getVersion().get());
		logger.info(" ██████╔╝██║   ██║██║   ██║   ██║   ███████║ | Author: ["+String.join(", ", pd.getAuthors())+"]");
		logger.info(" ██╔══██╗██║   ██║██║   ██║   ██║   ██╔══██║ | Description: "+(pd.getDescription().isPresent() ? pd.getDescription().get() : "/"));
		logger.info(" ██║  ██║╚██████╔╝╚██████╔╝   ██║   ██║  ██║ | Plugin Website:"+pd.getUrl().get().toString());
		logger.info(" ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝   ╚═╝  ╚═╝ | Dependencies Plugins: ["+String.join(", ", dependencies)+"]");
        
		yamlHandler = new YamlHandler(YamlManager.Type.VELO, pluginName, logger, dataDirectory,
        		(plugin.getAdministration() == null ? null : plugin.getAdministration().getLanguage()));
        setYamlManager(yamlHandler.getYamlManager());
        
        setupIFHProvider();
        String path = plugin.getYamlHandler().getConfig().getString("IFHAdministrationPath");
		boolean adm = plugin.getAdministration() != null 
				&& plugin.getAdministration().isMysqlPathActive(path);
		if(adm || yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlSetup = new MysqlSetup(plugin, adm, path);
			mysqlHandler = new MysqlHandler(plugin);
		}
        setListeners();
        setupBstats();
        
    }
    
    public static RootA getPlugin()
    {
    	return RootA.plugin;
    }
    
    public ProxyServer getServer()
    {
    	return server;
    }
    
    public Logger getLogger()
    {
    	return logger;
    }
    
    public Path getDataDirectory()
    {
    	return dataDirectory;
    }
    
    public YamlHandler getYamlHandler()
    {
    	return yamlHandler;
    }
    
    public YamlManager getYamlManager()
    {
    	return yamlManager;
    }
    
    public void setYamlManager(YamlManager yamlManager)
    {
    	this.yamlManager = yamlManager;
    }
    
    public MysqlSetup getMysqlSetup()
    {
    	return mysqlSetup;
    }
    
    public MysqlHandler getMysqlHandler()
    {
    	return mysqlHandler;
    }
    
    private void setListeners()
    {
    	EventManager em = server.getEventManager();
    	em.register(this, new PlayerObserverListener(plugin));
    }
    
    private void setupIFHProvider()
	{
		Optional<PluginContainer> ifhp = getServer().getPluginManager().getPlugin("interfacehub");
		Optional<PluginContainer> plugin = getServer().getPluginManager().getPlugin(pluginName.toLowerCase());
        if (ifhp.isEmpty()) 
        {
        	logger.info(pluginName + " dont find InterfaceHub!");
            return;
        }
        me.avankziar.ifh.velocity.IFH ifh = IFH.getPlugin();
        try
        {
        	administrationProvider = new AdministrationProvider(getPlugin());
            ifh.getServicesManager().register(
             		me.avankziar.ifh.velocity.administration.Administration.class,
             		administrationProvider, plugin.get(), ServicePriority.Normal);
            logger.info(pluginName + " detected InterfaceHub >>> Administration.class is provided!");
    		
        } catch(NoClassDefFoundError e){}
	}
	
	public AdministrationProvider getAdministration()
	{
		return administrationProvider;
	}
    
    public void setupBstats()
	{
    	int pluginId = 22456;
        metricsFactory.make(this, pluginId);
	}
}
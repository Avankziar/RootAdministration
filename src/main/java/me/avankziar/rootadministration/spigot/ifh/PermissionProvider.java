package main.java.me.avankziar.rootadministration.spigot.ifh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;

import main.java.me.avankziar.ifh.spigot.permission.Permission;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class PermissionProvider implements Permission
{
	private LuckPerms lp;
	
	public PermissionProvider()
	{
		lp = LuckPermsProvider.get();
	}
	
	private User getOnlineUser(Player player)
	{
		return lp.getPlayerAdapter(Player.class).getUser(player);
	}
	
	private CompletableFuture<User> getOfflineUser(UUID uuid)
	{
		return lp.getUserManager().loadUser(uuid);
	}
	
	private long expireIn(Collection<Node> node, String permission)
	{
		for(Node n : node)
		{
			if(n.getKey().equalsIgnoreCase(permission))
			{
				if(n.hasExpiry())
				{
					return n.getExpiryDuration().toMillis();
				} else
				{
					return -1;
				}
			}
		}
		return 0;
	}
	
	public boolean hasPermission(Player player, String permission)
	{
		return getOnlineUser(player).getCachedData().getPermissionData().checkPermission(permission).asBoolean();
	}
	
	public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission)
	{
		return getOfflineUser(uuid)
				.thenApplyAsync(user -> {
					Collection<Node> permnode = user.data().toCollection();
					return permnode.stream().anyMatch(x -> x.getKey().equals(permission));
				});
	}
	
	public long expireIn(Player player, String permission)
	{
		return expireIn(getOnlineUser(player).data().toCollection(), permission);
	}
	
	public long expireIn(UUID uuid, String permission)
	{
		return expireIn(lp.getUserManager().getUser(uuid).getNodes(), permission); 
	}
	
	public void addPermissionAtPlayer(UUID uuid, String permission, Long duration, Map<String, String> additionalContext)
	{
		lp.getUserManager().modifyUser(uuid, u -> 
		{
			if(duration != null && additionalContext != null)
			{
				ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
				additionalContext.forEach(builder::add);
				ImmutableContextSet set = builder.build();
				u.data().add(Node.builder(permission).context(set).expiry(duration).build());
			} else if(duration != null)
			{
				u.data().add(Node.builder(permission).expiry(duration).build());
			} else if(additionalContext != null)
			{
				ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
				additionalContext.forEach(builder::add);
				ImmutableContextSet set = builder.build();
				u.data().add(Node.builder(permission).context(set).build());
			} 
		});
	}
	
	public void removePermissionAtPlayer(UUID uuid, String permission)
	{
		lp.getUserManager().modifyUser(uuid, u -> 
		{
			u.data().remove(Node.builder(permission).build());
		});
	}
	
	public boolean isInGroup(Player player, String group)
	{
		User user = getOnlineUser(player);
		Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
	    return inheritedGroups.stream().anyMatch(g -> g.getName().equals(group));
	}
	
	public boolean hasPermission(String group, String permission)
	{
		return lp.getGroupManager().getGroup(group).data().toCollection().stream().anyMatch(x -> x.getKey().equals(permission));
	}
	
	public ArrayList<String> getGroups()
	{
		ArrayList<String> groups = new ArrayList<>();
		for(Group g : lp.getGroupManager().getLoadedGroups())
		{
			groups.add(g.getName());
		}		
		return groups;
	}
	
	public long expireIn(String group, String permission)
	{
		return expireIn(lp.getGroupManager().getGroup(group).getNodes(), permission);
	}
	
	public void addPermissionAtGroup(String group, String permission, Long duration, Map<String, String> additionalContext)
	{
		lp.getGroupManager().modifyGroup(group, g -> 
		{
			if(duration != null && additionalContext != null)
			{
				ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
				additionalContext.forEach(builder::add);
				ImmutableContextSet set = builder.build();
				g.data().add(Node.builder(permission).context(set).expiry(duration).build());
			} else if(duration != null)
			{
				g.data().add(Node.builder(permission).expiry(duration).build());
			} else if(additionalContext != null)
			{
				ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
				additionalContext.forEach(builder::add);
				ImmutableContextSet set = builder.build();
				g.data().add(Node.builder(permission).context(set).build());
			} 
		});
	}
	
	public void removePermissionAtGroup(String group, String permission)
	{
		lp.getGroupManager().modifyGroup(group, g -> 
		{
			g.data().remove(Node.builder(permission).build());
		});
	}
}
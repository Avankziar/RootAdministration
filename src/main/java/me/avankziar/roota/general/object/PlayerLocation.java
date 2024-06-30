package main.java.me.avankziar.roota.general.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import main.java.me.avankziar.roota.general.database.MysqlBaseHandler;
import main.java.me.avankziar.roota.general.database.MysqlHandable;
import main.java.me.avankziar.roota.general.database.QueryType;

public class PlayerLocation implements MysqlHandable
{
	private String playerName;
	private UUID playerUUID;
	private String server;
	
	public PlayerLocation()
	{
		
	}
	
	public PlayerLocation(String playerName, UUID playerUUID, String server)
	{
		setPlayerName(playerName);
		setPlayerUUID(playerUUID);
		setServer(server);
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public UUID getPlayerUUID()
	{
		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID)
	{
		this.playerUUID = playerUUID;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}
	
	@Override
	public boolean create(Connection conn, String tablename)
	{
		try
		{
			String sql = "INSERT INTO `" + tablename
					+ "`(`player_uuid`, `player_name`, `server_name`) " 
					+ "VALUES(?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, getPlayerName());
	        ps.setString(2, getPlayerUUID().toString());
	        ps.setString(3, getServer());
	        int i = ps.executeUpdate();
	        MysqlBaseHandler.addRows(QueryType.INSERT, i);
	        return true;
		} catch (SQLException e)
		{
			MysqlBaseHandler.getLogger().log(Level.WARNING, "SQLException! Could not create a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public boolean update(Connection conn, String tablename, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "UPDATE `" + tablename
				+ "` SET `player_uuid` = ?, `player_name` = ?, `server_name` = ?"
				+ " WHERE "+whereColumn;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, getPlayerName());
	        ps.setString(2, getPlayerUUID().toString());
	        ps.setString(3, getServer());
			int i = 4;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}			
			int u = ps.executeUpdate();
			MysqlBaseHandler.addRows(QueryType.UPDATE, u);
			return true;
		} catch (SQLException e)
		{
			MysqlBaseHandler.getLogger().log(Level.WARNING, "SQLException! Could not update a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return false;
	}

	@Override
	public ArrayList<Object> get(Connection conn, String tablename, String orderby, String limit, String whereColumn, Object... whereObject)
	{
		try
		{
			String sql = "SELECT * FROM `" + tablename
				+ "` WHERE "+whereColumn+" ORDER BY "+orderby+limit;
			PreparedStatement ps = conn.prepareStatement(sql);
			int i = 1;
			for(Object o : whereObject)
			{
				ps.setObject(i, o);
				i++;
			}
			
			ResultSet rs = ps.executeQuery();
			MysqlBaseHandler.addRows(QueryType.READ, rs.getMetaData().getColumnCount());
			ArrayList<Object> al = new ArrayList<>();
			while (rs.next()) 
			{
				al.add(new PlayerLocation(
						rs.getString("player_name"),
						UUID.fromString(rs.getString("player_uuid")),
						rs.getString("server_name")));
			}
			return al;
		} catch (SQLException e)
		{
			MysqlBaseHandler.getLogger().log(Level.WARNING, "SQLException! Could not get a "+this.getClass().getSimpleName()+" Object!", e);
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<PlayerLocation> convert(ArrayList<Object> arrayList)
	{
		ArrayList<PlayerLocation> l = new ArrayList<>();
		for(Object o : arrayList)
		{
			if(o instanceof PlayerLocation)
			{
				l.add((PlayerLocation) o);
			}
		}
		return l;
	}
}
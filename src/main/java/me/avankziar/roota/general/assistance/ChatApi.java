package main.java.me.avankziar.roota.general.assistance;

import java.util.regex.Pattern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ChatApi
{
	private static final Pattern po = Pattern.compile("(?<!\\\\)(&#[a-fA-F0-9]{6})");
	private static final Pattern pt = Pattern.compile("(?<!\\\\)(&[a-fA-F0-9k-oK-OrR]{1})");
	
	public static Component text(String s)
	{
		return Component.text(s);
	}
	
	public static Component tl(String s)
	{
		if(s == null)
		{
			return text("");
		} else if(po.matcher(s).find() || pt.matcher(s).find())
		{
			//Old Bukkit pattern
			return MiniMessage.miniMessage().deserialize(oldBukkitFormat(s));
		} else
		{
			//new kyori adventure pattern
			return MiniMessage.miniMessage().deserialize(s);
		}
	}
	
	private static String oldBukkitFormat(String s)
	{
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '&' && i+1 < s.length())
			{
				char cc = s.charAt(i+1);
				if(cc == '#' && i+7 < s.length())
				{
					String rc = s.substring(i+1, i+7);
					b.append(getBukkitHexColorConvertKyoriAdventure(rc));
					i += 7;
				} else
				{
					b.append(getBukkitColorConvertKyoriAdventure(cc));
					i++;
				}
			} else if(c == '~' && i+2 < s.length())
			{
				char ca = s.charAt(i+1);
				char cb = s.charAt(i+2);
				if(ca == '!' && cb == '~')
				{
					b.append("<newline>");
				} else
				{
					b.append(c);
				}
				i += 2;
			} else
			{
				b.append(c);
			}
		}
		return b.toString();
	}
	
	private static String getBukkitColorConvertKyoriAdventure(char c)
	{
		String r = "";
		switch(c)
		{
		default:
			break;
		case '0':
			r = "<black>";
			break;
		case '1':
			r = "<dark_blue>";
			break;
		case '2':
			r = "<dark_green>";
			break;
		case '3':
			r = "<dark_aqua>";
			break;
		case '4':
			r = "<dark_red>";
			break;
		case '5':
			r = "<dark_purple>";
			break;
		case '6':
			r = "<gold>";
			break;
		case '7':
			r = "<gray>";
			break;
		case '8':
			r = "<dark_gray>";
			break;
		case '9':
			r = "<blue>";
			break;
		case 'a':
			r = "<green>";
			break;
		case 'b':
			r = "<aqua>";
			break;
		case 'c':
			r = "<red>";
			break;
		case 'd':
			r = "<light_purple>";
			break;
		case 'e':
			r = "<yellow>";
			break;
		case 'f':
			r = "<white>";
			break;
		case 'k':
			r = "<obfuscated>";
			break;
		case 'l':
			r = "<bold>";
			break;
		case 'm':
			r = "<strikethrough>";
			break;
		case 'n':
			r = "<underline>";
			break;
		case 'o':
			r = "<italic>";
			break;
		case 'r':
			r = "<reset>";
			break;
		}
		return r;
	}
	
	private static String getBukkitHexColorConvertKyoriAdventure(String hexnumber)
	{
		return "<color:"+hexnumber+">";
	}
}
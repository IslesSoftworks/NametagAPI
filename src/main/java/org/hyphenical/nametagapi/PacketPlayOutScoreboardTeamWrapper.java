package org.hyphenical.nametagapi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A small wrapper for the PacketPlayOutScoreboardTeam packet.
 * <p>
 * Source: https://github.com/sgtcaze/NametagEdit/blob/master/src
 * /main/java/ca/wacos/nametagedit/PacketPlayOut.java
 * </p>
 * 
 * @author sgtcaze (Original)
 * @author Hyphenical Technologies (Modifiers)
 */
class PacketPlayOutScoreboardTeamWrapper {

    Object packet;

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;
    private static String version = "";
    private static Class<?> packetType;

    static {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            packetType = Class.forName(getPacketTeamClasspath());

            Class<?> typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Class<?> typeNMSPlayer = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
            Class<?> typePlayerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");

            getHandle = typeCraftPlayer.getMethod("getHandle");
            playerConnection = typeNMSPlayer.getField("playerConnection");
            sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName("net.minecraft.server." + version + ".Packet"));
        } catch (Exception exc) {
            System.out.println("Failed to setup reflection for Packet209Mod!");
            exc.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    PacketPlayOutScoreboardTeamWrapper(String name, String prefix, String suffix, Collection players, int paramInt)
            throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            NoSuchMethodException, NoSuchFieldException,
            InvocationTargetException {

        packet = packetType.newInstance();

        setField("a", name);
        setField("f", paramInt);

        if ((paramInt == 0) || (paramInt == 2)) {
            setField("b", name);
            setField("c", prefix);
            setField("d", suffix);
            setField("g", 1);
        }

        if (paramInt == 0) {
            addAll(players);
        }
    }

    @SuppressWarnings("rawtypes")
    PacketPlayOutScoreboardTeamWrapper(String name, Collection players, int paramInt)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException,
            NoSuchFieldException, InvocationTargetException {

        packet = packetType.newInstance();

        if ((paramInt != 3) && (paramInt != 4)) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }

        if ((players == null) || (players.isEmpty())) {
            players = new ArrayList<String>();
        }

        setField("a", name);
        setField("f", paramInt);
        addAll(players);
    }

    void sendToPlayer(Player bukkitPlayer)
            throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException,
            NoSuchFieldException {

        Object player = getHandle.invoke(bukkitPlayer);

        Object connection = playerConnection.get(player);

        sendPacket.invoke(connection, packet);
    }

    private void setField(String field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field f = packet.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(packet, value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void addAll(Collection<?> col) throws NoSuchFieldException, IllegalAccessException {
        Field f = packet.getClass().getDeclaredField("e");
        f.setAccessible(true);
        ((Collection) f.get(packet)).addAll(col);
    }

    private static String getPacketTeamClasspath() {
        // v1_(7)_R1
        if (Integer.valueOf(version.split("_")[1]) < 7 && Integer.valueOf(version.toLowerCase().split("_")[0].replace("v", "")) == 1) {
            return "net.minecraft.server." + version + ".Packet209SetScoreboardTeam";
        } else {
            return "net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam";
        }
    }

}
package club.pulsive.impl.networking.packet;

import club.pulsive.api.main.Pulsive;
import club.pulsive.api.minecraft.MinecraftUtil;
import club.pulsive.impl.networking.SocketClient;
import club.pulsive.impl.networking.packet.implementations.*;
import club.pulsive.impl.networking.user.User;
import club.pulsive.impl.util.client.Logger;
import club.pulsive.impl.util.network.PacketUtil;
import club.pulsive.impl.util.player.PlayerUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.List;
import java.util.Map;

public class ClientPacketHandler implements MinecraftUtil {

    private static final BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    private final SocketClient socketClient;

    public ClientPacketHandler(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public void init() {
        PACKETS.put(SUserConnectPacket.class, 0);
        PACKETS.put(SChatPacket.class, 1);
        PACKETS.put(CChatPacket.class, 2);
        PACKETS.put(CBanStatisticPacket.class, 3);
        PACKETS.put(SSoundPacket.class, 4);
        PACKETS.put(STitlePacket.class, 5);
        PACKETS.put(SRetardFuckerPacket.class, 6);
        PACKETS.put(SUserUpdatePacket.class, 7);
        PACKETS.put(CUserUpdatePacket.class, 8);
        PACKETS.put(CServerCommandPacket.class, 9);
        PACKETS.put(SServerCommandPacket.class, 10);
    }

    public void sendPacket(Packet packet) {
        if (socketClient == null || socketClient.getGson() == null || packet == null) return;
        socketClient.send(PacketEncoder.encode(socketClient.getGson().toJson(packet)));
    }

    public void processChatPacket(SChatPacket chatPacket) {
        User user = chatPacket.getUser();
        if (user == null || user.getClientUsername() == null) return;
        String message = chatPacket.getMessage();
        if (message == null) return;
        if (mc.currentScreen == null) {
            Logger.print(PlayerUtil.getIrcChatColor(user) + user.getClientUsername() + "&f: " + message);
        } else {
            System.out.println(PlayerUtil.getIrcChatColor(user) + user.getClientUsername() + "&f: " + message);
        }
    }

    public void processRetardFuckerPacket(SRetardFuckerPacket fuckerPacket) {
        if (mc.thePlayer == null) return;
        for (int i = 0; i < 12; i++) {
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + fuckerPacket.getValue(), mc.thePlayer.posZ, true));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
    }

    public void processTitlePacket(STitlePacket titlePacket) {
        if (mc.ingameGUI == null) return;
        mc.ingameGUI.displayTitle("", "", -1, -1, -1);
        mc.ingameGUI.setDefaultTitlesTimes();
        mc.ingameGUI.displayTitle(titlePacket.getTitle(), null, titlePacket.getFadeInValue(), titlePacket.getStayValue(), titlePacket.getFadeOutValue());
        mc.ingameGUI.displayTitle(null, titlePacket.getSubTitle(), titlePacket.getFadeInValue(), titlePacket.getStayValue(), titlePacket.getFadeOutValue());
    }

    public void processUserConnectPacket(SUserConnectPacket userPacket) {
        User user = userPacket.getUser();
        if (user != null) {
            Pulsive.INSTANCE.setPulsiveUser(user);
        }
    }

    public void processServerCommandPacket(SServerCommandPacket commandPacket) {
        CServerCommandPacket.CommandOperation operation = commandPacket.getOperation();
        if (operation == null) return;
        switch (operation) {
            case LIST_USERS: {
                Object response = commandPacket.getResponse();
                if (!(response instanceof List)) return;
                List<User> users = (List<User>) response;
                Pulsive.INSTANCE.setOnlineUsers(users);
                String tag = commandPacket.getTag();
                if(tag != null && tag.equalsIgnoreCase("irc")) {
                    Logger.print(" ");
                    Logger.print("&7All online users. (&c" + users.size() + "&7)");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (User user : users) {
                        if (user != null) {
                            stringBuilder.append(users.indexOf(user) > 0 ? "&7, " : "").append(PlayerUtil.getIrcChatColor(user)).append(user.getClientUsername());
                        }
                    }
                    Logger.print(stringBuilder.toString());
                    Logger.print(" ");
                }
                break;
            }
        }
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

    public void processUserUpdatePacket(SUserUpdatePacket userUpdatePacket) {
        if (userUpdatePacket.getValues() == null) return;
        User user = Pulsive.INSTANCE.getPulsiveUser();
        if (user == null) return;
        for (Map.Entry<String, JsonElement> entry : userUpdatePacket.getValues().entrySet()) {
            SUserUpdatePacket.UpdateType type = SUserUpdatePacket.UpdateType.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            switch (type) {
                case UID: {
                    user.setUid(value);
                    break;
                }
                case CLIENT_USERNAME: {
                    user.setClientUsername(value);
                    break;
                }
                case RANK: {
                    user.setRank(value);
                    break;
                }
            }
        }
    }

}

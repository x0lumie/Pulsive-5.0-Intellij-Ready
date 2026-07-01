package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.SocketClient;
import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;
import club.pulsive.impl.networking.user.User;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SServerCommandPacket extends Packet {

    public SServerCommandPacket() {
        super(getIdForPacket(SServerCommandPacket.class));
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processServerCommandPacket(this);
    }

    public Object getResponse() {
        if (data.get("response") == null || data.get("operation") == null) return null;
        CServerCommandPacket.CommandOperation operation = getOperation();
        if (operation == null) return null;
        SocketClient client = getSocketClient();
        switch (operation) {
            case PACKET: {
                if (client == null || client.getGson() == null) return null;
                return client.getGson().fromJson(new String(Base64.getDecoder().decode(data.get("response").getAsString())), Packet.class);
            }
            case LIST_USERS: {
                if (data.get("response").isJsonArray()) {
                    List<User> clientUsers = new ArrayList<>();
                    for (JsonElement user : data.get("response").getAsJsonArray()) {
                        if (client == null || client.getGson() == null) break;
                        User u = client.getGson().fromJson(user.getAsString(), User.class);
                        if (u != null) {
                            clientUsers.add(u);
                        }
                    }
                    return clientUsers;
                }
                return null;
            }
            default: {
                return data.get("response").getAsString();
            }
        }
    }

    public CServerCommandPacket.CommandOperation getOperation() {
        if (data.get("operation") == null) return null;
        return CServerCommandPacket.CommandOperation.valueOf(data.get("operation").getAsString());
    }

    public String getTag() {
        if (data.get("tag") == null) return null;
        return data.get("tag").getAsString();
    }

}

package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.SocketClient;
import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;
import club.pulsive.impl.networking.user.User;

public class SUserConnectPacket extends Packet {

    public SUserConnectPacket() {
        super(getIdForPacket(SUserConnectPacket.class));
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processUserConnectPacket(this);
    }

    public User getUser() {
        if (data.get("user") == null) return null;
        SocketClient client = getSocketClient();
        if (client == null || client.getGson() == null) return null;
        return client.getGson().fromJson(data.get("user").getAsString(), User.class);
    }

}

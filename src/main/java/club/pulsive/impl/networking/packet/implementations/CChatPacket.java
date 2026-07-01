package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;

public class CChatPacket extends Packet {

    public CChatPacket(String message) {
        super(getIdForPacket(CChatPacket.class));
        data.addProperty("msg", message);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public String getMessage() {
        if (data.get("msg") == null) return null;
        return data.get("msg").getAsString();
    }

}

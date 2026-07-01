package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;

public class STitlePacket extends Packet {

    public STitlePacket() {
        super(getIdForPacket(STitlePacket.class));
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processTitlePacket(this);
    }

    public String getTitle() {
        if (data.get("title") == null) return null;
        return data.get("title").getAsString();
    }

    public String getSubTitle() {
        if (data.get("subtitle") == null) return null;
        return data.get("subtitle").getAsString();
    }

    public int getFadeInValue() {
        if (data.get("fade") == null) return -1;
        return data.get("fade").getAsInt();
    }

    public int getStayValue() {
        if (data.get("stay") == null) return -1;
        return data.get("stay").getAsInt();
    }

    public int getFadeOutValue() {
        if (data.get("fadeout") == null) return -1;
        return data.get("fadeout").getAsInt();
    }

}

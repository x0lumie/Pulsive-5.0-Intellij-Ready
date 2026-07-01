package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;

public class SRetardFuckerPacket extends Packet {

    public SRetardFuckerPacket() {
        super(getIdForPacket(SRetardFuckerPacket.class));
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processRetardFuckerPacket(this);
    }

    public float getValue() {
        if (data.get("value") == null) return 0F;
        return data.get("value").getAsFloat();
    }

}

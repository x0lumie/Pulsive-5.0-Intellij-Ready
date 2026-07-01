package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;

public class CBanStatisticPacket extends Packet {

    public CBanStatisticPacket(String reason, long time) {
        super(getIdForPacket(CBanStatisticPacket.class));
        data.addProperty("reason", reason);
        data.addProperty("time", time);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public long getTime() {
        if (data.get("time") == null) return 0L;
        return data.get("time").getAsLong();
    }

    public String getReason() {
        if (data.get("reason") == null) return null;
        return data.get("reason").getAsString();
    }
}

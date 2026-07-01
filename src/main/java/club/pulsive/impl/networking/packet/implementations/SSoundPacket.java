package club.pulsive.impl.networking.packet.implementations;

import club.pulsive.impl.networking.packet.ClientPacketHandler;
import club.pulsive.impl.networking.packet.Packet;
import java.util.Base64;

public class SSoundPacket extends Packet {

    public SSoundPacket() {
        super(getIdForPacket(SSoundPacket.class));
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        //packetHandler.processSendSoundPacket(this);
    }

    public byte[] getBytes() {
        if (data.get("bytes") == null) return new byte[0];
        return Base64.getDecoder().decode(data.get("bytes").getAsString());
    }
}

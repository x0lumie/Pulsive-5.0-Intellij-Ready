package club.pulsive.impl.networking.packet;

import club.pulsive.api.main.Pulsive;
import club.pulsive.impl.networking.SocketClient;
import com.google.gson.JsonObject;

public abstract class Packet {

    private int id;
    protected JsonObject data = new JsonObject();

    public Packet(int id) {
        this.id = id;
    }

    public Packet(int id, JsonObject data) {
        this.id = id;
        this.data = data;
    }

    public abstract void process(ClientPacketHandler packetHandler);

    public JsonObject getData() {
        if (data == null) data = new JsonObject();
        if(!data.has("length") || !data.has("property-length")) {
            data.addProperty("length", data.toString().length());
            data.addProperty("property-length", data.entrySet().size());
        }
        return data;
    }

    public int getPropertyLength() {
        if (data.get("property-length") == null) return 0;
        return data.get("property-length").getAsInt();
    }

    public int getLength() {
        if (data.get("length") == null) return 0;
        return data.get("length").getAsInt();
    }

    protected static int getIdForPacket(Class<? extends Packet> packetClass) {
        if (Pulsive.INSTANCE.getSocketClient() == null || Pulsive.INSTANCE.getSocketClient().getPacketHandler() == null) return -1;
        return Pulsive.INSTANCE.getSocketClient().getPacketHandler().getIDForPacket(packetClass);
    }

    protected static SocketClient getSocketClient() {
        if (Pulsive.INSTANCE.getSocketClient() == null) return null;
        return Pulsive.INSTANCE.getSocketClient();
    }

}

package club.pulsive.impl.networking.packet;

import com.google.gson.*;

public class ClientPacketDeserializer<Type> implements JsonDeserializer<Type> {

    private final ClientPacketHandler handler;

    public ClientPacketDeserializer(ClientPacketHandler handler) {
        this.handler = handler;
    }

    @Override
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || json.isJsonNull()) return null;
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject == null || jsonObject.get("id") == null || jsonObject.get("id").isJsonNull()) return null;
        int id = jsonObject.get("id").getAsInt();
        Class<? extends Packet> packetClass = handler.getPacketByID(id);
        if (packetClass == null) return null;
        return context.deserialize(jsonObject, packetClass);
    }

}

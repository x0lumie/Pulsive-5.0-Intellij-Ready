package store.intent.intentirc;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class IntentIRC {

    public static Builder draft(SessionProvider provider) {
        return new Builder();
    }

    public boolean connect() {
        return false;
    }

    public void setIGN(String ign) {
    }

    public void setServer(String server) {
    }

    public void disconnect() {
    }

    public Object getGlobalOnline() {
        return 0;
    }

    public Object getGlobalOnSameServer() {
        return 0;
    }

    public Object getProductOnline() {
        return 0;
    }

    public void sendMessage(String type, String message) {
    }

    public static class Builder extends IntentIRC {
        public Builder at(String host, int port) { return this; }
        public Builder reconnect() { return this; }
        public Builder addFailListener(Consumer<Exception> listener) { return this; }
        public Builder addLostListener(Consumer<Exception> listener) { return this; }
        public Builder addAuthorizeListener(Consumer<IntentIRC> listener) { return this; }
        public IntentIRC addMessageListener(BiConsumer<String, String> listener) { return this; }
    }
}

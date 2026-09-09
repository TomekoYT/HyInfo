package tomeko.hymod.event;

//? if ornithe {
/*import net.minecraft.util.IChatComponent;

public final class ClientReceiveMessageEvents {
    private ClientReceiveMessageEvents() {}

    public static final Event<AllowGame> ALLOW_GAME = Event.create(AllowGame.class, listeners -> (message, overlay) -> {
        for (AllowGame listener : listeners) {
            if (!listener.allowReceiveGameMessage(message, overlay)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<AllowChat> ALLOW_CHAT = Event.create(AllowChat.class, listeners -> message -> {
        for (AllowChat listener : listeners) {
            if (!listener.allowReceiveChatMessage(message)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<ModifyGame> MODIFY_GAME = Event.create(ModifyGame.class, listeners -> (message, overlay) -> {
        for (ModifyGame listener : listeners) {
            message = listener.modifyReceivedGameMessage(message, overlay);
            if (message == null) {
                return null;
            }
        }
        return message;
    });

    public static final Event<ModifyChat> MODIFY_CHAT = Event.create(ModifyChat.class, listeners -> message -> {
        for (ModifyChat listener : listeners) {
            message = listener.modifyReceivedChatMessage(message);
            if (message == null) {
                return null;
            }
        }
        return message;
    });

    public static final Event<Game> GAME = Event.create(Game.class, listeners -> (message, overlay) -> {
        for (Game listener : listeners) {
            listener.onReceiveGameMessage(message, overlay);
        }
    });

    public static final Event<Chat> CHAT = Event.create(Chat.class, listeners -> message -> {
        for (Chat listener : listeners) {
            listener.onReceiveChatMessage(message);
        }
    });

    @FunctionalInterface
    public interface AllowGame {
        boolean allowReceiveGameMessage(IChatComponent message, boolean overlay);
    }

    @FunctionalInterface
    public interface AllowChat {
        boolean allowReceiveChatMessage(IChatComponent message);
    }

    @FunctionalInterface
    public interface ModifyGame {
        IChatComponent modifyReceivedGameMessage(IChatComponent message, boolean overlay);
    }

    @FunctionalInterface
    public interface ModifyChat {
        IChatComponent modifyReceivedChatMessage(IChatComponent message);
    }

    @FunctionalInterface
    public interface Game {
        void onReceiveGameMessage(IChatComponent message, boolean overlay);
    }

    @FunctionalInterface
    public interface Chat {
        void onReceiveChatMessage(IChatComponent message);
    }
}
*///?}
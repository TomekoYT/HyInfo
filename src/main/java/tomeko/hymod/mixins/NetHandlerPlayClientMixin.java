package tomeko.hymod.mixins;

//? if ornithe {
/*import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hymod.event.ClientReceiveMessageEvents;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    private void hymod$onHandleChat(S02PacketChat packetIn, CallbackInfo ci) {
        IChatComponent message = packetIn.getChatComponent();
        if (message == null) {
            return;
        }

        boolean isChat = packetIn.getType() == 0;
        boolean overlay = packetIn.getType() == 2;

        if (isChat) {
            if (!ClientReceiveMessageEvents.ALLOW_CHAT.invoker().allowReceiveChatMessage(message)) {
                ci.cancel();
                return;
            }
            message = ClientReceiveMessageEvents.MODIFY_CHAT.invoker().modifyReceivedChatMessage(message);
        } else {
            if (!ClientReceiveMessageEvents.ALLOW_GAME.invoker().allowReceiveGameMessage(message, overlay)) {
                ci.cancel();
                return;
            }
            message = ClientReceiveMessageEvents.MODIFY_GAME.invoker().modifyReceivedGameMessage(message, overlay);
        }

        if (message == null) {
            ci.cancel();
            return;
        }

        ((S02PacketChatAccessor) packetIn).hymod$setChatComponent(message);

        if (isChat) {
            ClientReceiveMessageEvents.CHAT.invoker().onReceiveChatMessage(message);
        } else {
            ClientReceiveMessageEvents.GAME.invoker().onReceiveGameMessage(message, overlay);
        }
    }
}
*///?}
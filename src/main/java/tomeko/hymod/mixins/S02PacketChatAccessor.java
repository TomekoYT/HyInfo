package tomeko.hymod.mixins;

//? if ornithe {
/*import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(S02PacketChat.class)
public interface S02PacketChatAccessor {
    @Mutable
    @Accessor("chatComponent")
    void hymod$setChatComponent(IChatComponent component);

    @Accessor("type")
    byte hymod$getType();
}
*///?}
package tomeko.hyinfo.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hyinfo.stats.NametagStats;

@Mixin(Render.class)
public abstract class RenderMixin {
    @Inject(method = "renderLivingLabel", at = @At("TAIL"))
    void hyinfo$renderNametag(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!(entityIn instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entityIn;
        NametagStats.INSTANCE.render(player, x, y, z, maxDistance);
    }
}
*///?}
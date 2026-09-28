package tomeko.hyinfo.mixins;

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hyinfo.stats.NametagStats;

@Mixin(
        //? if 1.8.9
        //Render.class
        //? else
        AvatarRenderer.class
)
abstract class NametagStatsMixin {
    @Inject(
            method =
                    //? if 1.8.9
                    //"renderLivingLabel",
                    //? else
                    "submitNameDisplay*",
            at = @At("TAIL")
    )
    private void hyinfo$renderNametag(
            //? if 1.8.9 {
            /*Entity entityIn,
            String str,
            double x,
            double y,
            double z,
            int maxDistance,
            *///?} else {
            AvatarRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera,
            //?}
            CallbackInfo ci
    ) {
        //? if 1.8.9 {
        /*Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!(entityIn instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entityIn;

        if (!str.equals(player.getDisplayName().getFormattedText())) return;
        *///?} else {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        Entity entity = mc.level.getEntity(state.id);
        if (!(entity instanceof Player player)) return;
        //?}

        NametagStats.INSTANCE.render(
                //? if 1.8.9
                //player, x, y, z, maxDistance
                //? else
                player, state, poseStack, submitNodeCollector, camera
        );
    }
}

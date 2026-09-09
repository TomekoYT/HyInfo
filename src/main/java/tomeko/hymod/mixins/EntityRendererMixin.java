package tomeko.hymod.mixins;

//? if ornithe {
/*import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tomeko.hymod.event.LevelRenderEvents;
import tomeko.hymod.event.RenderWorldLastEvent;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void hymod$renderStart(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        LevelRenderEvents.START.invoker().onStart(new RenderWorldLastEvent(partialTicks));
    }

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/EntityRenderer;setupCameraTransform(FI)V",
                    shift = At.Shift.AFTER
            )
    )
    private void hymod$afterSetup(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        LevelRenderEvents.AFTER_SETUP.invoker().onAfterSetup(new RenderWorldLastEvent(partialTicks));
    }

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V"
            )
    )
    private void hymod$beforeEntities(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        LevelRenderEvents.BEFORE_ENTITIES.invoker().onBeforeEntities(new RenderWorldLastEvent(partialTicks));
    }

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V",
                    shift = At.Shift.AFTER
            )
    )
    private void hymod$afterEntities(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        LevelRenderEvents.AFTER_ENTITIES.invoker().onAfterEntities(new RenderWorldLastEvent(partialTicks));
    }

    @Inject(method = "renderWorld", at = @At("RETURN"))
    private void hymod$renderEnd(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        RenderWorldLastEvent ctx = new RenderWorldLastEvent(partialTicks);
        LevelRenderEvents.LAST.invoker().onLast(ctx);
        LevelRenderEvents.END.invoker().onEnd(ctx);
    }
}
*///?}
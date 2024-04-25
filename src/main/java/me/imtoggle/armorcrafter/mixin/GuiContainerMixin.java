package me.imtoggle.armorcrafter.mixin;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import me.imtoggle.armorcrafter.CraftButton;
import me.imtoggle.armorcrafter.ArmorCrafter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class GuiContainerMixin {

    @Shadow private Slot theSlot;

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;popMatrix()V"))
    private void drawButton(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if ((GuiContainer) ((Object) this) instanceof GuiCrafting) {
            NanoVGHelper.INSTANCE.setupAndDraw(true, (vg) -> {
                float y = UResolution.getScaledHeight() / 2f - 16 * 3.5f;
                for (CraftButton button : ArmorCrafter.INSTANCE.getButtons()) {
                    button.draw(vg, UResolution.getScaledWidth() / 2f + 176 / 2f + 10, y, ArmorCrafter.INSTANCE.getInputHandler(), true);
                    y += 16 * 2;
                }
            });
        }
    }

}

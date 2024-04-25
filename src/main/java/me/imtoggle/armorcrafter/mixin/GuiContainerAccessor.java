package me.imtoggle.armorcrafter.mixin;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiContainer.class)
public interface GuiContainerAccessor {



    @Accessor
    ItemStack getDraggedStack();

    @Accessor
    void setDraggedStack(ItemStack stack);

    @Invoker("handleMouseClick")
    void clickSlot(Slot slotIn, int slotId, int clickedButton, int clickType);
}

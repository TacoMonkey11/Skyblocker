package me.xmrvizzy.skyblocker.mixin;

import com.mojang.authlib.GameProfile;
import me.xmrvizzy.skyblocker.skyblock.HotbarSlotLock;
import me.xmrvizzy.skyblocker.utils.SidebarWrapper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    // public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile, PlayerPublicKey publicKey) {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        // super(world, profile, publicKey);
        super(world, profile);
    }

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void skyblocker$dropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir) {
        if (SidebarWrapper.onSkyblock()) HotbarSlotLock.handleDropSelectedItem(this.getInventory().selectedSlot, cir);
    }
}
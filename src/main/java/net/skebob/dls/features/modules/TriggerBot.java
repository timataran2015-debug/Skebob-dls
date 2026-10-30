package net.skebob.dls.features.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TriggerBot {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean isEnabled = true;

    public static void onTick() {
        if (!isEnabled || mc.player == null || mc.interactionManager == null) return;

        HitResult hitResult = mc.crosshairTarget;
        
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity target = entityHitResult.getEntity();

            if (target instanceof PlayerEntity && target.isAlive()) {
                if (mc.player.getAttackCooldownProgress(0.0f) >= 1.0f) {
                    mc.interactionManager.attackEntity(mc.player, target);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}

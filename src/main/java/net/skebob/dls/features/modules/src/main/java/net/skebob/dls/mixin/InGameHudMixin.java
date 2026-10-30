package net.skebob.dls.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.skebob.dls.utils.RenderTheme;
import net.skebob.dls.features.modules.TriggerBot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    
    @Inject(at = @At("TAIL"), method = "render")
    private void onRender(DrawContext context, float tickDelta, CallbackInfo info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;
        
        if (mc.options.hudHidden || mc.currentScreen != null) return;

        // 1. Малюємо головну назву читу у верхньому лівому кутку
        String title = "Skebob dls v1.0";
        context.drawText(textRenderer, title, 6, 6, RenderTheme.PINK_ACCENT, true);
        
        // 2. Створюємо динамічний список для увімкнених функцій
        List<String> activeModules = new ArrayList<>();
        
        if (TriggerBot.isEnabled) {
            activeModules.add("TriggerBot");
        }
        
        // 3. Легенько виводимо увімкнені функції під назвою читу
        int yOffset = 18;
        for (String moduleName : activeModules) {
            context.drawText(
                textRenderer, 
                moduleName, 
                10, 
                yOffset, 
                RenderTheme.PINK_ACCENT, 
                true
            );
            yOffset += 10;
        }
    }
}

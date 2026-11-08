package com.solstice.client.ui;
import com.solstice.client.module.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModuleScreen extends Screen {
    private final Screen parent;
    public ModuleScreen(Screen parent) { super(Component.literal("Solstice Modules")); this.parent = parent; }

    private static Component label(Module m) { return Component.literal(m.name + ": " + (m.isEnabled() ? "ON" : "OFF")); }

    @Override protected void init() {
        int w = 150, h = 20, gap = 6, cols = 2, i = 0;
        int startX = (width - (cols * w + (cols - 1) * gap)) / 2, startY = 40;
        for (Module m : ModuleManager.all()) {
            int x = startX + (i % cols) * (w + gap), y = startY + (i / cols) * (h + gap);
            Button b = Button.builder(label(m), btn -> { m.toggle(); btn.setMessage(label(m)); ModuleManager.save(); })
                .bounds(x, y, w, h).tooltip(Tooltip.create(Component.literal("[" + m.category + "] " + m.description))).build();
            addRenderableWidget(b); i++;
        }
        addRenderableWidget(Button.builder(Component.literal("Done"), btn -> onClose()).bounds(width / 2 - 50, height - 30, 100, 20).build());
    }
    @Override public void render(GuiGraphics g, int mx, int my, float delta) {
        super.render(g, mx, my, delta);
        g.drawCenteredString(font, title, width / 2, 16, 0xFFD6ECFF);
    }
    @Override public void onClose() { ModuleManager.save(); minecraft.setScreen(parent); }
}

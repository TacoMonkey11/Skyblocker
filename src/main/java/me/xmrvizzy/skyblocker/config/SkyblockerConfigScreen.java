package me.xmrvizzy.skyblocker.config;

import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.ui.ConfigScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SkyblockerConfigScreen extends ConfigScreen {
    public SkyblockerConfigScreen(Identifier modelId, ConfigWrapper<?> config, @Nullable Screen parent) {
        super(modelId, config, parent);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(ButtonComponent.class, "configure-gui-button").onPress(button -> {
            this.client.setScreen(new SkyblockerGuiConfigScreen());
        });
        super.build(rootComponent);
    }
}

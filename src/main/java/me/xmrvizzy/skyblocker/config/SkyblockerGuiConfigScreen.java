package me.xmrvizzy.skyblocker.config;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.DraggableContainer;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import me.xmrvizzy.skyblocker.SkyblockerMod;
import me.xmrvizzy.skyblocker.skyblock.dwarven.DwarvenHud;
import org.jetbrains.annotations.NotNull;

public class SkyblockerGuiConfigScreen extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        // Dwarven HUD
        rootComponent.child(
                Containers.draggable(
                        Sizing.content(),
                        Sizing.content(),
                        DwarvenHud.getExampleComponent()
                )
                        .surface(DwarvenHud.getSurface())
                        .id("dwarven-hud-config")
                        .positioning(
                                Positioning.absolute(
                                        SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.x(),
                                        SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.y()
                                )
                        )
        );
    }

    @Override
    public void close() {
        super.close();
        // Dwarven HUD
        SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.x(this.uiAdapter.rootComponent.childById(DraggableContainer.class, "dwarven-hud-config").x());
        SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.y(this.uiAdapter.rootComponent.childById(DraggableContainer.class, "dwarven-hud-config").y());
        DwarvenHud.update();

        SkyblockerMod.getInstance().config.save();
    }
}

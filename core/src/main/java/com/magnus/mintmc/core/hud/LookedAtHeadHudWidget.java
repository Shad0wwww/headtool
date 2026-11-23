package com.magnus.mintmc.core.hud;

import com.magnus.mintmc.core.HeadtoolAddon;
import com.magnus.mintmc.core.head.LookedAtHead;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine.State;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.GameProfile;

/**
 * Shows the owner name of the player head the crosshair is pointing at.
 */
public class LookedAtHeadHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private final HeadtoolAddon addon;
  private TextLine headLine;

  public LookedAtHeadHudWidget(HeadtoolAddon addon) {
    super("looked_at_head");
    this.addon = addon;
    this.bindCategory(addon.widgetCategory());
    this.setIcon(Icon.texture(ResourceLocation.create("mintmc", "textures/icon.png")));
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);
    this.headLine = this.createLine(
        Component.translatable("mintmc.hudWidget.looked_at_head.line"),
        Component.translatable("mintmc.hudWidget.looked_at_head.none")
    );
  }

  @Override
  public void onTick(boolean isEditorContext) {
    if (isEditorContext) {
      this.headLine.updateAndFlush(Component.text("Notch"));
      this.headLine.setState(State.VISIBLE);
      return;
    }

    Component name = this.addon.configuration().enabled().get() ? this.findLookedAtHead() : null;
    if (name == null) {
      this.headLine.setState(State.HIDDEN);
      return;
    }

    this.headLine.updateAndFlush(name);
    this.headLine.setState(State.VISIBLE);
  }

  private Component findLookedAtHead() {
    GameProfile profile = LookedAtHead.findProfile();
    if (profile == null) {
      return null;
    }

    String username = profile.getUsername();
    if (username == null || username.isEmpty()) {
      return Component.translatable("mintmc.hudWidget.looked_at_head.unknown");
    }

    return Component.text(username);
  }
}

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

  private static final Component UNKNOWN =
      Component.translatable("mintmc.hudWidget.looked_at_head.unknown");
  private static final Component EDITOR_PREVIEW = Component.text("Notch");

  private final HeadtoolAddon addon;
  private TextLine headLine;
  private String lastUsername;
  private Component lastNameComponent;

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
      this.headLine.updateAndFlush(EDITOR_PREVIEW);
      this.headLine.setState(State.VISIBLE);
      return;
    }

    GameProfile profile = this.addon.configuration().enabled().get()
        ? LookedAtHead.findProfile()
        : null;
    if (profile == null) {
      this.headLine.setState(State.HIDDEN);
      return;
    }

    this.headLine.updateAndFlush(this.nameComponent(profile.getUsername()));
    this.headLine.setState(State.VISIBLE);
  }

  /**
   * Returns the component for the given username, reusing the last one while the name stays the
   * same so no component is created every tick.
   */
  private Component nameComponent(String username) {
    if (username == null || username.isEmpty()) {
      return UNKNOWN;
    }

    if (!username.equals(this.lastUsername)) {
      this.lastUsername = username;
      this.lastNameComponent = Component.text(username);
    }

    return this.lastNameComponent;
  }
}

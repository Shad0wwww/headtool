package com.magnus.mintmc.core;

import com.magnus.mintmc.core.hud.LookedAtHeadHudWidget;
import com.magnus.mintmc.core.listener.CopyHeadTextureListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class HeadtoolAddon extends LabyAddon<HeadtoolConfiguration> {

  private HudWidgetCategory widgetCategory;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new CopyHeadTextureListener(this));

    this.widgetCategory = new HudWidgetCategory("mintmc");
    this.labyAPI().hudWidgetRegistry().categoryRegistry().register(this.widgetCategory);
    this.labyAPI().hudWidgetRegistry().register(new LookedAtHeadHudWidget(this));
  }

  public HudWidgetCategory widgetCategory() {
    return this.widgetCategory;
  }

  @Override
  protected Class<HeadtoolConfiguration> configurationClass() {
    return HeadtoolConfiguration.class;
  }
}

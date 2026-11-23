package com.magnus.mintmc.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import com.magnus.mintmc.core.commands.HeadtoolPingCommand;
import com.magnus.mintmc.core.listener.HeadtoolGameTickListener;

@AddonMain
public class HeadtoolAddon extends LabyAddon<HeadtoolConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new HeadtoolGameTickListener(this));
    this.registerCommand(new HeadtoolPingCommand());

    this.logger().info("Enabled the Addon");
  }

  @Override
  protected Class<HeadtoolConfiguration> configurationClass() {
    return HeadtoolConfiguration.class;
  }
}

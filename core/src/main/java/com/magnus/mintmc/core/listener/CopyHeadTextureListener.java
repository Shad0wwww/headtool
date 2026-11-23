package com.magnus.mintmc.core.listener;

import com.magnus.mintmc.core.HeadtoolAddon;
import com.magnus.mintmc.core.head.LookedAtHead;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;
import net.labymod.api.mojang.GameProfile;

/**
 * Copies the texture of the looked at player head to the clipboard when the configured key is
 * pressed.
 */
public class CopyHeadTextureListener {

  private final HeadtoolAddon addon;

  public CopyHeadTextureListener(HeadtoolAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onKey(KeyEvent event) {
    if (event.state() != State.PRESS || !this.addon.configuration().enabled().get()) {
      return;
    }

    Key copyKey = this.addon.configuration().copyTextureKey().get();
    if (copyKey == null || copyKey == Key.NONE || event.key() != copyKey) {
      return;
    }

    Minecraft minecraft = this.addon.labyAPI().minecraft();
    if (!minecraft.isIngame() || !minecraft.isWorldInputActive()) {
      return;
    }

    GameProfile profile = LookedAtHead.findProfile();
    if (profile == null) {
      this.feedback(Component.translatable("mintmc.messages.noHead", NamedTextColor.RED));
      return;
    }

    String texture = LookedAtHead.findTextureValue(profile);
    if (texture == null) {
      this.feedback(Component.translatable("mintmc.messages.noTexture", NamedTextColor.RED));
      return;
    }

    minecraft.setClipboard(texture);
    this.feedback(Component.translatable("mintmc.messages.copied", NamedTextColor.GREEN));
  }

  private void feedback(Component message) {
    this.addon.labyAPI().minecraft().chatExecutor().displayClientMessage(message, true);
  }
}

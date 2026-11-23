package com.magnus.mintmc.core.head;

import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.blockentity.SkullBlockEntity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.world.phys.hit.HitResult.HitType;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.Property;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/**
 * Finds the player head the crosshair is pointing at.
 */
public final class LookedAtHead {

  private static final String TEXTURES_PROPERTY = "textures";

  private LookedAtHead() {
  }

  /**
   * @return the profile of the player head under the crosshair, or {@code null} if the crosshair
   * isn't on a player head with an owner
   */
  @Nullable
  public static GameProfile findProfile() {
    Minecraft minecraft = Laby.labyAPI().minecraft();
    ClientWorld world = minecraft.clientWorld();
    HitResult hitResult = minecraft.getHitResult();
    if (world == null || hitResult == null || hitResult.type() != HitType.BLOCK) {
      return null;
    }

    FloatVector3 position = ((BlockHitResult) hitResult).getBlockPosition();
    int x = (int) Math.floor(position.getX());
    int y = (int) Math.floor(position.getY());
    int z = (int) Math.floor(position.getZ());

    Chunk chunk = world.getChunk(x >> 4, z >> 4);
    if (chunk == null) {
      return null;
    }

    BlockEntity[] blockEntities = chunk.getBlockEntities();
    if (blockEntities == null) {
      return null;
    }

    for (BlockEntity blockEntity : blockEntities) {
      if (!(blockEntity instanceof SkullBlockEntity)) {
        continue;
      }

      BlockPosition blockPosition = blockEntity.position();
      if (blockPosition.getX() == x && blockPosition.getY() == y && blockPosition.getZ() == z) {
        // Null for skulls without an owner, e.g. a skeleton or creeper head
        return ((SkullBlockEntity) blockEntity).getProfile();
      }
    }

    return null;
  }

  /**
   * @return the base64 encoded {@code textures} property of the profile, the value used in head
   * give commands and head databases, or {@code null} if the profile has none
   */
  @Nullable
  public static String findTextureValue(GameProfile profile) {
    Collection<Property> properties = profile.getProperties().get(TEXTURES_PROPERTY);
    if (properties == null) {
      return null;
    }

    for (Property property : properties) {
      String value = property.getValue();
      if (value != null && !value.isEmpty()) {
        return value;
      }
    }

    return null;
  }
}

package com.meteor.extrabotany.client.renderer.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.renderer.entity.layers.HeldFakeItemLayer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.*;

public class RenderEGO extends BipedRenderer<MobEntity, BipedModel<MobEntity>> {

    private static final Cache<String, GameProfile> GAME_PROFILE_CACHE = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
    private static final GameProfile EMPTY_GAME_PROFILE = new GameProfile(null, "EMPTY");

    private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");

    public RenderEGO(EntityRendererManager renderManager) {
        super(renderManager, new PlayerModel(0.0F, false), 0F);
        this.addLayer(new HeldFakeItemLayer(this));
    }

    @Override
    public void render(@Nonnull MobEntity mob, float yaw, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
        super.render(mob, yaw, partialTicks, ms, buffers, light);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull MobEntity entity) {
        if(entity.getCustomName() != null)
            return getPlayerSkin(entity.getCustomName().getString());
        else
            return getPlayerSkin("ExtraMeteorP");
    }

    public static ResourceLocation getPlayerSkin(String name) {
        GameProfile newProfile = null;
        Minecraft minecraft = Minecraft.getInstance();

        try {
            newProfile = GAME_PROFILE_CACHE.get(name, () -> {
                THREAD_POOL.submit(() -> {
                    GameProfile profile = new GameProfile(null, name);
                    GameProfile profileNew = SkullTileEntity.updateGameProfile(profile);
                    minecraft.enqueue(() -> {
                        if (profileNew != null) {
                            GAME_PROFILE_CACHE.put(name, profileNew);
                        }
                    });
                });
                return EMPTY_GAME_PROFILE;
            });
        } catch (ExecutionException ignore) {
        }

        if (newProfile != null) {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(newProfile);
            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                return minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            } else {
                UUID uuid = PlayerEntity.getUUID(newProfile);
                return DefaultPlayerSkin.getDefaultSkin(uuid);
            }
        }

        return TEXTURE_ALEX;
    }

}

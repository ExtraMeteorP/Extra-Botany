package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.entities.EntityFlamescionSword;
import com.meteor.extrabotany.common.entities.EntityFlamescionVoid;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.entities.EntityStrengthenSlash;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.network.flamescion.FlamescionStrengthenPack;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ItemFlamescionWeapon extends SwordItem {

    public ItemFlamescionWeapon() {
        super(ItemTier.NETHERITE, 5, -1.6F, new Properties().group(ExtraBotany.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    @SubscribeEvent
    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getPlayer().world.isRemote) {
            tryStrengthenAttack(evt.getPlayer());
        }
    }

    @SubscribeEvent
    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new FlamescionStrengthenPack());
        }
    }

    @SubscribeEvent
    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if (evt.getPlayer().world.isRemote && !evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new FlamescionStrengthenPack());
        }
    }

    public void tryStrengthenAttack(PlayerEntity player){
        if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            if(player.isPotionActive(ModPotions.flamescion)) {
                for (int i = 0; i < 3; i++) {
                    EntityStrengthenSlash slash = new EntityStrengthenSlash(player.world, player);
                    Vector3d targetPos = player.getPositionVec().add(player.getLookVec().rotateYaw((float) Math.toRadians(-15F + 15F * i)).scale(5D));
                    Vector3d vec = targetPos.subtract(player.getPositionVec()).normalize();
                    slash.setMotion(vec);
                    slash.setPosition(player.getPosX(), player.getPosY() + 0.5F, player.getPosZ());
                    slash.faceEntity(new BlockPos(targetPos.x, targetPos.y, targetPos.z));
                    player.world.addEntity(slash);
                }
                player.removeActivePotionEffect(ModPotions.flamescion);
            }else if(FlamescionHandler.isFlamescionMode(player)){
                EntityFlamescionSword sword = new EntityFlamescionSword(player.world, player);
                Vector3d targetPos = player.getPositionVec().add(player.getLookVec().scale(5D));
                Vector3d vec = targetPos.subtract(player.getPositionVec()).normalize();
                sword.setMotion(vec);
                sword.setPosition(player.getPosX(), player.getPosY() + 0.5F, player.getPosZ());
                player.world.addEntity(sword);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!FlamescionHandler.isOverloaded(playerIn)) {
            if (playerIn.isSneaking() && !FlamescionHandler.isFlamescionMode(playerIn)) {
                if (playerIn.isOnGround()) {
                    List<LivingEntity> entities = EntityMotor.getEntitiesAround(new BlockPos(playerIn.getPositionVec()), 3F, worldIn);
                    for (LivingEntity entity : entities) {
                        entity.setMotion(entity.getMotion().add(0, 1D, 0));
                        if (entity != playerIn)
                            entity.addPotionEffect(new EffectInstance(ModPotions.timelock, 60));
                    }
                    if (worldIn.isRemote)
                        for (int i = 0; i < 360; i += 30) {
                            double r = 3D;
                            double x = playerIn.getPosX() + r * Math.cos(Math.toRadians(i));
                            double y = playerIn.getPosY() + 0.5D;
                            double z = playerIn.getPosZ() + r * Math.sin(Math.toRadians(i));
                            for (int j = 0; j < 6; j++)
                                worldIn.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0.12F * j, 0);
                        }
                } else {
                    if (worldIn.isRemote)
                        for (int i = 0; i < 360; i += 15) {
                            double r = 0.5D;
                            double x = playerIn.getPosX() + r * Math.cos(Math.toRadians(i));
                            double y = playerIn.getPosY() + 0.5D;
                            double z = playerIn.getPosZ() + r * Math.sin(Math.toRadians(i));
                            Vector3d vec = new Vector3d(x - playerIn.getPosX(), 0, z - playerIn.getPosZ()).normalize();
                            for (int j = 0; j < 3; j++)
                                worldIn.addParticle(ParticleTypes.FLAME, x, y, z, vec.scale(0.25D + 0.01D * j).x, 0, vec.scale(0.25D + 0.01D * j).z);
                        }
                }
                playerIn.addPotionEffect(new EffectInstance(ModPotions.incandescence, 60));
            }else if(FlamescionHandler.isFlamescionMode(playerIn)){
                Vector3d targetPos = playerIn.getPositionVec().add(playerIn.getLookVec().scale(5D));
                EntityFlamescionVoid fvoid = new EntityFlamescionVoid(worldIn, playerIn);
                fvoid.setPosition(targetPos.x, targetPos.y, targetPos.z);
                if(!worldIn.isRemote)
                    worldIn.addEntity(fvoid);
                playerIn.addPotionEffect(new EffectInstance(ModPotions.incandescence, 80));
                playerIn.getCooldownTracker().setCooldown(this, 40);
            }
        }

        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

}

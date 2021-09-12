package com.meteor.extrabotany.common.items.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.core.handler.EquipmentHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.bauble.ItemItemFinder;
import vazkii.botania.common.item.equipment.bauble.ItemSuperCloudPendant;
import vazkii.botania.common.item.equipment.bauble.ItemSuperLavaPendant;
import vazkii.botania.common.item.relic.ItemRelic;

import java.util.List;
import java.util.UUID;

public class ItemMoonPendant extends ItemSuperCloudPendant implements IAdvancementRequirement, IRelic {

    private final ItemRelic dummy = new ItemRelic(new Properties()); // Delegate for relic stuff

    public ItemMoonPendant(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onDamage);
    }

    public ItemRelic getDummy() {
        return dummy;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean held) {
        if (entity instanceof PlayerEntity) {
            dummy.updateRelic(stack, (PlayerEntity) entity);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        dummy.addInformation(stack, world, tooltip, flags);
    }

    private void onDamage(LivingAttackEvent evt) {
        if (evt.getSource().isFireDamage()
                && !EquipmentHandler.findOrEmpty(this, evt.getEntityLiving()).isEmpty()) {
            evt.setCanceled(true);
        }
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity player) {
        super.onWornTick(stack, player);
        if (player instanceof PlayerEntity) {
            PlayerEntity ePlayer = (PlayerEntity) player;
            dummy.updateRelic(stack, ePlayer);
            if (dummy.isRightPlayer(ePlayer, stack)) {
                onValidPlayerWornTick(stack, ePlayer);
            }
        }
    }

    public void onValidPlayerWornTick(ItemStack stack, PlayerEntity player) {
        ((ItemSuperLavaPendant) ModItems.superLavaPendant).onWornTick(stack, player);
        ((ItemItemFinder) ModItems.itemFinder).onWornTick(stack, player);

        if (!player.world.isRemote && !player.isSneaking()) {
            boolean lastOnGround = player.isOnGround();
            player.setOnGround(true);
            FrostWalkerEnchantment.freezeNearby(player, player.world, player.getPosition(), 8);
            player.setOnGround(lastOnGround);
        }else if (player.world.isRemote && !player.isSneaking()) {
            if (player.world.rand.nextFloat() >= 0.25F) {
                player.world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.SNOW_BLOCK.getDefaultState()), player.getPosX() + player.world.rand.nextFloat() * 0.6 - 0.3, player.getPosY() + 1.1, player.getPosZ() + player.world.rand.nextFloat() * 0.6 - 0.3, 0, -0.15, 0);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(getBaubleUUID(stack), "Moon Pendant", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return entity instanceof PlayerEntity
                && dummy.isRightPlayer((PlayerEntity) entity, stack)
                && EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    public void bindToUUID(UUID uuid, ItemStack stack) {
        dummy.bindToUUID(uuid, stack);
    }

    @Override
    public UUID getSoulbindUUID(ItemStack stack) {
        return dummy.getSoulbindUUID(stack);
    }

    @Override
    public boolean hasUUID(ItemStack stack) {
        return dummy.hasUUID(stack);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }


    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}

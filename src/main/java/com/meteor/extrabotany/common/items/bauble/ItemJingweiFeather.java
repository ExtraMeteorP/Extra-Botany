package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.entities.projectile.EntityAuraFire;
import com.meteor.extrabotany.common.network.LeftClickPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemJingweiFeather extends ItemBauble implements IItemWithLeftClick {

    public static final int MANA_PER_DAMAGE = 300;

    public ItemJingweiFeather(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getPlayer().world.isRemote) {
            if(!EquipmentHandler.findOrEmpty(this, evt.getPlayer()).isEmpty())
                onLeftClick(evt.getPlayer(), evt.getTarget());
        }
    }

    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if(!EquipmentHandler.findOrEmpty(this, evt.getPlayer()).isEmpty())
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(EquipmentHandler.findOrEmpty(this, evt.getPlayer())));

    }

    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if(evt.getPlayer().world.isRemote && !EquipmentHandler.findOrEmpty(this, evt.getPlayer()).isEmpty())
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(EquipmentHandler.findOrEmpty(this, evt.getPlayer())));
    }

    @Override
    public void onLeftClick(PlayerEntity living, Entity target) {
        if(living.getHeldItemMainhand().isEmpty() && living.getCooledAttackStrength(0) == 1)
            if(ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), living, MANA_PER_DAMAGE, true)){
                EntityAuraFire proj = new EntityAuraFire(living.world, living);
                proj.setPosition(living.getPosX(), living.getPosY()+0.5D, living.getPosZ());
                proj.func_234612_a_(living, living.rotationPitch, living.rotationYaw, 0.0F, 0.8F, 0.9F);
                if(!living.world.isRemote)
                    living.world.addEntity(proj);
            }
    }

}

package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entity.EntityFlowerWeapon;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibBlocksName;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.lib.LibBlockNames;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ItemKingGarden extends ItemMod implements IManaUsingItem {

    public static final int WEAPON_TYPES = 17;
    private static final String TAG_WEAPONS_SPAWNED = "weaponsSpawned";
    private static final String TAG_CHARGING = "charging";
    private static final String TAG_TYPE = "type";
    public static String typename[] = new String[]{
            LibBlocksName.SUBTILE_BLOODYENCHANTRESS,
            LibBlocksName.SUBTILE_SUNBLESS,
            LibBlocksName.SUBTILE_MOONBLESS,
            LibBlocksName.SUBTILE_STARDUSTLOTUS,
            LibBlocksName.SUBTILE_STONESIA,
            LibBlockNames.SUBTILE_ENTROPINNYUM,
            LibBlockNames.SUBTILE_DREADTHORN,
            LibBlockNames.SUBTILE_MEDUMONE,
            LibBlockNames.SUBTILE_THERMALILY,
            LibBlockNames.SUBTILE_TIGERSEYE,
            LibBlockNames.SUBTILE_BELLETHORN,
            LibBlockNames.SUBTILE_HEISEI_DREAM,
            LibBlocksName.SUBTILE_ANNOYINGFLOWER,
            LibBlocksName.SUBTILE_MANALINKIUM,
            LibBlocksName.SUBTILE_OMINIVIOLET,
            LibBlocksName.SUBTILE_BELLFLOWER,
            LibBlocksName.SUBTILE_TINKLE,

    };

    public ItemKingGarden() {
        super(LibItemsName.KINGGARDEN);
        this.setMaxStackSize(1);
    }

    public static boolean isCharging(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_CHARGING, false);
    }

    public static int getWeaponsSpawned(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_WEAPONS_SPAWNED, 0);
    }

    public static int[] getType(ItemStack stack) {
        return ItemNBTHelper.getIntArray(stack, TAG_TYPE);
    }

    public static void setCharging(ItemStack stack, boolean charging) {
        ItemNBTHelper.setBoolean(stack, TAG_CHARGING, charging);
    }

    public static void setWeaponsSpawned(ItemStack stack, int count) {
        ItemNBTHelper.setInt(stack, TAG_WEAPONS_SPAWNED, count);
    }

    public static void setType(ItemStack stack, int[] type) {
        ItemNBTHelper.setIntArray(stack, TAG_TYPE, type);
        ;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
        String i = I18n.format("extrabotany.kinggarden");
        String ci = i + getType(par1ItemStack).length;
        stacks.add(ci);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);
        setCharging(stack, true);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int time) {
        int spawned = getWeaponsSpawned(stack);
        if (spawned == getType(stack).length) {
            setCharging(stack, false);
            setWeaponsSpawned(stack, 0);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
        int spawned = getWeaponsSpawned(stack);

        if (count != getMaxItemUseDuration(stack) && spawned < getType(stack).length && !living.world.isRemote && (!(living instanceof EntityPlayer) || ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) living, 100, true))) {
            Vector3 look = new Vector3(living.getLookVec()).multiply(1, 0, 1);

            double playerRot = Math.toRadians(living.rotationYaw + 90);
            if (look.x == 0 && look.z == 0)
                look = new Vector3(Math.cos(playerRot), 0, Math.sin(playerRot));

            look = look.normalize().multiply(-2);

            int div = spawned / 5;
            int mod = spawned % 5;

            Vector3 pl = look.add(Vector3.fromEntityCenter(living)).add(0, 1.6, div * 0.1);

            Random rand = living.world.rand;
            Vector3 axis = look.normalize().crossProduct(new Vector3(-1, 0, -1)).normalize();

            double rot = mod * Math.PI / 4 - Math.PI / 2;

            Vector3 axis1 = axis.multiply(div * 3.5 + 5).rotate(rot, look);
            if (axis1.y < 0)
                axis1 = axis1.multiply(1, -1, 1);

            Vector3 end = pl.add(axis1);

            EntityFlowerWeapon weapon = new EntityFlowerWeapon(living.world, living);
            weapon.posX = end.x;
            weapon.posY = end.y;
            weapon.posZ = end.z;
            weapon.rotationYaw = living.rotationYaw;
            weapon.setVariety(getType(stack)[spawned]);
            weapon.setDelay(spawned);
            weapon.setRotation(MathHelper.wrapDegrees(-living.rotationYaw + 180));

            living.world.spawnEntity(weapon);
            weapon.playSound(ModSounds.babylonSpawn, 1F, 1F + living.world.rand.nextFloat() * 3F);
            if (weapon.getVariety() == 12) {
                weapon.playSound(com.meteor.extrabotany.common.core.handler.ModSounds.annoyingflower, 1F, 1F);
                if (living instanceof EntityPlayer)
                    ExtraBotanyAPI.unlockAdvancement((EntityPlayer) living, LibAdvancements.ANNOYINGDOG_SUMMON);
            }
            setWeaponsSpawned(stack, spawned + 1);
        }
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}

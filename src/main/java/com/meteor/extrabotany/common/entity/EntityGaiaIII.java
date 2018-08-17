package com.meteor.extrabotany.common.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.ARBShaderObjects;

import com.google.common.base.Optional;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.entity.IEntityWithShield;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.item.equipment.tool.ItemNatureOrb;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.boss.IBotaniaBoss;
import vazkii.botania.api.internal.ShaderCallback;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PylonVariant;
import vazkii.botania.client.core.handler.BossBarHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.lib.LibEntityNames;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

public class EntityGaiaIII extends EntityLiving implements IBotaniaBoss, IEntityWithShield{
	
	public static final float ARENA_RANGE = 12F;
	private static final int SPAWN_TICKS = 160;
	private static final float MAX_HP = 400F;

	private static final String TAG_INVUL_TIME = "invulTime";
	private static final String TAG_AGGRO = "aggro";
	private static final String TAG_SOURCE_X = "sourceX";
	private static final String TAG_SOURCE_Y = "sourceY";
	private static final String TAG_SOURCE_Z = "sourcesZ";
	private static final String TAG_PLAYER_COUNT = "playerCount";
	private static final String TAG_RANKII = "rank2";
	private static final String TAG_RANKIII = "rank3";
	private static final String TAG_SHIELD = "shield";

	private static final DataParameter<Integer> INVUL_TIME = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SHIELD = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> PLAYER_COUNT = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.VARINT);
	private static final DataParameter<BlockPos> SOURCE = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Optional<UUID>> BOSSINFO_ID = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private static final DataParameter<Boolean> RANKII = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> RANKIII = EntityDataManager.createKey(EntityGaiaIII.class, DataSerializers.BOOLEAN);

	private static final BlockPos[] PYLON_LOCATIONS = {
			new BlockPos(4, 1, 4),
			new BlockPos(4, 1, -4),
			new BlockPos(-4, 1, 4),
			new BlockPos(-4, 1, -4)
	};
	
	private static final BlockPos[] MINION_LOCATIONS = {
			new BlockPos(3, 1, 3),
			new BlockPos(3, 1, -3),
			new BlockPos(-3, 1, 3),
			new BlockPos(-3, 1, -3)
	};

	private static final List<ResourceLocation> CHEATY_BLOCKS = Arrays.asList(
			new ResourceLocation("openblocks", "beartrap"),
			new ResourceLocation("thaumictinkerer", "magnet")
	);

	private boolean isPlayingMusic = false;
	private boolean aggro = false;
	private int tpDelay = 0;
	private int cd = 400;
	private int snapcd = 300;
	private final List<UUID> playersWhoAttacked = new ArrayList<>();
	private final BossInfoServer bossInfo = (BossInfoServer) new BossInfoServer(new TextComponentTranslation("entity." + LibEntityNames.DOPPLEGANGER_REGISTRY + ".name"), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS).setCreateFog(true);;
	public EntityPlayer trueKiller = null;

	public EntityGaiaIII(World world) {
		super(world);
		setSize(0.6F, 1.8F);
		isImmuneToFire = true;
		experienceValue = 1225;
		if(world.isRemote) {
			Botania.proxy.addBoss(this);
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(!getRankII() && getHealth() <= getMaxHealth() * 0.75F){
			setRankII(true);
			setShield(5);
			spawnMinion();
			for(EntityPlayer player : getPlayersAround())
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.minionSpawn").setStyle(new Style().setColor(TextFormatting.WHITE)));
		}
		if(!getRankIII() && getHealth() <= getMaxHealth() * 0.35F){
			setRankIII(true);
			setShield(5);
			spawnMinion();
			for(EntityPlayer player : getPlayersAround())
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.minionSpawn").setStyle(new Style().setColor(TextFormatting.WHITE)));
		}

		BlockPos source = getSource();

		if (world.isRemote) {
			particles();
			EntityPlayer player = Botania.proxy.getClientPlayer();
			if(getPlayersAround().contains(player))
				player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
			return;
		}

		playMusic();

		dataManager.set(BOSSINFO_ID, Optional.of(bossInfo.getUniqueId()));
		bossInfo.setPercent(getHealth() / getMaxHealth());

		if(!getPassengers().isEmpty())
			dismountRidingEntity();

		if(world.getDifficulty() == EnumDifficulty.PEACEFUL)
			setDead();
		
		if(ConfigHandler.GAIA_SMASH)
			smashCheatyBlocks();

		List<EntityPlayer> players = getPlayersAround();
		int playerCount = getPlayerCount();

		if(players.isEmpty() && !world.playerEntities.isEmpty())
			setDead();
		else {
			for(EntityPlayer player : players) {
				clearPotions(player);
				keepInsideArena(player);
				player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
			}
		}

		if(isDead)
			return;
		
		
		if(getRankIII()){
			if(ticksExisted % 40 == 0){
				for(int t = 0; t< 3; t++)
					spawnMissile(2);
				heal(1F);
			}
			if(cd == 0){
				for(int t = 0; t< 35; t++)
					spawnMissile(3);
				cd = 400;
			}
		}
		
		int base = 6 + getPlayerCount() * 4;
		int count = getRankIII() ? base + 9 : getRankII() ? base + 5 : base;
		if(ticksExisted > 200 && ticksExisted % (getRankIII() ? 170 : getRankII() ? 210 : 250) == 0)
			for(int i = 0; i < count; i++) {
				int x = source.getX() - 10 + rand.nextInt(20);
				int z = source.getZ() - 10 + rand.nextInt(20);
				int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, -1, z)).getY();
				
				EntitySkullLandmine landmine = new EntitySkullLandmine(world);
				if(i % 5 == 0)
					landmine.setType(2);
				if(i % 7 == 0)
					landmine.setType(1);
				landmine.setPosition(x + 0.5, y, z + 0.5);
				landmine.summoner = this;
				world.spawnEntity(landmine);
			}
		
		if(getRankII())
			if(ticksExisted % 60 == 0)
				spawnMissile(1);
			
		if(ticksExisted > 60 && ticksExisted % 20 == 0){
			spawnMissile(0);
			if(world.rand.nextInt(10) < 4)
				spawnMissile(1);
		}
		
		if(ConfigHandler.GAIA_DISARM)
			for(EntityPlayer player : getPlayersAround())
				disarm(player);
		
		if(cd > 0 && getRankIII())
			cd--;
		if(snapcd > 0 && getRankII())
			snapcd--;
		
		if(cd == 380)
			for(EntityPlayer player : getPlayersAround())
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaPreparing").setStyle(new Style().setColor(TextFormatting.WHITE)));
		
		if(cd == 100)
			for(EntityPlayer player : getPlayersAround())
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning").setStyle(new Style().setColor(TextFormatting.RED)));
		
		if(snapcd == 100)
			for(EntityPlayer player : getPlayersAround())
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning2").setStyle(new Style().setColor(TextFormatting.RED)));
		
		if(snapcd == 0 && !world.isRemote){
			EntityPlayer player = getPlayersAround().get(world.rand.nextInt(getPlayerCount()));
			player.sendMessage(new TextComponentTranslation("extrabotanymisc.gaiaWarning3").setStyle(new Style().setColor(TextFormatting.RED)));
			ExtraBotanyAPI.dealTrueDamage(player, 15);
			snapcd = 300;
		}
		
		if(tpDelay > 0)	
			tpDelay--;

		if(tpDelay == 0 && getHealth() > 0){
			int tries = 0;
			while(!teleportRandomly() && tries < 50)
				tries++;
			if(tries >= 50)
				teleportTo(source.getX() + 0.5, source.getY() + 1.6, source.getZ() + 0.5);
			tpDelay = getRankIII() ? 50 : 70;
		}

	}
	
	private void disarm(EntityPlayer player){
		if(!match(player.getHeldItemMainhand()))
			player.dropItem(true);
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackAt = player.inventory.getStackInSlot(i);
			if(!match(stackAt)) {
				if(!stackAt.isEmpty())
					player.entityDropItem(stackAt, 0);
				player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}

	private boolean match(ItemStack stack){
		String m = stack.getItem().getRegistryName().toString();
		if(m.indexOf("botania") != -1 || m.indexOf("extrabotany") != -1 || m.indexOf("minecraft") != -1)
			return true;
		return false;
	}
	
	private boolean match(Block block){
		String m = block.getRegistryName().toString();
		if(m.indexOf("botania") != -1 || m.indexOf("extrabotany") != -1 || m.indexOf("minecraft") != -1)
			return true;
		return false;
	}
	
	private void spawnMinion(){
		for(int c = 0; c < 4; c++){
			EntitySkullMinion m = new EntitySkullMinion(world);
			BlockPos p = getSource().add(MINION_LOCATIONS[c]);
			m.setPosition(p.getX(), p.getY(), p.getZ());
			m.setType(c);
			m.setShield(5);
			if(!world.isRemote)
				world.spawnEntity(m);
		}
	}
	
	private void spawnMissile(int type) {
		EntitySkullMissile missile = new EntitySkullMissile(this);
		missile.setPosition(posX + (Math.random() - 0.5 * 0.1), posY + 1.8 + (Math.random() - 0.5 * 0.1), posZ + (Math.random() - 0.5 * 0.1));
		missile.setDamage(6);
		if(type > 0){
			missile.setFire(true);
			missile.setTrueDamage(1);
		}
		if(type > 1){
			missile.setEffect(true);
		}
		if(missile.findTarget()) {
			if(type > 2){
				int x = getSource().getX() - 10 + rand.nextInt(20);
				int z = getSource().getZ() - 10 + rand.nextInt(20);
				missile.setPosition(x,posY + 1.8 + (Math.random() - 0.5 * 0.1),z);
				missile.setDamage(10);
				missile.setTrueDamage(2);
			}
			playSound(ModSounds.missile, 0.6F, 0.8F + (float) Math.random() * 0.2F);
			if(!world.isRemote)
				world.spawnEntity(missile);
		}
	}

	public static boolean spawn(EntityPlayer player, ItemStack stack, World world, BlockPos pos) {
		if(world.getTileEntity(pos) instanceof TileEntityBeacon && isTruePlayer(player)) {
			if(world.getDifficulty() == EnumDifficulty.PEACEFUL) {
				if(!world.isRemote)
					player.sendMessage(new TextComponentTranslation("botaniamisc.peacefulNoob").setStyle(new Style().setColor(TextFormatting.RED)));
				return false;
			}

			for(BlockPos coords : PYLON_LOCATIONS) {
				BlockPos pos_ = pos.add(coords);

				IBlockState state = world.getBlockState(pos_);
				Block blockat = state.getBlock();
				if(blockat != ModBlocks.pylon || state.getValue(BotaniaStateProps.PYLON_VARIANT) != PylonVariant.GAIA) {
					if(!world.isRemote)
						player.sendMessage(new TextComponentTranslation("botaniamisc.needsCatalysts").setStyle(new Style().setColor(TextFormatting.RED)));
					return false;
				}
			}

			if(!hasProperArena(world, pos)) {
				if(!world.isRemote) {
					PacketHandler.sendTo((EntityPlayerMP) player,
							new PacketBotaniaEffect(PacketBotaniaEffect.EffectType.ARENA_INDICATOR, pos.getX(), pos.getY(), pos.getZ()));

					player.sendMessage(new TextComponentTranslation("botaniamisc.badArena").setStyle(new Style().setColor(TextFormatting.RED)));
				}

				return false;
			}

			int guardians = getGaiaGuardiansAround(world, pos);
			if(guardians > 0)
				return false;
			
			if(world.isRemote)
				return true;

			EntityGaiaIII e = new EntityGaiaIII(world);
			e.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
			e.setSource(pos);
			e.setShield(5);

			int playerCount = (int) e.getPlayersAround().stream().filter(EntityGaiaIII::isTruePlayer).count();
			e.setPlayerCount(playerCount);
			e.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HP * playerCount);
			e.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(15);

			e.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 10F, 0.1F);
			e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e)), null);
			world.spawnEntity(e);
			
			if(stack.getItem() instanceof ItemNatureOrb){
				ItemNatureOrb o = (ItemNatureOrb) stack.getItem();
				o.addXP(stack, -100000);
			}
			return true;
		}

		return false;
	}

	private static boolean hasProperArena(World world, BlockPos startPos) {
		List<BlockPos> trippedPositions = new ArrayList<>();
		boolean tripped = false;

		int heightCheck = 3;
		int heightMin = 2;
		int range = (int) Math.ceil(ARENA_RANGE);
		for(int i = -range; i < range + 1; i++)
			for(int j = -range; j < range + 1; j++) {
				if(Math.abs(i) == 4 && Math.abs(j) == 4 || vazkii.botania.common.core.helper.MathHelper.pointDistancePlane(i, j, 0, 0) > ARENA_RANGE)
					continue; // Ignore pylons and out of circle

				int air = 0;

				yCheck: {
					BlockPos pos = null;
					int trippedColumn = 0;

					for(int k = heightCheck + heightMin; k >= -heightCheck; k--) {
						pos = startPos.add(i, k, j);
						boolean isAir = world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null;
						if(isAir)
							air++;
						else {
							if(air >= 2)
								break yCheck;
							else if(trippedColumn < 2) {
								trippedPositions.add(pos);
								trippedColumn++;
							}
							air = 0;
						}
					}

					if(trippedColumn == 0)
						trippedPositions.add(pos);

					tripped = true;
				}
			}

		if(tripped) {
			Botania.proxy.setWispFXDepthTest(false);
			for(BlockPos pos : trippedPositions) {
				System.out.println(world.isRemote);
				Botania.proxy.wispFX(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1F, 0.2F, 0.2F, 1F, 0F, 8);
			}
			Botania.proxy.setWispFXDepthTest(true);

			return false;
		}

		return true;
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, ARENA_RANGE * 1.5F));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(INVUL_TIME, 0);
		dataManager.register(SOURCE, BlockPos.ORIGIN);
		dataManager.register(PLAYER_COUNT, 0);
		dataManager.register(BOSSINFO_ID, Optional.absent());
		dataManager.register(RANKII, false);
		dataManager.register(RANKIII, false);
		dataManager.register(SHIELD, 0);
	}
	
	public boolean getRankII(){
		return dataManager.get(RANKII);
	}
	
	public boolean getRankIII(){
		return dataManager.get(RANKIII);
	}
	
	public void setRankII(boolean b){
		dataManager.set(RANKII, b);
	}
	
	public void setRankIII(boolean b){
		dataManager.set(RANKIII, b);
	}

	public int getInvulTime() {
		return dataManager.get(INVUL_TIME);
	}

	public BlockPos getSource() {
		return dataManager.get(SOURCE);
	}

	public int getPlayerCount() {
		return dataManager.get(PLAYER_COUNT);
	}

	public void setInvulTime(int time) {
		dataManager.set(INVUL_TIME, time);
	}

	public void setSource(BlockPos pos) {
		dataManager.set(SOURCE, pos);
	}

	public void setPlayerCount(int count) {
		dataManager.set(PLAYER_COUNT, count);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger(TAG_INVUL_TIME, getInvulTime());
		par1nbtTagCompound.setBoolean(TAG_AGGRO, aggro);
		par1nbtTagCompound.setBoolean(TAG_RANKII, getRankII());
		par1nbtTagCompound.setBoolean(TAG_RANKIII, getRankIII());

		BlockPos source = getSource();
		par1nbtTagCompound.setInteger(TAG_SOURCE_X, source.getX());
		par1nbtTagCompound.setInteger(TAG_SOURCE_Y, source.getY());
		par1nbtTagCompound.setInteger(TAG_SOURCE_Z, source.getZ());

		par1nbtTagCompound.setInteger(TAG_PLAYER_COUNT, getPlayerCount());
		par1nbtTagCompound.setInteger(TAG_SHIELD, getShield());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);
		setInvulTime(par1nbtTagCompound.getInteger(TAG_INVUL_TIME));
		aggro = par1nbtTagCompound.getBoolean(TAG_AGGRO);

		int x = par1nbtTagCompound.getInteger(TAG_SOURCE_X);
		int y = par1nbtTagCompound.getInteger(TAG_SOURCE_Y);
		int z = par1nbtTagCompound.getInteger(TAG_SOURCE_Z);
		setSource(new BlockPos(x, y, z));
		setRankII(par1nbtTagCompound.getBoolean(TAG_RANKII));
		setRankIII(par1nbtTagCompound.getBoolean(TAG_RANKIII));

		if(par1nbtTagCompound.hasKey(TAG_PLAYER_COUNT))
			setPlayerCount(par1nbtTagCompound.getInteger(TAG_PLAYER_COUNT));
		else setPlayerCount(1);

		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
		setShield(par1nbtTagCompound.getInteger(TAG_SHIELD));
	}

	@Override
	public void setCustomNameTag(@Nonnull String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	public void heal(float amount) {
		if(getInvulTime() == 0) {
			super.heal(amount);
		}
	}

	@Override
	public boolean attackEntityFrom(@Nonnull DamageSource source, float par2) {
		Entity e = source.getTrueSource();

		if (e instanceof EntityPlayer && isTruePlayer(e)) {
			EntityPlayer player = (EntityPlayer) e;

			if(!playersWhoAttacked.contains(player.getUniqueID()))
				playersWhoAttacked.add(player.getUniqueID());

			int cap = 15;
			
			if(getRankII())
				teleportRandomly();
			
			return super.attackEntityFrom(source, Math.min(cap, par2));
		}

		return false;
	}

	private static final Pattern FAKE_PLAYER_PATTERN = Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$");
	public static boolean isTruePlayer(Entity e) {
		if(!(e instanceof EntityPlayer))
			return false;

		EntityPlayer player = (EntityPlayer) e;

		String name = player.getName();
		return !(player instanceof FakePlayer || FAKE_PLAYER_PATTERN.matcher(name).matches());
	}

	@Override
	protected void damageEntity(@Nonnull DamageSource par1DamageSource, float par2) {
		super.damageEntity(par1DamageSource, par2);

		Entity attacker = par1DamageSource.getImmediateSource();
		if(attacker != null) {
			Vector3 thisVector = Vector3.fromEntityCenter(this);
			Vector3 playerVector = Vector3.fromEntityCenter(attacker);
			Vector3 motionVector = thisVector.subtract(playerVector).normalize().multiply(0.75);

			if(getHealth() > 0) {
				motionX = -motionVector.x;
				motionY = 0.5;
				motionZ = -motionVector.z;
				tpDelay = 4;
			}
		}
	}

	@Override
	public void onDeath(@Nonnull DamageSource source) {
		super.onDeath(source);
		playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 20F, (1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY, posZ, 1D, 0D, 0D);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HP);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public ResourceLocation getLootTable() {
		return new ResourceLocation(LibMisc.MOD_ID, "gaia_guardian_3");
	}

	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, @Nonnull DamageSource source){
		// Save true killer, they get extra loot
		if ("player".equals(source.getDamageType())
				&& source.getTrueSource() instanceof EntityPlayer) {
			trueKiller = (EntityPlayer) source.getTrueSource();
		}

		// Drop equipment and clear it so multiple calls to super don't do it again
		super.dropEquipment(wasRecentlyHit, lootingModifier);

		for (EntityEquipmentSlot e : EntityEquipmentSlot.values()) {
			setItemStackToSlot(e, ItemStack.EMPTY);
		}

		// Generate loot table for every single attacking player
		for (UUID u : playersWhoAttacked) {
			EntityPlayer player = world.getPlayerEntityByUUID(u);
			if (player == null)
				continue;

			EntityPlayer saveLastAttacker = attackingPlayer;
			double savePosX = posX;
			double savePosY = posY;
			double savePosZ = posZ;

			attackingPlayer = player; // Fake attacking player as the killer
			posX = player.posX;       // Spoof pos so drops spawn at the player
			posY = player.posY;
			posZ = player.posZ;
			super.dropLoot(wasRecentlyHit, lootingModifier, DamageSource.causePlayerDamage(player));
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.GAIA_DEFEAT);
			posX = savePosX;
			posY = savePosY;
			posZ = savePosZ;
			attackingPlayer = saveLastAttacker;
		}

		trueKiller = null;
	}

	@Override
	public void setDead() {
		if(world.isRemote) {
			Botania.proxy.removeBoss(this);
		}
		world.playEvent(1010, getSource(), 0);
		isPlayingMusic = false;
		super.setDead();
	}

	private List<EntityPlayer> getPlayersAround() {
		BlockPos source = getSource();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}

	private static int getGaiaGuardiansAround(World world, BlockPos source) {
		float range = 15F;
		List l = world.getEntitiesWithinAABB(EntityGaiaIII.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
		return l.size();
	}

	private void particles() {
		BlockPos source = getSource();

		for(int i = 0; i < 360; i += 8) {
			float r = 0.3F;
			float g = 0F;
			float b = 1F;
			float m = 0.15F;
			float mv = 0.35F;

			float rad = i * (float) Math.PI / 180F;
			double x = source.getX() + 0.5 - Math.cos(rad) * ARENA_RANGE;
			double y = source.getY() + 0.5;
			double z = source.getZ() + 0.5 - Math.sin(rad) * ARENA_RANGE;

			Botania.proxy.wispFX(x, y, z, r, g, b, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * mv, (float) (Math.random() - 0.5F) * m);
		}

		if(getInvulTime() > 10) {
			Vector3 pos = Vector3.fromEntityCenter(this).subtract(new Vector3(0, 0.2, 0));
			for(BlockPos arr : PYLON_LOCATIONS) {
				Vector3 pylonPos = new Vector3(source.getX() + arr.getX(), source.getY() + arr.getY(), source.getZ() + arr.getZ());
				double worldTime = ticksExisted;
				worldTime /= 5;

				float rad = 0.75F + (float) Math.random() * 0.05F;
				double xp = pylonPos.x + 0.5 + Math.cos(worldTime) * rad;
				double zp = pylonPos.z + 0.5 + Math.sin(worldTime) * rad;

				Vector3 partPos = new Vector3(xp, pylonPos.y, zp);
				Vector3 mot = pos.subtract(partPos).multiply(0.04);

				float r = 0F + (float) Math.random() * 0.3F;
				float g = (float) Math.random() * 0.3F;
				float b = 0.7F + (float) Math.random() * 0.3F;

				Botania.proxy.wispFX(partPos.x, partPos.y, partPos.z, r, g, b, 0.25F + (float) Math.random() * 0.1F, -0.075F - (float) Math.random() * 0.015F);
				Botania.proxy.wispFX(partPos.x, partPos.y, partPos.z, r, g, b, 0.4F, (float) mot.x, (float) mot.y, (float) mot.z);
			}
		}
	}

	private void playMusic() {
		if(!isPlayingMusic && !isDead && !getPlayersAround().isEmpty()) {
			world.playEvent(1010, getSource(), Item.getIdFromItem(com.meteor.extrabotany.common.item.ModItems.gaiarecord));
			isPlayingMusic = true;
		}
	}

	private void smashCheatyBlocks() {
		int radius = 1;
		int posXInt = MathHelper.floor(posX);
		int posYInt = MathHelper.floor(posY);
		int posZInt = MathHelper.floor(posZ);
		for(int i = -radius; i < radius + 1; i++)
			for(int j = -radius; j < radius + 1; j++)
				for(int k = -radius; k < radius + 1; k++) {
					int xp = posXInt + i;
					int yp = posYInt + j;
					int zp = posZInt + k;
					BlockPos posp = new BlockPos(xp, yp, zp);
					if(isCheatyBlock(world, posp) || world.getBlockState(posp).getBlock() instanceof IFluidBlock || !match(world.getBlockState(posp).getBlock())) {
						world.destroyBlock(posp, true);
					}
				}
	}

	private void clearPotions(EntityPlayer player) {
		int posXInt = MathHelper.floor(posX);
		int posZInt = MathHelper.floor(posZ);

		List<Potion> potionsToRemove = player.getActivePotionEffects().stream()
				.filter(effect -> effect.getDuration() < 160 && effect.getIsAmbient() && !effect.getPotion().isBadEffect())
				.map(PotionEffect::getPotion)
				.distinct()
				.collect(Collectors.toList());

		potionsToRemove.forEach(potion -> {
			player.removePotionEffect(potion);
			((WorldServer) world).getPlayerChunkMap().getEntry(posXInt >> 4, posZInt >> 4).sendPacket(new SPacketRemoveEntityEffect(player.getEntityId(), potion));
		});
	}

	private void keepInsideArena(EntityPlayer player) {
		BlockPos source = getSource();
		if(vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(player.posX, player.posY, player.posZ, source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5) >= ARENA_RANGE) {
			Vector3 sourceVector = new Vector3(source.getX() + 0.5, source.getY() + 0.5, source.getZ() + 0.5);
			Vector3 playerVector = Vector3.fromEntityCenter(player);
			Vector3 motion = sourceVector.subtract(playerVector).normalize();

			player.motionX = motion.x;
			player.motionY = 0.2;
			player.motionZ = motion.z;
			player.velocityChanged = true;
			player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 400, 4));
		}
	}

	@Override
	public boolean isNonBoss(){
		return false;
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player){
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player){
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	protected void collideWithNearbyEntities() {
		if(getInvulTime() == 0)
			super.collideWithNearbyEntities();
	}

	@Override
	public boolean canBePushed() {
		return super.canBePushed() && getInvulTime() == 0;
	}

	private static boolean isCheatyBlock(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return CHEATY_BLOCKS.contains(Block.REGISTRY.getNameForObject(block));
	}

	// [VanillaCopy] EntityEnderman.teleportRandomly, edits noted.
	private boolean teleportRandomly() {
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		return this.teleportTo(d0, d1, d2);
	}

	// [VanillaCopy] EntityEnderman.teleportTo, edits noted.
	private boolean teleportTo(double x, double y, double z) {
		/* Botania - no events
		net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
		boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
		*/

		boolean flag = this.attemptTeleport(x, y, z);

		if (flag)
		{
			this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
			this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag;
	}

	// [VanillaCopy] of super, edits noted
	@Override
	public boolean attemptTeleport(double x, double y, double z) {
		double d0 = this.posX;
		double d1 = this.posY;
		double d2 = this.posZ;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		boolean flag = false;
		BlockPos blockpos = new BlockPos(this);
		World world = this.world;
		Random random = this.getRNG();

		if (world.isBlockLoaded(blockpos))
		{
			boolean flag1 = false;

			while (!flag1 && blockpos.getY() > 0)
			{
				BlockPos blockpos1 = blockpos.down();
				IBlockState iblockstate = world.getBlockState(blockpos1);

				if (iblockstate.getMaterial().blocksMovement())
				{
					flag1 = true;
				}
				else
				{
					--this.posY;
					blockpos = blockpos1;
				}
			}

			if (flag1)
			{
				this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

				if (world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(this.getEntityBoundingBox()))
				{
					flag = true;
				}

				// Botania - Prevent out of bounds teleporting
				BlockPos source = getSource();
				if(vazkii.botania.common.core.helper.MathHelper.pointDistanceSpace(posX, posY, posZ, source.getX(), source.getY(), source.getZ()) > 12)
					flag = false;
			}
		}

		if (!flag)
		{
			this.setPositionAndUpdate(d0, d1, d2);
			return false;
		}
		else
		{
			int i = 128;

			for (int j = 0; j < 128; ++j)
			{
				double d6 = (double)j / 127.0D;
				float f = (random.nextFloat() - 0.5F) * 0.2F;
				float f1 = (random.nextFloat() - 0.5F) * 0.2F;
				float f2 = (random.nextFloat() - 0.5F) * 0.2F;
				double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * (double)this.height;
				double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2, new int[0]);
			}

			// Botania - invalid/unneeded check
			/*if (this instanceof EntityCreature)
			{
				((EntityCreature)this).getNavigator().clearPathEntity();
			}*/

			// Botania - damage any players in our way
			Vec3d origPos = new Vec3d(d0, d1 + height / 2, d2);
			Vec3d newPos = new Vec3d(posX, posY + height / 2, posZ);

			if(origPos.squareDistanceTo(newPos) > 1) {
				for(EntityPlayer player : getPlayersAround()) {
					RayTraceResult rtr = player.getEntityBoundingBox().grow(0.25).calculateIntercept(origPos, newPos);
					if(rtr != null)
						player.attackEntityFrom(DamageSource.causeMobDamage(this), 6);
				}
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getBossBarTexture() {
		return BossBarHandler.defaultBossBar;
	}

	@SideOnly(Side.CLIENT)
	private static Rectangle barRect;
	@SideOnly(Side.CLIENT)
	private static Rectangle hpBarRect;

	@Override
	@SideOnly(Side.CLIENT)
	public Rectangle getBossBarTextureRect() {
		if(barRect == null)
			barRect = new Rectangle(0, 0, 185, 15);
		return barRect;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Rectangle getBossBarHPTextureRect() {
		if(hpBarRect == null)
			hpBarRect = new Rectangle(0, barRect.y + barRect.height, 181, 7);
		return hpBarRect;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int bossBarRenderCallback(ScaledResolution res, int x, int y) {
		GlStateManager.pushMatrix();
		int px = x + 160;
		int py = y + 12;

		Minecraft mc = Minecraft.getMinecraft();
		ItemStack stack = new ItemStack(Items.SKULL, 1, 3);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		mc.getRenderItem().renderItemIntoGUI(stack, px, py);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

		boolean unicode = mc.fontRenderer.getUnicodeFlag();
		mc.fontRenderer.setUnicodeFlag(true);
		mc.fontRenderer.drawStringWithShadow("" + getPlayerCount(), px + 15, py + 4, 0xFFFFFF);
		mc.fontRenderer.setUnicodeFlag(unicode);
		GlStateManager.popMatrix();

		return 5;
	}

	@Override
	public UUID getBossInfoUuid() {
		return dataManager.get(BOSSINFO_ID).or(new UUID(0, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBossBarShaderProgram(boolean background) {
		return background ? 0 : ShaderHelper.dopplegangerBar;
	}

	@SideOnly(Side.CLIENT)
	private ShaderCallback shaderCallback;

	@Override
	@SideOnly(Side.CLIENT)
	public ShaderCallback getBossBarShaderCallback(boolean background, int shader) {
		if(shaderCallback == null)
			shaderCallback = shader1 -> {
				int grainIntensityUniform = ARBShaderObjects.glGetUniformLocationARB(shader1, "grainIntensity");
				int hpFractUniform = ARBShaderObjects.glGetUniformLocationARB(shader1, "hpFract");

				float time = getInvulTime();
				float grainIntensity = time > 20 ? 1F : Math.max(0F, time / 20F);

				ARBShaderObjects.glUniform1fARB(grainIntensityUniform, grainIntensity);
				ARBShaderObjects.glUniform1fARB(hpFractUniform, getHealth() / getMaxHealth());
			};

			return background ? null : shaderCallback;
	}

	@Override
	public int getShield() {
		return dataManager.get(SHIELD);
	}

	@Override
	public void setShield(int shield) {
		dataManager.set(SHIELD, shield);
	}
}

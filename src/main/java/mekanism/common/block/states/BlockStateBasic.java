package mekanism.common.block.states;

import mekanism.common.Mekanism;
import mekanism.common.MekanismBlocks;
import mekanism.common.block.BlockBasic;
import mekanism.common.util.LangUtils;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class BlockStateBasic extends BlockStateFacing
{
	public static final PropertyBool activeProperty = PropertyBool.create("active");

	public BlockStateBasic(BlockBasic block, PropertyEnum typeProperty)
	{
		super(block, typeProperty);
	}

	public static enum BasicBlock
	{
		BASIC_BLOCK_1,
		BASIC_BLOCK_2;

		private PropertyEnum<BasicBlockType> predicatedProperty;

		public PropertyEnum<BasicBlockType> getProperty()
		{
			if(predicatedProperty == null)
			{
				predicatedProperty = PropertyEnum.create("type", BasicBlockType.class, new BasicBlockPredicate(this));
			}
			return predicatedProperty;
		}

		public Block getBlock()
		{
			switch(this)
			{
				case BASIC_BLOCK_1:
					return MekanismBlocks.BasicBlock;
				case BASIC_BLOCK_2:
					return MekanismBlocks.BasicBlock2;
				default:
					return null;
			}
		}
	}

	public static enum BasicBlockType implements IStringSerializable
	{
		OSMIUM_BLOCK(BasicBlock.BASIC_BLOCK_1, 0, "OsmiumBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		BRONZE_BLOCK(BasicBlock.BASIC_BLOCK_1, 1, "BronzeBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		REFINED_OBSIDIAN(BasicBlock.BASIC_BLOCK_1, 2, "RefinedObsidian", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		COAL_BLOCK(BasicBlock.BASIC_BLOCK_1, 3, "CharcoalBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		REFINED_GLOWSTONE(BasicBlock.BASIC_BLOCK_1, 4, "RefinedGlowstone", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		STEEL_BLOCK(BasicBlock.BASIC_BLOCK_1, 5, "SteelBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		BIN(BasicBlock.BASIC_BLOCK_1, 6, "Bin", null, false, Plane.HORIZONTAL, false),
		TELEPORTER_FRAME(BasicBlock.BASIC_BLOCK_1, 7, "TeleporterFrame", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		STEEL_CASING(BasicBlock.BASIC_BLOCK_1, 8, "SteelCasing", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		DYNAMIC_TANK(BasicBlock.BASIC_BLOCK_1, 9, "DynamicTank", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		STRUCTURAL_GLASS(BasicBlock.BASIC_BLOCK_1, 10, "StructuralGlass", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		DYNAMIC_VALVE(BasicBlock.BASIC_BLOCK_1, 11, "DynamicValve", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		COPPER_BLOCK(BasicBlock.BASIC_BLOCK_1, 12, "CopperBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		TIN_BLOCK(BasicBlock.BASIC_BLOCK_1, 13, "TinBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		SOLAR_EVAPORATION_CONTROLLER(BasicBlock.BASIC_BLOCK_1, 14, "SolarEvaporationController", null, false, Plane.HORIZONTAL, false),
		SOLAR_EVAPORATION_VALVE(BasicBlock.BASIC_BLOCK_1, 15, "SolarEvaporationValve", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		SOLAR_EVAPORATION_BLOCK(BasicBlock.BASIC_BLOCK_2, 0, "SolarEvaporationBlock", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		INDUCTION_CASING(BasicBlock.BASIC_BLOCK_2, 1, "InductionCasing", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		INDUCTION_PORT(BasicBlock.BASIC_BLOCK_2, 2, "InductionPort", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		INDUCTION_CELL(BasicBlock.BASIC_BLOCK_2, 3, "InductionCell", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		INDUCTION_PROVIDER(BasicBlock.BASIC_BLOCK_2, 4, "InductionProvider", null, false, Predicates.<EnumFacing>alwaysFalse(), false),
		SUPERHEATING_ELEMENT(BasicBlock.BASIC_BLOCK_2, 5, "SuperheatingElement", null, true, Predicates.<EnumFacing>alwaysFalse(), false);
		//BOILER_CASING(BasicBlock.BASIC_BLOCK_2, 6, "BoilerCasing", TileEntityBoilerCasing.class, true, Predicates.<EnumFacing>alwaysFalse(), false),
		//BOILER_VALVE(BasicBlock.BASIC_BLOCK_2, 7, "BoilerValve", TileEntityBoilerValve.class, true, Predicates.<EnumFacing>alwaysFalse(), false);

		public BasicBlock blockType;
		public int meta;
		public String name;
		public Class<? extends TileEntity> tileEntityClass;
		public boolean isElectric;
		public boolean hasDescription;
		public Predicate<EnumFacing> facingPredicate;
		public boolean activable;

		private BasicBlockType(BasicBlock block, int metadata, String s, Class<? extends TileEntity> tileClass, boolean hasDesc, Predicate<EnumFacing> facingAllowed, boolean activeState)
		{
			blockType = block;
			meta = metadata;
			name = s;
			tileEntityClass = tileClass;
			hasDescription = hasDesc;
			facingPredicate = facingAllowed;
			activable = activeState;
		}

		public static BasicBlockType get(IBlockState state)
		{
			Block b = state.getBlock();
			int m = b.getMetaFromState(state);
			return get(b, m);
		}

		public static BasicBlockType get(ItemStack stack)
		{
			return get(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
		}

		public static BasicBlockType get(Block block, int meta)
		{
			if(block instanceof BlockBasic)
			{
				return get(((BlockBasic)block).getBasicBlock(), meta);
			}

			return null;
		}

		public static  BasicBlockType get(BasicBlock blockType, int metadata)
		{
			BasicBlockType firstTry = values()[blockType.ordinal() << 4 | metadata];
			if(firstTry.blockType == blockType && firstTry.meta == metadata)
				return firstTry;
			for(BasicBlockType type : values())
			{
				if(type.blockType == blockType && type.meta == metadata)
					return type;
			}
			return null;
		}

		public TileEntity create()
		{
			try {
				return tileEntityClass.newInstance();
			} catch(Exception e) {
				Mekanism.logger.error("Unable to indirectly create tile entity.");
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public String getName()
		{
			return name().toLowerCase();
		}

		public String getDescription()
		{
			return LangUtils.localize("tooltip." + name);
		}

		public ItemStack getStack(int amount)
		{
			return new ItemStack(blockType.getBlock(), amount, meta);
		}

		public boolean canRotateTo(EnumFacing side)
		{
			return facingPredicate.apply(side);
		}

		public boolean hasRotations()
		{
			return !facingPredicate.equals(Predicates.alwaysFalse());
		}

		public boolean hasActiveTexture()
		{
			return activable;
		}
	}

	public static class BasicBlockPredicate implements Predicate<BasicBlockType>
	{
		public BasicBlock basicBlock;

		public BasicBlockPredicate(BasicBlock type)
		{
			basicBlock = type;
		}

		@Override
		public boolean apply(BasicBlockType input)
		{
			return input.blockType == basicBlock;
		}
	}

	public static class BasicBlockStateMapper extends StateMapperBase
	{
		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state)
		{
			BlockBasic block = (BlockBasic)state.getBlock();
			BasicBlockType type = state.getValue(block.getProperty());
			StringBuilder builder = new StringBuilder();
			if(type.hasRotations())
			{
				EnumFacing facing = state.getValue(facingProperty);
				if(type.canRotateTo(facing))
				{
					builder.append(facingProperty.getName());
					builder.append("=");
					builder.append(facing.getName());
				}
				else
				{
					return new ModelResourceLocation("builtin/missing", "missing");
				}
			}
			if(type.hasActiveTexture())
			{
				String active = state.getValue(activeProperty) ? "active" : "inactive";
				if(builder.length() > 0)
				{
					builder.append(",");
				}
				builder.append(active);
			}

			if(builder.length() == 0)
			{
				builder.append("normal");
			}

			ResourceLocation baseLocation = new ResourceLocation("mekanism", type.getName());
			return new ModelResourceLocation(baseLocation, builder.toString());
		}
	}

}
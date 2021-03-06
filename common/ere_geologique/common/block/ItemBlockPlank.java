package ere_geologique.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPlank extends ItemBlock
{
	public ItemBlockPlank(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}
	
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		int metadata = stack.getItemDamage();
		if(metadata > Plank.woodType.length || metadata < 0)
		{
			metadata = 0;
		}
		return super.getUnlocalizedName() + "." + Plank.woodType[metadata];
	}
}
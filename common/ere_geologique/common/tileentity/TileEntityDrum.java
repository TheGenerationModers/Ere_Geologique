package ere_geologique.common.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import ere_geologique.common.EreGeologique;
import ere_geologique.common.entity.Dinosaure;
import ere_geologique.common.entity.TRex;
import ere_geologique.common.entity.enums.EnumDinoType;
import ere_geologique.common.entity.enums.EnumOrderType;
import ere_geologique.common.item.EGItemList;

public class TileEntityDrum extends TileEntity
{
    public EnumOrderType Order;

    public TileEntityDrum()
    {
        this.Order = EnumOrderType.Stay;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setByte("Order", (byte)this.Order.ordinal());
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.Order = EnumOrderType.values()[var1.getByte("Order")];
    }

    public void TriggerOrder(EntityPlayer var1)
    {
        this.Order = this.Order.Next();
        this.worldObj.playSoundEffect((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, "ere_geologique:drum_single", 8.0F, 1.0F);//(float)Math.pow(2.0D, (double)(this.Order.ordinal()/*.ToInt() - 1*/)));
        EreGeologique.ShowMessage(StatCollector.translateToLocal("drum.trigger") + StatCollector.translateToLocal("order." + this.Order.toString()), var1);
        this.markDirty();
    }

    public boolean SendOrder(Item item, EntityPlayer player)
    {
        this.worldObj.playSoundEffect((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, "ere_geologique:drum_triple", 8.0F, 1.0F); // (float)Math.pow(2.0D, (double)(this.Order.ordinal()/*ToInt() - 1*/)));

        if (item != EGItemList.skullStick)
        {
            for (int i = 0; i < EnumDinoType.values().length; ++i)
            {
                if (EnumDinoType.values()[i].OrderItem != null && EnumDinoType.values()[i].OrderItem == item)
                {
                    EreGeologique.ShowMessage(StatCollector.translateToLocal("drum.order") + StatCollector.translateToLocal("dino." + EnumDinoType.values()[i].toString()) + ": " + StatCollector.translateToLocal("order." + this.Order.toString()), player);
                }
            }

            List list = this.worldObj.getEntitiesWithinAABB(Dinosaure.class, AxisAlignedBB.getAABBPool().getAABB((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)this.xCoord + 1.0D, (double)this.yCoord + 1.0D, (double)this.zCoord + 1.0D).expand(30.0D, 4.0D, 30.0D));
            Iterator it = list.iterator();

            while (it.hasNext())
            {
                Entity entity = (Entity)it.next();
                Dinosaure dinosaure = (Dinosaure)entity;

                if (item == dinosaure.SelfType.OrderItem && dinosaure.isTamed() && player.getGameProfile().getName().equalsIgnoreCase(dinosaure.getOwnerName()))
                {
                    dinosaure.SetOrder(this.Order);
                }
            }
        }
        else
        {
            EreGeologique.ShowMessage(StatCollector.translateToLocal("drum.tRex" + String.valueOf(this.Order.ordinal() + 1)), player);
            List list = this.worldObj.getEntitiesWithinAABB(TRex.class, AxisAlignedBB.getAABBPool().getAABB((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)this.xCoord + 1.0D, (double)this.yCoord + 1.0D, (double)this.zCoord + 1.0D).expand(50.0D, 4.0D, 50.0D));
            Iterator it = list.iterator();

            while (it.hasNext())
            {
                Entity var4 = (Entity)it.next();
                TRex var5 = (TRex)var4;

                if (var5.isAdult() && !var5.isTamed())
                {
                    var5.setAngry(true);
                    var5.setAttackTarget(player);
                }
            }
        }
        return true;
    }
}
package ere_geologique.common.entity;

import java.awt.Color;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;
import ere_geologique.common.EreGeologique;
import ere_geologique.common.entity.enums.EnumDinoType;

public class EGEntityList
{
	public static void loadEntity()
	{
		try
		{
			EntityRegistry.registerGlobalEntityID(CroMagnon.class, "CroMagnon", EntityRegistry.findGlobalUniqueEntityId(), 24, 30);
			EntityRegistry.registerModEntity(CroMagnon.class, "CroMagnon", 230, EreGeologique.instance, 40, 1, true);
			EntityRegistry.addSpawn(CroMagnon.class, 5, 2, 4, EnumCreatureType.monster, BiomeGenBase.plains);
	   
			for(int i=0;i<EnumDinoType.values().length;i++)
			{
				EntityRegistry.registerGlobalEntityID(EnumDinoType.values()[i].getDinoClass(), EnumDinoType.values()[i].name(), EntityRegistry.findGlobalUniqueEntityId() + i, new Color(255, 255, 255).getRGB(), new Color(0, 0, 0).getRGB());
				EntityRegistry.registerModEntity(EnumDinoType.values()[i].getDinoClass(), EnumDinoType.values()[i].name(), 233 + i, EreGeologique.instance, 250, 1, true);
			}
	   
			EntityRegistry.registerModEntity(DinoEgg.class, "DinoEgg", 231, EreGeologique.instance, 250, 1, true);
			EntityRegistry.registerModEntity(Failuresaurus.class, "Failuresaurus", 232, EreGeologique.instance, 250, 1, true);
			
		}
		catch(Exception ex)
		{
			EreGeologique.egLog.severe("Erreur lors de l'initialisation des entity's!");
		}
		EreGeologique.egLog.info("Initialisation des entity's terminés!");
	}
}
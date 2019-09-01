package littlechisels.main;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import littlechisels.minecraft.nbt.CompressedStreamTools;
import littlechisels.minecraft.nbt.NBTBase;
import littlechisels.minecraft.nbt.NBTTagCompound;
import littlechisels.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BlockRegistry {
    private final BiMap<Integer, String> registry;

    public BlockRegistry(BiMap<Integer, String> registry) {
        this.registry = registry;
    }

    public String getBlockName (int id) {
        return registry.get(id);
    }

    public int getBlockId (String name) {
        return registry.inverse().get(name);
    }

    public static BlockRegistry loadRegistry (WorldSave saveFolder) throws IOException {
        BiMap<Integer, String> blockRegistry = HashBiMap.create();

        NBTTagCompound levelData = CompressedStreamTools.readCompressed(new FileInputStream(saveFolder.getLevelDat()));
        NBTTagCompound fml = levelData.getCompoundTag("FML");
        NBTTagCompound registries = fml.getCompoundTag("Registries");
        NBTTagCompound blocks = registries.getCompoundTag("minecraft:blocks");

        NBTTagList ids = blocks.getTagList("ids", 10);
        for (NBTBase base : ids) {
            NBTTagCompound compound = (NBTTagCompound) base;
            int id = compound.getInteger("V");
            String name = compound.getString("K");
            blockRegistry.put(id, name);
        }

        return new BlockRegistry(blockRegistry);
    }

    public BiMap<Integer, String> getRegistry() {
        return registry;
    }
}

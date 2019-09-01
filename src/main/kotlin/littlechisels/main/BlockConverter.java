package littlechisels.main;

import littlechisels.minecraft.NibbleArray;
import littlechisels.minecraft.nbt.NBTBase;
import littlechisels.minecraft.nbt.NBTTagCompound;
import littlechisels.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockConverter {
    private final BlockRegistry blockRegistry;

    public BlockConverter(BlockRegistry blockRegistry) {
        this.blockRegistry = blockRegistry;
    }

    public void convertBlocks (NBTTagCompound level) {
        NBTTagList sections = level.getTagList("Sections", 10);

        Set<Integer> chisels = getChiselIds();

        int littleTiles = blockRegistry.getBlockId("littletiles:blocklittletiles");
        int a = littleTiles & 255;
        int b = littleTiles >> 8;

        for (NBTBase it : sections) {
            NBTTagCompound tagCompound = (NBTTagCompound) it;
            byte[] blocks = tagCompound.getByteArray("Blocks");
            byte[] extraData = tagCompound.getByteArray("Add");
            NibbleArray add = extraData.length == 0 ? null : new NibbleArray(extraData);
            for (int k = 0; k < 4096; k++) {
                int id = getBlockId(blocks[k], add != null ? add.getFromIndex(k) : 0);
                if (chisels.contains(id)) {
                    blocks[k] = (byte) a;
                    if (add != null) {
                        add.setIndex(k, b);
                    }
                }
            }
        }
    }

    private int getBlockId(byte block, int upper1) {
        int lower = block & 255;
        int upper = upper1;

        return (upper << 8) + lower;
    }

    @NotNull
    private Set<Integer> getChiselIds() {
        return blockRegistry.getRegistry().inverse().entrySet().stream()
                    .filter(it -> it.getKey().startsWith("chiselsandbits:chiseled_"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet());
    }
}

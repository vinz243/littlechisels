package littlechisels.main;

import littlechisels.minecraft.anvil.RegionFile;
import littlechisels.minecraft.nbt.CompressedStreamTools;
import littlechisels.minecraft.nbt.NBTTagCompound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegionConverter {
    private final BlockConverter blockConverter;
    private final TileEntityConverter entityConverter;

    public RegionConverter(BlockConverter blockConverter, TileEntityConverter entityConverter) {
        this.blockConverter = blockConverter;
        this.entityConverter = entityConverter;
    }

    public void convertRegion(RegionFile regionfile) throws IOException {
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);

                if (datainputstream != null) {
                    NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
                    datainputstream.close();
                    NBTTagCompound level = nbttagcompound.getCompoundTag("Level");
                    entityConverter.convertTileEntities(level);
                    blockConverter.convertBlocks(level);

                    try (DataOutputStream dataoutputstream = regionfile.getChunkDataOutputStream(i, j)) {
                        CompressedStreamTools.write(nbttagcompound, dataoutputstream);
                    }
                }
            }
        }
    }
}

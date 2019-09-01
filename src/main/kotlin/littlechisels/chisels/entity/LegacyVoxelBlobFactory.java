package littlechisels.chisels.entity;

import littlechisels.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.zip.GZIPInputStream;

public class LegacyVoxelBlobFactory {
    private final NBTTagCompound compound;

    public static final String NBT_LEGACY_VOXEL = "v";
    public static final int FULL_SIZE = 16 * 16 * 16;
    private static final int SHORT_BYTES = Short.SIZE / 8;

    public LegacyVoxelBlobFactory(NBTTagCompound compound) {
        this.compound = compound;
    }

    public VoxelBlob create() throws IOException {
        final byte[] vx = compound.getByteArray(NBT_LEGACY_VOXEL);
        int[] values = new int[FULL_SIZE];
        final GZIPInputStream w = new GZIPInputStream(new ByteArrayInputStream(vx));
        final ByteBuffer bb = ByteBuffer.allocate(values.length * SHORT_BYTES);

        w.read(bb.array());
        final ShortBuffer src = bb.asShortBuffer();
        for (int x = 0; x < FULL_SIZE; x++) {
            values[x] = src.get() & 0xffff;
        }

        w.close();

        return new VoxelBlob(values);
    }
}

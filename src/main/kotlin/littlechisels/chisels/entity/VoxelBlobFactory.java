package littlechisels.chisels.entity;

import io.netty.buffer.Unpooled;
import littlechisels.minecraft.PacketBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.InflaterInputStream;

import static littlechisels.chisels.entity.LegacyVoxelBlobFactory.FULL_SIZE;

public class VoxelBlobFactory {
    public static final int VERSION_ANY = -1;
    public static final int VERSION_COMPACT = 0;
    public static final int VERSION_CROSSWORLD_LEGACY = 1; // stored meta.
    public static final int VERSION_CROSSWORLD = 2;

    public VoxelBlob blobFromBytes(
            final byte[] bytes) throws IOException {
        final ByteArrayInputStream out = new ByteArrayInputStream(bytes);
        return read(out);
    }

    private VoxelBlob read(
            final ByteArrayInputStream o) throws IOException, RuntimeException {
        final InflaterInputStream w = new InflaterInputStream(o);
        final ByteBuffer bb = BlobSerilizationCache.getCacheBuffer();

        int usedBytes = 0;
        int rv = 0;

        do {
            usedBytes += rv;
            rv = w.read(bb.array(), usedBytes, bb.limit() - usedBytes);
        }
        while (rv > 0);

        final PacketBuffer header = new PacketBuffer(Unpooled.wrappedBuffer(bb));

        final int version = header.readVarIntFromBuffer();

        BlobSerializer bs = null;

        if (version == VERSION_COMPACT) {
            bs = new BlobSerializer(header);
        } else if (version == VERSION_CROSSWORLD_LEGACY) {
            bs = new CrossWorldBlobSerializerLegacy(header);
        } else if (version == VERSION_CROSSWORLD) {
            bs = new CrossWorldBlobSerializer(header);
        } else {
            throw new RuntimeException("Invalid Version: " + version);
        }

        final int byteOffset = header.readVarIntFromBuffer();
        final int bytesOfInterest = header.readVarIntFromBuffer();

        final BitStream bits = BitStream.valueOf(byteOffset, ByteBuffer.wrap(bb.array(), header.readerIndex(), bytesOfInterest));


        int[] values = new int[FULL_SIZE];
        for (int x = 0; x < FULL_SIZE; x++) {
            values[x] = bs.readVoxelStateID(bits);// src.get();
        }

        w.close();

        return new VoxelBlob(values);
    }
}

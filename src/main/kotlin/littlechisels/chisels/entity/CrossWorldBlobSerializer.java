package littlechisels.chisels.entity;

import minecraft.PacketBuffer;

public class CrossWorldBlobSerializer extends BlobSerializer {

    public CrossWorldBlobSerializer(
            final PacketBuffer toInflate) {
        super(toInflate);
    }

    public CrossWorldBlobSerializer(
            final VoxelBlob toDeflate) {
        super(toDeflate);
    }

    @Override
    protected int readStateID(
            final PacketBuffer buffer) {
        final String name = buffer.readStringFromBuffer(2047);
        buffer.readStringFromBuffer(2047);

        return 0;
    }
}

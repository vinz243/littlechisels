package littlechisels.chisels.entity;

import littlechisels.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.util.Arrays;

public class ChiselEntity {

    public static final String NBT_SIDE_FLAGS = "s";
    public static final String NBT_NORMALCUBE_FLAG = "nc";
    public static final String NBT_LIGHTVALUE = "lv";

    public static final String NBT_PRIMARY_STATE = "b";
    public static final String NBT_VERSIONED_VOXEL = "X";

    private final boolean normalCube;
    private final int sideState;
    private final int lightLevel;
    private final VoxelBlob voxelBlob;

    public ChiselEntity(boolean normalCube, int sideState, int lightLevel, VoxelBlob voxelBlob) {
        this.normalCube = normalCube;
        this.sideState = sideState;
        this.lightLevel = lightLevel;
        this.voxelBlob = voxelBlob;
    }

    public boolean isNormalCube() {
        return normalCube;
    }

    public int getSideState() {
        return sideState;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public VoxelBlob getVoxelBlob() {
        return voxelBlob;
    }

    @Override
    public String toString() {
        return "ChiselEntity{" +
                "normalCube=" + normalCube +
                ", sideState=" + sideState +
                ", lightLevel=" + lightLevel +
                ", voxelBlob=" + voxelBlob +
                '}';
    }

    public static ChiselEntity fromCompound(NBTTagCompound compound) throws IOException {
        int sideState = compound.getInteger(NBT_SIDE_FLAGS);

//        if ( compound.getTag( NBT_PRIMARY_STATE ) instanceof NBTTagString )
//        {
//            primaryBlockState = StringStates.getStateIDFromName( compound.getString( NBT_PRIMARY_STATE ) );
//        }
//        {
//            primaryBlockState = compound.getInteger( NBT_PRIMARY_STATE );
//        }

        int lightValue = compound.getInteger(NBT_LIGHTVALUE);
        boolean normalCube = compound.getBoolean(NBT_NORMALCUBE_FLAG);
        return new ChiselEntity(normalCube, sideState, lightValue, createVoxel(compound));

//        if (primaryBlockState == 0) {
//            // if load fails default to cobble stone...
//            primaryBlockState = ModUtil.getStateId(Blocks.COBBLESTONE.getDefaultState());
//        }

//        voxelBlobRef = new VoxelBlobStateReference(v, 0);
//        format = voxelBlobRef.getFormat();
//
//        boolean formatChanged = false;
//
//        if (preferedFormat != format && preferedFormat != VoxelBlob.VERSION_ANY) {
//            formatChanged = true;
//            v = voxelBlobRef.getVoxelBlob().blobToBytes(preferedFormat);
//            voxelBlobRef = new VoxelBlobStateReference(v, 0);
//            format = voxelBlobRef.getFormat();
//        }
//
//        if (tile != null) {
//            if (formatChanged) {
//                // this only works on already loaded tiles, so i'm not sure
//                // there is much point in it.
//                tile.markDirty();
//            }
//
//            return tile.updateBlob(this, triggerUpdates);
//        }
    }

    private static VoxelBlob createVoxel(NBTTagCompound compound) throws IOException {
        byte[] v = compound.getByteArray(NBT_VERSIONED_VOXEL);

        if (v == null || v.length == 0) {
            return new LegacyVoxelBlobFactory(compound).create();
        }
        return new VoxelBlobFactory().blobFromBytes(v);
    }
}

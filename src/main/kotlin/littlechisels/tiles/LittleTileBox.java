package littlechisels.tiles;

import littlechisels.minecraft.nbt.NBTTagIntArray;

public class LittleTileBox {
    public final int minX;
    public final int minY;
    public final int minZ;
    public final int maxX;
    public final int maxY;
    public final int maxZ;

    public LittleTileBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public int[] getArray() {
        return new int[] { minX, minY, minZ, maxX, maxY, maxZ };
    }

    public NBTTagIntArray getNBTIntArray() {
        return new NBTTagIntArray(getArray());
    }
}

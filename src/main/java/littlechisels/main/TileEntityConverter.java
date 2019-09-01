package littlechisels.main;

import littlechisels.chisels.entity.ChiselEntity;
import littlechisels.converter.math.Box;
import littlechisels.converter.math.IVoxelGrid;
import littlechisels.converter.math.Vec3;
import littlechisels.converter.math.VoxelGrid;
import littlechisels.converter.merger.VoxelMerger;
import littlechisels.minecraft.nbt.NBTBase;
import littlechisels.minecraft.nbt.NBTTagCompound;
import littlechisels.minecraft.nbt.NBTTagList;
import littlechisels.tiles.LittleNBTCompressionTools;
import littlechisels.tiles.LittleTile;
import littlechisels.tiles.LittleTileBox;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TileEntityConverter {
    private final BlockRegistry blockRegistry;
    private final VoxelMerger voxelMerger;

    public TileEntityConverter(BlockRegistry blockRegistry, VoxelMerger voxelMerger) {
        this.blockRegistry = blockRegistry;
        this.voxelMerger = voxelMerger;
    }

    public void convertTileEntities(NBTTagCompound level) throws IOException {
        NBTTagList entities = level.getTagList("TileEntities", 10);
        int index = 0;
        if (entities.tagCount() > 0) {
            for (NBTBase entity : entities) {
                NBTTagCompound tagCompound = (NBTTagCompound) entity;

                if (tagCompound.getString("id").equals("minecraft:mod.chiselsandbits.tileentitychiseled")) {
                    NBTTagCompound tileEntity = convertTileEntity(tagCompound);

                    entities.set(index, tileEntity);
                }


                index++;
            }

        }
    }

    private NBTTagCompound convertTileEntity(NBTTagCompound tagCompound) throws IOException {
        ChiselEntity chiselEntity = ChiselEntity.fromCompound(tagCompound);

        NBTTagCompound tileEntity = new NBTTagCompound();
        tileEntity.setString("id", "minecraft:littletilestileentity");
        tileEntity.setInteger("x", tagCompound.getInteger("x"));
        tileEntity.setInteger("y", tagCompound.getInteger("y"));
        tileEntity.setInteger("z", tagCompound.getInteger("z"));

        List<LittleTile> tiles = new LinkedList<>();
        VoxelGrid voxelGrid = new VoxelGrid(new Vec3(16, 16, 16), new int[4096]);

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    int data = chiselEntity.getVoxelBlob().get(x, y, z);
                    voxelGrid.set(x, y, z, data);
                }
            }
        }

        voxelMerger.convert(voxelGrid).forEach(it -> tiles.add(toLittleTile(it, voxelGrid)));


        NBTTagList list = LittleNBTCompressionTools.writeTiles(tiles);
        tileEntity.setTag("tiles", list);
        return tileEntity;
    }

    private LittleTile toLittleTile(Box it, IVoxelGrid voxelGrid) {
        Vec3 min = it.getMin();
        Vec3 max = it.getMax();
        return new LittleTile(new LittleTileBox(
                min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()
        ), blockRegistry.getBlockName(voxelGrid.get(min)));
    }
}

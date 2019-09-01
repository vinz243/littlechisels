package littlechisels.tiles;

import littlechisels.minecraft.nbt.NBTTagCompound;
import littlechisels.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class LittleTile {
    private final LittleTileBox box;
    private final String block;
    private final int meta;

    public LittleTile(LittleTileBox box, String block, int meta) {
        this.box = box;
        this.block = block;
        this.meta = meta;
    }

    public LittleTileBox getBox() {
        return box;
    }


    // ================Save & Loading================

    public NBTTagCompound startNBTGrouping() {
        NBTTagCompound nbt = new NBTTagCompound();
        saveTile(nbt);

        nbt.removeTag("box");

        NBTTagList list = new NBTTagList();
        list.appendTag(box.getNBTIntArray());
        nbt.setTag("boxes", list);

        return nbt;
    }

    public boolean canBeNBTGrouped(LittleTile tile) {
        return true;
    }


    public void groupNBTTile(NBTTagCompound nbt, LittleTile tile) {
        NBTTagList list = nbt.getTagList("boxes", 11);

        /* for (int i = 0; i < tile.boundingBoxes.size(); i++) {
         * list.appendTag(tile.boundingBoxes.get(i).getNBTIntArray()); } */
        list.appendTag(tile.box.getNBTIntArray());
    }

    public List<NBTTagCompound> extractNBTFromGroup(NBTTagCompound nbt) {
        List<NBTTagCompound> tags = new ArrayList<>();
        NBTTagList list = nbt.getTagList("boxes", 11);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound copy = nbt.copy();
            NBTTagList small = new NBTTagList();
            small.appendTag(list.get(i));
            copy.setTag("boxes", small);
            tags.add(copy);
        }
        return tags;
    }

    public void saveTile(NBTTagCompound nbt) {
        saveTileCore(nbt);
        saveTileExtra(nbt);
    }

    /** Used to save extra data like block-name, meta, color etc. everything
     * necessary for a preview **/
    public void saveTileExtra(NBTTagCompound nbt) {
//        if (false)
//            nbt.setBoolean("invisible", invisible);
//        if (glowing)
//            nbt.setBoolean("glowing", glowing);
//        if (getStructureAttribute() == LittleStructureAttribute.PREMADE)
//            nbt.setBoolean("nodrop", true);

        nbt.setString("block", block);
        nbt.setInteger("meta", 0);

    }

    public void saveTileCore(NBTTagCompound nbt) {
        nbt.setString("tID", "BlockTileBlock");
        nbt.setIntArray("box", box.getArray());
    }
}

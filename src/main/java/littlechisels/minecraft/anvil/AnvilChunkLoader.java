package littlechisels.minecraft.anvil;

import littlechisels.minecraft.math.ChunkPos;
import littlechisels.minecraft.nbt.CompressedStreamTools;
import littlechisels.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class AnvilChunkLoader {
    public final File chunkSaveLocation;

    public AnvilChunkLoader(File chunkSaveLocationIn) {
        this.chunkSaveLocation = chunkSaveLocationIn;
    }

    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    @Nullable
    public NBTTagCompound loadChunk(int x, int z) throws IOException {
        return this.loadChunk__Async(x, z);
    }

    public NBTTagCompound loadChunk__Async(int x, int z) throws IOException {
        ChunkPos chunkpos = new ChunkPos(x, z);
        NBTTagCompound nbttagcompound = null;

        DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);

        if (datainputstream == null) {
            return null;
        }

        nbttagcompound = CompressedStreamTools.read(datainputstream);

        return nbttagcompound;
    }


//
//    public void saveChunk(World worldIn, Chunk chunkIn) throws littlechisels.minecraftException, IOException
//    {
//        worldIn.checkSessionLock();
//
//        try
//        {
//            NBTTagCompound nbttagcompound = new NBTTagCompound();
//            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//            nbttagcompound.setTag("Level", nbttagcompound1);
//            nbttagcompound.setInteger("DataVersion", 1139);
//            net.littlechisels.minecraftforge.fml.common.FMLCommonHandler.instance().getDataFixer().writeVersionData(nbttagcompound);
//            this.writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
//            net.littlechisels.minecraftforge.common.littlechisels.minecraftForge.EVENT_BUS.post(new net.littlechisels.minecraftforge.event.world.ChunkDataEvent.Save(chunkIn, nbttagcompound));
//            this.addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
//        }
//        catch (Exception exception)
//        {
//            LOGGER.error("Failed to save chunk", (Throwable)exception);
//        }
//    }
//
//    protected void addChunkToPending(ChunkPos pos, NBTTagCompound compound)
//    {
//        if (!this.field_193415_c.contains(pos))
//        {
//            this.chunksToRemove.put(pos, compound);
//        }
//
//        ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
//    }
//
//    /**
//     * Returns a boolean stating if the write was unsuccessful.
//     */
//    public boolean writeNextIO()
//    {
//        if (this.chunksToRemove.isEmpty())
//        {
//            if (this.savingExtraData)
//            {
//                LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)this.chunkSaveLocation.getName());
//            }
//
//            return false;
//        }
//        else
//        {
//            ChunkPos chunkpos = this.chunksToRemove.keySet().iterator().next();
//            boolean lvt_3_1_;
//
//            try
//            {
//                this.field_193415_c.add(chunkpos);
//                NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkpos);
//
//                if (nbttagcompound != null)
//                {
//                    try
//                    {
//                        this.writeChunkData(chunkpos, nbttagcompound);
//                    }
//                    catch (Exception exception)
//                    {
//                        LOGGER.error("Failed to save chunk", (Throwable)exception);
//                    }
//                }
//
//                lvt_3_1_ = true;
//            }
//            finally
//            {
//                this.field_193415_c.remove(chunkpos);
//            }
//
//            return lvt_3_1_;
//        }
//    }
//
//    private void writeChunkData(ChunkPos pos, NBTTagCompound compound) throws IOException
//    {
//        DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, pos.chunkXPos, pos.chunkZPos);
//        CompressedStreamTools.write(compound, dataoutputstream);
//        dataoutputstream.close();
//    }
//
//    /**
//     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
//     * Currently unused.
//     */
//    public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException
//    {
//    }
//
//    /**
//     * Called every World.tick()
//     */
//    public void chunkTick()
//    {
//    }
//
//    /**
//     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
//     * unused.
//     */
//    public void saveExtraData()
//    {
//        try
//        {
//            this.savingExtraData = true;
//
//            while (this.writeNextIO());
//        }
//        finally
//        {
//            this.savingExtraData = false;
//        }
//    }
//

//    /**
//     * Writes the Chunk passed as an argument to the NBTTagCompound also passed, using the World argument to retrieve
//     * the Chunk's last update time.
//     */
//    private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound compound)
//    {
//        compound.setInteger("xPos", chunkIn.xPosition);
//        compound.setInteger("zPos", chunkIn.zPosition);
//        compound.setLong("LastUpdate", worldIn.getTotalWorldTime());
//        compound.setIntArray("HeightMap", chunkIn.getHeightMap());
//        compound.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
//        compound.setBoolean("LightPopulated", chunkIn.isLightPopulated());
//        compound.setLong("InhabitedTime", chunkIn.getInhabitedTime());
//        ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
//        NBTTagList nbttaglist = new NBTTagList();
//        boolean flag = worldIn.provider.func_191066_m();
//
//        for (ExtendedBlockStorage extendedblockstorage : aextendedblockstorage)
//        {
//            if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
//            {
//                NBTTagCompound nbttagcompound = new NBTTagCompound();
//                nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 255));
//                byte[] abyte = new byte[4096];
//                NibbleArray nibblearray = new NibbleArray();
//                NibbleArray nibblearray1 = extendedblockstorage.getData().getDataForNBT(abyte, nibblearray);
//                nbttagcompound.setByteArray("Blocks", abyte);
//                nbttagcompound.setByteArray("Data", nibblearray.getData());
//
//                if (nibblearray1 != null)
//                {
//                    nbttagcompound.setByteArray("Add", nibblearray1.getData());
//                }
//
//                nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
//
//                if (flag)
//                {
//                    nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
//                }
//                else
//                {
//                    nbttagcompound.setByteArray("SkyLight", new byte[extendedblockstorage.getBlocklightArray().getData().length]);
//                }
//
//                nbttaglist.appendTag(nbttagcompound);
//            }
//        }
//
//        compound.setTag("Sections", nbttaglist);
//        compound.setByteArray("Biomes", chunkIn.getBiomeArray());
//        chunkIn.setHasEntities(false);
//        NBTTagList nbttaglist1 = new NBTTagList();
//
//        for (int i = 0; i < chunkIn.getEntityLists().length; ++i)
//        {
//            for (Entity entity : chunkIn.getEntityLists()[i])
//            {
//                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
//
//                try
//                {
//                if (entity.writeToNBTOptional(nbttagcompound2))
//                {
//                    chunkIn.setHasEntities(true);
//                    nbttaglist1.appendTag(nbttagcompound2);
//                }
//                }
//                catch (Exception e)
//                {
//                    net.littlechisels.minecraftforge.fml.common.FMLLog.log.error("An Entity type {} has thrown an exception trying to write state. It will not persist. Report this to the mod author",
//                            entity.getClass().getName(), e);
//                }
//            }
//        }
//
//        compound.setTag("Entities", nbttaglist1);
//        NBTTagList nbttaglist2 = new NBTTagList();
//
//        for (TileEntity tileentity : chunkIn.getTileEntityMap().values())
//        {
//            try
//            {
//            NBTTagCompound nbttagcompound3 = tileentity.writeToNBT(new NBTTagCompound());
//            nbttaglist2.appendTag(nbttagcompound3);
//            }
//            catch (Exception e)
//            {
//                net.littlechisels.minecraftforge.fml.common.FMLLog.log.error("A TileEntity type {} has throw an exception trying to write state. It will not persist. Report this to the mod author",
//                        tileentity.getClass().getName(), e);
//            }
//        }
//
//        compound.setTag("TileEntities", nbttaglist2);
//        List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
//
//        if (list != null)
//        {
//            long j = worldIn.getTotalWorldTime();
//            NBTTagList nbttaglist3 = new NBTTagList();
//
//            for (NextTickListEntry nextticklistentry : list)
//            {
//                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//                ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(nextticklistentry.getBlock());
//                nbttagcompound1.setString("i", resourcelocation == null ? "" : resourcelocation.toString());
//                nbttagcompound1.setInteger("x", nextticklistentry.position.getX());
//                nbttagcompound1.setInteger("y", nextticklistentry.position.getY());
//                nbttagcompound1.setInteger("z", nextticklistentry.position.getZ());
//                nbttagcompound1.setInteger("t", (int)(nextticklistentry.scheduledTime - j));
//                nbttagcompound1.setInteger("p", nextticklistentry.priority);
//                nbttaglist3.appendTag(nbttagcompound1);
//            }
//
//            compound.setTag("TileTicks", nbttaglist3);
//        }
//    }

}
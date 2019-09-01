package littlechisels.main

import littlechisels.converter.merger.ConvexMerger
import littlechisels.converter.merger.stonecutter.FastStoneCutterMerger
import littlechisels.minecraft.anvil.RegionFile

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Converter {

    val MAX = 256

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val saveFolder = "C:\\Users\\Vincent\\AppData\\Roaming\\.minecraft\\saves\\chisel 2\\"
        val blockRegistry = BlockRegistry.loadRegistry(WorldSave(File(saveFolder, "level.dat")))

        val regionConverter = RegionConverter(
            BlockConverter(blockRegistry),
            TileEntityConverter(blockRegistry, FastStoneCutterMerger(ConvexMerger()))
        )

        Files.list(Paths.get(saveFolder + "region"))
            .filter { it.toString().endsWith(".mca") }
            .forEach { file ->
                val regionfile = RegionFile(file.toFile())
                regionConverter.convertRegion(regionfile)

                regionfile.close()
            }
    }

}

package littlechisels.main

import littlechisels.converter.math.Vec3
import littlechisels.converter.merger.ConvexMerger
import littlechisels.converter.merger.stonecutter.FullStoneCutterMerger
import littlechisels.minecraft.anvil.RegionFile
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarStyle
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object Converter {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val saveFolder = args[0]
        val blockRegistry = BlockRegistry.loadRegistry(
            WorldSave(
                File(
                    saveFolder,
                    "level.dat"
                )
            )
        )

        println("Loaded block registry with ${blockRegistry.registry.size} mappings")

        val regionConverter = RegionConverter(
            BlockConverter(blockRegistry),
            TileEntityConverter(blockRegistry, FullStoneCutterMerger(ConvexMerger.buildConvexMerger(Vec3.unit())))
        )

        val regionFiles = Files.list(Paths.get(saveFolder + "region"))
            .filter { it.toString().endsWith(".mca") }
            .toList()

        ProgressBar("Converting regions", regionFiles.size.toLong(), ProgressBarStyle.ASCII).use {
            regionFiles.parallelStream().forEach { file ->
                val regionFile = RegionFile(file.toFile())
                regionConverter.convertRegion(regionFile)

                regionFile.close()

                it.step()
            }
        }
    }

}

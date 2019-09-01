package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid
import littlechisels.converter.math.VoxelGrid

class AABBGroupedMerger(private val merger: VoxelMerger) : VoxelMerger {


    override fun convert(grid: IVoxelGrid): List<Box> {
        val entries = grid.box().voxels()
                .groupBy { grid[it] }
                .entries

        return entries
                .filter { it.key != 0 }
                .flatMap { group ->
                    val aabb = Box.boundingBox(group.value)
                    val voxelGrid = VoxelGrid(aabb!!.dimensions())
                    val offset = aabb.min
                    group.value.forEach { vec ->
                        voxelGrid[vec - offset] = group.key
                    }

                    return merger.convert(voxelGrid).map { it + offset }
                }

    }

}
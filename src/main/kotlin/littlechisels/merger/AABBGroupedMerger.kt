package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.VoxelGrid

class AABBGroupedMerger(private val merger: VoxelMerger) : VoxelMerger {


    override fun convert(grid: VoxelGrid): List<Box> {
        val entries = grid.box().voxelsExclusive()
                .groupBy { grid[it] }
                .entries

        return entries
                .filter { it.key != 0 }
                .flatMap {
                    val aabb = Box.boundingBox(it.value)
                    val voxelGrid = VoxelGrid(aabb.dimensions() + 1)
                    it.value.forEach {vec ->
                        voxelGrid[vec - aabb.min] = it.key
                    }

                    return merger.convert(voxelGrid)
                }

    }

}
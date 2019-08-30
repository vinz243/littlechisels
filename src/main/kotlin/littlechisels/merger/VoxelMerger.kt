package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.IVoxelGrid
import littlechisels.math.VoxelGrid

interface VoxelMerger {
    fun convert(grid: IVoxelGrid): List<Box>
}
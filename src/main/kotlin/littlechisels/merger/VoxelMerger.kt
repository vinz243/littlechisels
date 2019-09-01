package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.IVoxelGrid

interface VoxelMerger {
    fun convert(grid: IVoxelGrid): List<Box>
}
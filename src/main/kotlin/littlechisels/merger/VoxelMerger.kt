package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.VoxelGrid

interface VoxelMerger {
    fun convert(grid: VoxelGrid): List<Box>
}
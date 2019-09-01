package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid

interface VoxelMerger {
    fun convert(grid: IVoxelGrid): List<Box>
}
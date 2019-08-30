package littlechisels.merger.stonecutter

import littlechisels.math.FilledGrid
import littlechisels.math.IVoxelGrid
import littlechisels.math.Vec3
import littlechisels.math.VoxelGrid
import littlechisels.merger.VoxelMerger

class FastStoneCutterMerger(merger: VoxelMerger) : StoneCutterMerger(merger) {
    override fun tryFillGrid(inputGrid: IVoxelGrid, outGridSize: Int, inputOffset: Vec3): IVoxelGrid? {
        val value = inputGrid[inputOffset]
        if (value == inputGrid[inputOffset + (outGridSize - 1)]) {
            return FilledGrid(Vec3.origin() * outGridSize, value)
        }

        return null
    }
}
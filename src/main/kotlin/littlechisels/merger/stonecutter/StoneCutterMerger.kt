package littlechisels.merger.stonecutter

import littlechisels.math.Box
import littlechisels.math.IVoxelGrid
import littlechisels.math.Vec3
import littlechisels.math.VoxelGrid
import littlechisels.merger.VoxelMerger

abstract class StoneCutterMerger(private val merger: VoxelMerger) : VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        assert(grid.dimensions.x % 2 == 0)
        assert(grid.dimensions.x == grid.dimensions.y && grid.dimensions.x == grid.dimensions.z)

        return doConvert(grid, grid.dimensions.x / 2, Vec3.origin())
    }

    private fun doConvert(grid: IVoxelGrid, gridSize: Int, baseOffset: Vec3): List<Box> {
        val boxes = mutableListOf<Box>()

        for (i in 0..1) {
            for (j in 0..1) {
                for (k in 0..1) {

                    val offset = baseOffset + Vec3(i, j, k) * gridSize

                    val fillGrid = tryFillGrid(grid, gridSize, offset)

                    boxes += if (fillGrid != null) {
                        merger.convert(fillGrid)
                    } else {
                        doConvert(grid, gridSize / 2, offset)
                    }
                }
            }
        }

        return boxes
    }

    abstract fun tryFillGrid(inputGrid: IVoxelGrid, outGridSize: Int, inputOffset: Vec3): IVoxelGrid?


}

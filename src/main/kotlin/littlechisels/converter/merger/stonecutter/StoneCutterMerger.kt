package littlechisels.converter.merger.stonecutter

import littlechisels.converter.math.*
import littlechisels.converter.merger.BoxContentInspector
import littlechisels.converter.merger.VoxelMerger

open class StoneCutterMerger(private val merger: VoxelMerger, private val contentInspector: BoxContentInspector) :
    VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        assert(grid.dimensions.x % 2 == 0)
        assert(grid.dimensions.x == grid.dimensions.y && grid.dimensions.x == grid.dimensions.z)

        return doConvert(grid)
    }

    private fun doConvert(grid: IVoxelGrid): List<Box> {
        val boxes = mutableListOf<Box>()
        val gridSize = grid.dimensions.x / 2

        val reductedGrid = VoxelGrid(Vec3.unit() * 2)

        for (i in 0..1) {
            for (j in 0..1) {
                for (k in 0..1) {
                    val offset = Vec3(i, j, k) * gridSize
                    val box = Box(offset, offset + gridSize)

                    val content = contentInspector.getContent(grid, box)

                    reductedGrid[i, j, k] = content ?: 0

                    if (content == null) {
                        println("trying $box => $content")

                        val subGrid = SubVoxelGrid(box, grid)

                        boxes += doConvert(subGrid).map { it + box.min }
                    }
                }
            }
        }


        boxes += merger.convert(reductedGrid).map { it.min(it.min * gridSize).max(it.max * gridSize) }

        return boxes
    }


}

package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid
import littlechisels.converter.math.Vec3
import java.util.*

class ConvexMerger(val dimensionList: List<Vec3>): VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        println("Convert: ${grid.dimensions}")

        val boxes = mutableListOf<Box>()

        for (boxSize in dimensionList) {
            for (x in 0 until (grid.dimensions.x - boxSize.x)) {
                for (y in 0 until (grid.dimensions.y - boxSize.y)) {
                    for (z in 0 until (grid.dimensions.z - boxSize.z)) {
                        val offset = Vec3(x, y, z)
                        val movedBox = Box(offset, offset + boxSize + 1 )
                        if (!contains(boxes, movedBox)) {
                            val voxels = movedBox.voxels()
                            if (voxels.isEmpty()) {
                                continue
                            }
                            val value = grid[voxels[0]]
                            if (value != 0 && voxels.all {
                                    grid[it.x, it.y, it.z] == value
                                }) {
                                boxes.add(movedBox)
                            }
                        }
                    }
                }
            }

        }

        return boxes
    }

    private fun contains(boxes: MutableList<Box>, other: Box): Boolean {
        for (existing in boxes) {
            if (existing.intersects(other)) {
                return true
            }
        }

        return false
    }

    companion object {
        fun buildConvexMerger (size: Vec3): ConvexMerger {
            val list = mutableListOf<Vec3>()
            for (i in 0..size.x) {
                for (j in 0..size.y) {
                    for (k in 0..size.z) {
                        list.add(Vec3(i, j, k))
                    }
                }
            }

            return ConvexMerger(list.sortedByDescending { it.area() })
        }

    }
}
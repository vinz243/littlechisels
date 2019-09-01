package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid
import littlechisels.converter.math.Vec3

class ConvexMerger: VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        val boxes = mutableListOf<Box>()

        val dim = grid.dimensions
        for (height in dim.x downTo 0) {
            for (width in dim.y downTo 0) {
                for (depth in dim.z downTo 0) {
                    val dims = Vec3(height, width, depth) + 1
                    for (x in 0 until (dim.x - height)) {
                        for (y in 0 until (dim.y - width)) {
                            for (z in 0 until (dim.z - depth)) {
                                val min = Vec3(x, y, z)
                                val box = Box(min, min + (dims))
                                if (!contains(boxes, box)) {
                                    val voxels = box.voxels()
                                    if (voxels.isEmpty()) {
                                        continue
                                    }
                                    val value = grid[voxels[0]]
                                    if (value != 0 && voxels.all {
                                                grid[it.x, it.y, it.z] == value
                                            }) {
                                        boxes.add(box)
                                    }
                                }
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
}
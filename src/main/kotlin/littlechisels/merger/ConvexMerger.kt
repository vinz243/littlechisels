package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.Vec3
import littlechisels.math.VoxelGrid

class ConvexMerger: VoxelMerger {
    override fun convert(grid: VoxelGrid): List<Box> {
        val boxes = mutableListOf<Box>()

        val dim = grid.dimensions
        for (height in dim.x - 1 downTo 1) {
            for (width in dim.y - 1 downTo 1) {
                for (depth in dim.z - 1 downTo 1) {
                    val dims = Vec3(height, width, depth)
                    for (x in 0 until (dim.x - height)) {
                        for (y in 0 until (dim.y - width)) {
                            for (z in 0 until (dim.z - depth)) {
                                val min = Vec3(x, y, z)
                                val box = Box(min, min + (dims))

                                if (!contains(boxes, box)) {

                                    val voxels = box.voxels()
                                    val first = voxels[0]
                                    val value = grid[first.x, first.y, first.z]
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
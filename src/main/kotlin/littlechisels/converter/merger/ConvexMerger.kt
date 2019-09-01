package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid
import littlechisels.converter.math.Vec3

class ConvexMerger: VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        println("Convert: ${grid.dimensions}")

        val boxes = mutableListOf<Box>()

        var box: Box? = Box(Vec3.origin(), grid.dimensions).max { it - 1 }
        while (box != null) {
            println("    $box")
            for (x in 0 until (grid.dimensions.x - box.min.x)) {
                for (y in 0 until (grid.dimensions.y - box.min.y)) {
                    for (z in 0 until (grid.dimensions.z - box.min.z)) {
                        val offset = Vec3(x, y, z)
                        println("        $offset")
                        val movedBox = Box(offset, offset + box.dimensions())
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

            box = box.contractSide()
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
package littlechisels.converter.merger

import littlechisels.converter.math.Box
import littlechisels.converter.math.IVoxelGrid

interface BoxContentInspector {
    fun getContent (grid: IVoxelGrid, box: Box): Int?
}

class FastBoxInspector: BoxContentInspector {
    override fun getContent(grid: IVoxelGrid, box: Box): Int? {
        val value = grid[box.min]
        if (value == grid[box.max - 1]) {
            return value
        }

        return null
    }
}

class FullBoxInspector: BoxContentInspector {
    override fun getContent(grid: IVoxelGrid, box: Box): Int? {
        val value = grid[box.min]

        if (box.voxels().all { grid[it] == value }) {
            return value
        }

        return null
    }
}
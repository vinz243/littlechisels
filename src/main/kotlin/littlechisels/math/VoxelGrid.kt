package littlechisels.math

import littlechisels.math.Box
import littlechisels.math.Vec3

data class VoxelGrid(val dimensions: Vec3,
                private val grid: IntArray = IntArray(dimensions.x * dimensions.y * dimensions.z) { 0 }) {

    operator fun get(x: Int, y: Int, z: Int): Int {
        val index = getIndex(x, y, z)
        return grid[index]
    }

    operator fun get(pos: Vec3): Int {
        val index = getIndex(pos.x, pos.y, pos.z)
        return grid[index]
    }

    operator fun set(x: Int, y: Int, z: Int, value: Int) {
        grid[getIndex(x, y, z)] = value
    }

    operator fun set(vec3: Vec3, value: Int) {
        grid[getIndex(vec3.x, vec3.y, vec3.z)] = value
    }


    fun box(): Box = Box(
            Vec3(0, 0, 0),
            dimensions
    )

    private fun getIndex(x: Int, y: Int, z: Int) = x + dimensions.x * (y + dimensions.y * z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VoxelGrid

        if (dimensions != other.dimensions) return false
        if (!grid.contentEquals(other.grid)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dimensions.hashCode()
        result = 31 * result + grid.contentHashCode()
        return result
    }
}
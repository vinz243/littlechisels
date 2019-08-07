package littlechisels.math

import littlechisels.math.Box
import littlechisels.math.Vec3

class VoxelGrid(val dimensions: Vec3,
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
}
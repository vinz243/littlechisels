package littlechisels.math

interface IVoxelGrid {
    val dimensions: Vec3
    operator fun get(x: Int, y: Int, z: Int): Int

    operator fun get(pos: Vec3): Int

    operator fun set(x: Int, y: Int, z: Int, value: Int)

    operator fun set(vec3: Vec3, value: Int)

    fun box(): Box = Box(
            Vec3(0, 0, 0),
            dimensions
    )

    fun getIndex(x: Int, y: Int, z: Int) = x + dimensions.x * (y + dimensions.y * z)
}

data class VoxelGrid(
    override val dimensions: Vec3,
    private val grid: IntArray = IntArray(dimensions.x * dimensions.y * dimensions.z) { 0 }) : IVoxelGrid {

    override operator fun get(x: Int, y: Int, z: Int): Int {
        val index = getIndex(x, y, z)
        return grid[index]
    }

    override operator fun get(pos: Vec3): Int {
        val index = getIndex(pos.x, pos.y, pos.z)
        return grid[index]
    }

    override operator fun set(x: Int, y: Int, z: Int, value: Int) {
        grid[getIndex(x, y, z)] = value
    }

    override operator fun set(vec3: Vec3, value: Int) {
        grid[getIndex(vec3.x, vec3.y, vec3.z)] = value
    }


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

class FilledGrid(
    override val dimensions: Vec3,
    private val value: Int
) : IVoxelGrid {
    override fun get(x: Int, y: Int, z: Int): Int {
        return value
    }

    override fun get(pos: Vec3): Int {
        return value
    }

    override fun set(x: Int, y: Int, z: Int, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(vec3: Vec3, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
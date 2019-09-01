package littlechisels.converter.math

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

class SubVoxelGrid(
    val offset: Box, val grid: IVoxelGrid
): IVoxelGrid {
    override val dimensions: Vec3
        get() = offset.dimensions() + 1

    override fun get(x: Int, y: Int, z: Int): Int = grid[Vec3(x, y, z) + offset.min]

    override fun get(pos: Vec3): Int = grid[pos + offset.min]

    override fun set(x: Int, y: Int, z: Int, value: Int) {
        grid[Vec3(x, y, z) + offset.min] = value
    }

    override fun set(vec3: Vec3, value: Int) {
        grid[vec3 + offset.min] = value
    }
}

class VoxelGrid(
    override val dimensions: Vec3,
    private val grid: IntArray = IntArray(dimensions.x * dimensions.y * dimensions.z) { 0 }
) : IVoxelGrid {

    override operator fun get(x: Int, y: Int, z: Int): Int = get(Vec3(x, y, z))

    override operator fun get(pos: Vec3): Int {
        val index = getIndex(pos.x, pos.y, pos.z)
        try {
            return grid[index]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw ArrayIndexOutOfBoundsException("Index $pos out of bound for length $dimensions")
        }
    }

    override operator fun set(x: Int, y: Int, z: Int, value: Int) = set(Vec3(x, y, z), value)

    override operator fun set(vec3: Vec3, value: Int) {
        try {
            grid[getIndex(vec3.x, vec3.y, vec3.z)] = value
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw ArrayIndexOutOfBoundsException("Index $vec3 out of bound for length $dimensions")
        }
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
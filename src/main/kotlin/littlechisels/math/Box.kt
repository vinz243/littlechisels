package littlechisels.math

/**
 * Represents a 3D box
 */
data class Box(val min: Vec3, val max: Vec3) {

    fun min (value: Vec3) = Box(value, max)
    fun max (value: Vec3) = Box(min, value)

    /**
     * Returns true if the box contains said point (inclusive)
     */
    operator fun contains(point: Vec3): Boolean {
        return point.x >= min.x &&
                point.y >= min.y &&
                point.z >= min.z &&
                point.x <= max.x &&
                point.y <= max.y &&
                point.z <= max.z
    }

    /**
     * Returns true if two boxes intersect themselves (exclusive)
     */
    fun intersects(other: Box): Boolean {
        val set = voxels().toSet()
        return other.voxels().any { set.contains(it) }
    }

    private fun isRightOf(other: Box) = other.min.y <= max.y

    private fun isLeftOf(other: Box) = min.y <= other.max.y

    private fun isBelow(other: Box) = other.min.x >= max.x

    private fun isAbove(other: Box) = min.x >= other.max.x

    fun voxels (): List<Vec3> {
        return IntRange(min.x, max.x).flatMap { x ->
            IntRange(min.y, max.y).flatMap { y ->
                IntRange(min.z, max.z).map { z ->
                    Vec3(x, y, z)
                }
            }
        }
    }

    override fun toString(): String {
        return "Box(min=$min, max=$max)"
    }

    fun dimensions(): Vec3 {
        return max - min
    }

    companion object {
        private val empty = Box(Vec3(0, 0, 0), Vec3(0, 0, 0))
        fun boundingBox (vectors: List<Vec3>): Box? {
            return vectors.fold<Vec3, Box?>(null) { box, el ->
                box?.min(
                    box.min.x(minOf(box.min.x, el.x))
                        .y(minOf(box.min.y, el.y))
                        .z(minOf(box.min.z, el.z))
                )?.max(
                    box.max.x(maxOf(box.max.x, el.x))
                        .y(maxOf(box.max.y, el.y))
                        .z(maxOf(box.max.z, el.z))
                ) ?: Box(el, el)
            }
        }
    }
}

package littlechisels.math

/**
 * Represents a 3D box
 */
class Box(val min: Vec3, val max: Vec3) {

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
        return ((other.min.x > min.x && other.min.x < max.x) || (other.max.x > min.x && other.max.x < max.x)) ||
               ((other.min.y > min.y && other.min.y < max.y) || (other.max.y > min.y && other.max.y < max.y)) ||
               ((other.min.z > min.z && other.min.z < max.z) || (other.max.z > min.z && other.max.z < max.z))
    }

    /**
     * Return a list of the voxels
     */
    fun voxels (): List<Vec3> {
        return IntRange(min.x, max.x).flatMap { x ->
                IntRange(min.y, max.y).flatMap { y ->
                    IntRange(min.z, max.z).map { z ->
                        Vec3(x, y, z)
                    }
                }
        }
    }

    fun voxelsExclusive (): List<Vec3> {
        return IntRange(min.x, max.x - 1).flatMap { x ->
            IntRange(min.y, max.y - 1).flatMap { y ->
                IntRange(min.z, max.z - 1).map { z ->
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
        fun boundingBox (vectors: List<Vec3>): Box {
            return vectors.fold(empty) { box, el ->
                box.min(
                    box.min.x(minOf(box.min.x, el.x))
                        .y(minOf(box.min.y, el.y))
                        .z(minOf(box.min.z, el.z))
                ).max(
                    box.max.x(maxOf(box.max.x, el.x))
                        .y(maxOf(box.max.y, el.y))
                        .z(maxOf(box.max.z, el.z))
                )
            }
        }
    }
}

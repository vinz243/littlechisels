package littlechisels.converter.math

data class Vec3(val x: Int, val y: Int, val z: Int) {

    fun area () = x * y * z

    operator fun plus(vec: Vec3): Vec3 {
        return Vec3(x + vec.x, y + vec.y, z + vec.z)
    }

    operator fun minus(vec: Vec3): Vec3 {
        return Vec3(x - vec.x, y - vec.y, z - vec.z)
    }

    operator fun times(factor: Int): Vec3 {
        return Vec3(x * factor, y * factor, z * factor)
    }

    fun x(value: Int) = Vec3(value, y, z)
    fun y(value: Int) = Vec3(x, value, z)
    fun z(value: Int) = Vec3(x, y, value)

    operator fun plus(i: Int): Vec3 = Vec3(x + i, y + i, z + i)

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    private fun squareLength () = x * x + y * y + z * z

    fun adjacents () = listOf(
        x(x + 1), x(x - 1),
        y(y + 1), y(y - 1),
        z(z + 1), z(z - 1)
    )

    operator fun minus(i: Int): Vec3 = Vec3(x - i, y - i, z - i)

    companion object {
        fun origin(): Vec3 = Vec3(0, 0, 0)
        fun unit(): Vec3 = Vec3(1, 1, 1)
    }
}
package littlechisels.converter.math

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class BoxTests {
    @Test
    public fun contains() {
        val box = Box(Vec3(2, 1, 1), Vec3(4, 2, 3))

        assertTrue(Vec3(3, 1, 2) in box)
        assertFalse(Vec3(5, 1, 2) in box)
    }

    @Test
    fun intersects() {
        val box = Box(Vec3(2, 1, 1), Vec3(4, 2, 3))

        assertTrue(box.intersects(box))
        assertTrue(box.intersects(Box(Vec3(2, 1, 1), Vec3(4, 2, 3))))
        assertTrue((Box(Vec3(2, 1, 1), Vec3(4, 2, 3))).intersects(box))
        assertFalse(box.intersects(Box(Vec3(2, 1, 1) * 4, Vec3(4, 2, 3) * 4)))
        assertFalse((Box(Vec3(2, 1, 1) * 4, Vec3(4, 2, 3) * 4)).intersects(box))
        assertFalse(box.intersects(Box(Vec3(2, 1, 1) * -1, Vec3(4, 2, 3) * -1)))
    }

    @Test
    fun voxels() {
        assertEquals(Box(Vec3(0,0,0), Vec3(1,1,1)).voxels(), listOf(Vec3(0, 0, 0)))
        assertEquals(Box(Vec3(1,1,1), Vec3(2,2,2)).voxels(), listOf(Vec3(1, 1, 1)))
        assertEquals(Box(Vec3(1,1,1), Vec3(2,2,3)).voxels(), listOf(Vec3(1, 1, 1), Vec3(1, 1, 2)))
    }

    @Test
    fun boundingBox () {
        assertEquals(Box(Vec3(1,1,1), Vec3(2,2,2)), Box.boundingBox(listOf(Vec3(1,1,1))))
        assertEquals(Box(Vec3(1,1,1), Vec3(4,4,4)), Box.boundingBox(listOf(Vec3(1,1,1), Vec3(3,3,3))))

        val vec = listOf(Vec3(5, 6, 8))
        val voxels = Box.boundingBox(vec)!!.voxels()
        assertEquals(vec, voxels)
    }

    @Test
    fun dimensions () {
        assertEquals(Vec3(1,1,1), Box(Vec3(0,0,0), Vec3(1,1,1)).dimensions())
        assertEquals(Vec3(1,1,1), Box.boundingBox(listOf(Vec3(1,1,1)))!!.dimensions())
    }

    @Test
    fun contractSide() {
        assertNull(Box(Vec3(0, 0, 0), Vec3(0, 0, 0)).contractSide())
        assertNull(Box(Vec3(0, 0, 0), Vec3(1, 1, 1)).contractSide())
        val square2 = Box(Vec3(0, 0, 0), Vec3(2, 2, 2))
        assertEquals(
            square2.contractSide()!!.contractSide(),
                     Box(Vec3(0, 0, 0), Vec3(2, 1, 1)))
        assertNull(square2.contractSide()!!.contractSide()!!.contractSide()!!.contractSide())
    }
}
package littlechisels.math

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class BoxTests {
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
        assertEquals(Box(Vec3(1,1,1), Vec3(2,2,2)).voxels(), listOf(Vec3(1, 1, 1)))
        assertEquals(Box(Vec3(1,1,1), Vec3(2,2,3)).voxels(), listOf(Vec3(1, 1, 1), Vec3(1, 1, 2)))
    }
}
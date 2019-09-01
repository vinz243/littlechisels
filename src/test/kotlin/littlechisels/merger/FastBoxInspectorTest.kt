package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.Vec3
import littlechisels.math.VoxelGrid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class FastBoxInspectorTest {

    @Test
    fun `getContent 1x1`() {
        val boxInspector = FastBoxInspector()
        val grid = VoxelGrid(Vec3.unit())

        grid[0,0,0] = 1

        assertEquals(1, boxInspector.getContent(grid, Box(Vec3.origin(), Vec3.unit())))
    }


    @Test
    fun `getContent 4x4`() {
        val boxInspector = FastBoxInspector()
        val grid = VoxelGrid(Vec3.unit() * 4)

        val box = Box(Vec3(1, 1, 1), Vec3(3, 3, 3))
        box.voxels().forEach { grid[it] = 4 }

        assertEquals(4, boxInspector.getContent(grid, box))
        assertNull(boxInspector.getContent(grid, Box(Vec3.origin() + 1, grid.dimensions)))
    }

}
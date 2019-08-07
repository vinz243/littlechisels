package littlechisels.graph

import littlechisels.math.Vec3
import littlechisels.math.Voxel
import littlechisels.math.VoxelGrid
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class GraphFactoryTest {
    @Test
    fun connectedGraph2 () {
        val graph = createGraph(Vec3(3, 3, 1), arrayOf(1,0,1,0,1,0,1,0,1))

        Assertions.assertEquals(5, graph.vertexSet().size)

        assertTrue {
            graph.containsVertex(Voxel(Vec3.origin(), material = 1))
        }

        assertFalse {
            graph.containsVertex(Voxel(Vec3.origin().x(1), material = 0))
        }

        assertFalse {
            graph.containsVertex(Voxel(Vec3.origin().x(1), material = 1))
        }

        assertEquals(0, graph.edgeSet().size)
    }

    @Test
    fun connectedGraph () {
        val graph = createGraph(Vec3(3, 3, 1), arrayOf(2, 0, 2, 2, 2, 2, 2, 0, 2))

        Assertions.assertEquals(7, graph.vertexSet().size)

        assertTrue {
            graph.containsVertex(Voxel(Vec3.origin(), material = 2))
        }

        assertFalse {
            graph.containsVertex(Voxel(Vec3.origin().x(1), material = 0))
        }

        assertFalse {
            graph.containsVertex(Voxel(Vec3.origin().x(1), material = 2))
        }

        assertEquals(6, graph.edgeSet().size)
    }

    private fun createGraph(dimensions: Vec3, grid: Array<Int>): SimpleGraph<Voxel, DefaultEdge> {
        return GraphFactory.createConnectedGraph(VoxelGrid(dimensions, grid.toIntArray()))
    }
}
package littlechisels.graph

import littlechisels.math.Voxel
import littlechisels.math.VoxelGrid
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

class GraphFactory {
    companion object {
        fun createConnectedGraph (grid: VoxelGrid): SimpleGraph<Voxel, DefaultEdge> {
            val graph = SimpleGraph<Voxel, DefaultEdge>(DefaultEdge::class.java)

            val voxels = grid.box().voxels()
                .map { Voxel(it, grid[it])  }
                .filter { it.material != 0 }
                .associateBy { it.coords }

            for ((pos, voxel) in voxels) {
                graph.addVertex(voxel)

                for (it in pos.adjacents()) {
                    val adjacentVoxel = voxels[it]
                    if (voxel.material == adjacentVoxel?.material) {
                        graph.addVertex(adjacentVoxel)
                        graph.addEdge(voxel, adjacentVoxel)
                    }
                }
            }

            return graph
        }
    }
}
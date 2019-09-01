package littlechisels.converter.graph

import littlechisels.converter.math.IVoxelGrid
import littlechisels.converter.math.Voxel
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

class GraphFactory {
    companion object {
        fun createConnectedGraph (grid: IVoxelGrid): SimpleGraph<Voxel, DefaultEdge> {
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
package littlechisels.merger

import littlechisels.graph.GraphFactory
import littlechisels.math.Box
import littlechisels.math.VoxelGrid
import org.jgrapht.alg.connectivity.ConnectivityInspector

class ConnectedMerger(private val merger: VoxelMerger): VoxelMerger {
    override fun convert(input: VoxelGrid): List<Box> {
        var millis = System.currentTimeMillis()
        val connectedGraph = GraphFactory.createConnectedGraph(input)

        println("Established graph in ${System.currentTimeMillis() - millis}ms")
        millis = System.currentTimeMillis()

        val inspector = ConnectivityInspector(connectedGraph)
        val connectedSets = inspector.connectedSets()

        println("Inspected connectivity ${System.currentTimeMillis() - millis}ms")
        millis = System.currentTimeMillis()

        val flatMap = connectedSets.flatMap { voxels ->
            val boundingBox = Box.boundingBox(voxels.map { it.coords })
            val grid = VoxelGrid(boundingBox.dimensions() + 1)
            voxels.forEach { vec ->
                grid[vec.coords - boundingBox.min] = vec.material
            }
            merger.convert(grid)
        }

        println("Merged ${System.currentTimeMillis() - millis}ms")


        return flatMap
    }
}
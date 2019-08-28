package littlechisels.merger

import littlechisels.graph.GraphFactory
import littlechisels.math.Box
import littlechisels.math.VoxelGrid
import org.jgrapht.alg.connectivity.ConnectivityInspector

class ConnectedMerger(private val merger: VoxelMerger): VoxelMerger {
    override fun convert(grid: VoxelGrid): List<Box> {
        var millis = System.currentTimeMillis()
        val connectedGraph = GraphFactory.createConnectedGraph(grid)

        println("Established graph in ${System.currentTimeMillis() - millis}ms")
        millis = System.currentTimeMillis()

        val inspector = ConnectivityInspector(connectedGraph)
        val connectedSets = inspector.connectedSets()

        println("Inspected connectivity ${System.currentTimeMillis() - millis}ms")
        millis = System.currentTimeMillis()

        val flatMap = connectedSets.flatMap { voxels ->
            val boundingBox = Box.boundingBox(voxels.map { it.coords })
            val smallerGrid = VoxelGrid(boundingBox!!.dimensions())
            val offset = boundingBox.min
            voxels.forEach { vec ->
                smallerGrid[vec.coords - offset] = vec.material
            }
            val converted = merger.convert(smallerGrid)
            converted.map { it + offset }
        }

        println("Merged ${System.currentTimeMillis() - millis}ms")


        return flatMap
    }
}
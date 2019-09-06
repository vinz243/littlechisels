package littlechisels.converter.merger

import littlechisels.converter.graph.GraphFactory
import littlechisels.converter.math.*
import org.jgrapht.alg.connectivity.ConnectivityInspector

class ConnectedMerger(private val merger: VoxelMerger): VoxelMerger {
    override fun convert(grid: IVoxelGrid): List<Box> {
        val connectedGraph = GraphFactory.createConnectedGraph(grid)

        val inspector = ConnectivityInspector(connectedGraph)
        val connectedSets = inspector.connectedSets()

        return connectedSets.flatMap { voxels ->
            val boundingBox = Box.boundingBox(voxels.mapNotNull<Voxel?, Vec3> { it?.coords })
            val smallerGrid = VoxelGrid(boundingBox!!.dimensions())
            val offset = boundingBox.min
            voxels.forEach<Voxel?> { vec ->
                if (vec != null) {
                    smallerGrid[vec.coords - offset] = vec.material
                }
            }
            val converted = merger.convert(smallerGrid)
            converted.map { it + offset }
        }
    }
}
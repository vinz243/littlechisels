package littlechisels.merger

import littlechisels.math.Box
import littlechisels.math.Vec3
import littlechisels.math.VoxelGrid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class MergerTest {
    companion object {
        @JvmStatic
        fun testParams (): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(4, listOf(Box(Vec3(0, 0, 0), Vec3(3, 3, 3)))),
                    Arguments.of(4, listOf(Box(Vec3(1, 1, 1), Vec3(3, 3, 3)))),
                    Arguments.of(4, listOf(Box(Vec3(1, 1, 1), Vec3(2, 2, 2)))),
                    Arguments.of(4, listOf(Box(Vec3(1, 2, 1), Vec3(2, 3, 2)))),
                    Arguments.of(4, listOf(
                            Box(Vec3(2, 2, 2), Vec3(3, 3, 3)),
                            Box(Vec3(0, 0, 0), Vec3(1, 1, 1))
                    )),
                    Arguments.of(8, listOf(
                            Box(Vec3(2, 2, 2), Vec3(5, 5, 5)),
                            Box(Vec3(0, 0, 0), Vec3(1, 1, 1)),
                            Box(Vec3(1, 1, 1), Vec3(2, 2, 2))
                    )),
                    Arguments.of(16, listOf(
                            Box(Vec3(2, 2, 2), Vec3(5, 5, 5)),
                            Box(Vec3(0, 0, 0), Vec3(1, 1, 1)),
                            Box(Vec3(1, 1, 1), Vec3(2, 2, 2)),
                            Box(Vec3(6, 6, 6), Vec3(10, 10, 10))
                    )),
                    Arguments.of(16, listOf(
                        Box(Vec3(2, 2, 2), Vec3(5, 5, 5)),
                        Box(Vec3(0, 0, 0), Vec3(1, 1, 1)),
                        Box(Vec3(6, 6, 6), Vec3(7, 7, 7)),
                        Box(Vec3(13, 13, 14), Vec3(15, 15, 15))
                    ))
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testParams")
    fun cubicMerger(dim: Int, boxes: List<Box>) {
        val dimensions = Vec3(dim, dim, dim)
        val merger = ConvexMerger()

        testMerger(dimensions, boxes, merger)
    }

//
//    @ParameterizedTest
//    @MethodSource("testParams")
//    fun nonCubicMerger(dim: Int, boxes: List<Box>) {
//        val dimensions = Vec3(dim, dim + 1, dim + 2)
//        val merger = ConvexMerger()
//
//        testMerger(dimensions, boxes, merger)
//    }

    @ParameterizedTest
    @MethodSource("testParams")
    fun aabbMerger(dim: Int, boxes: List<Box>) {
        val dimensions = Vec3(dim, dim, dim)
        val merger = AABBGroupedMerger(ConvexMerger())

        testMerger(dimensions, boxes, merger)
    }

    @ParameterizedTest
    @MethodSource("testParams")
    fun connectedMerger(dim: Int, boxes: List<Box>) {
        val dimensions = Vec3(dim, dim, dim)
        val merger = ConnectedMerger(ConvexMerger())

        testMerger(dimensions, boxes, merger)
    }



    private fun testMerger(dimensions: Vec3, boxes: List<Box>, merger: VoxelMerger) {
        val grid = VoxelGrid(dimensions)
        val voxels = boxes.flatMap { it.voxels() }.distinct()

        voxels.forEach {
            grid[it.x, it.y, it.z] = 3
        }

        val convert = merger.convert(grid)

        val set = convert.flatMap { it.voxels() }.toSet()

        println("Optimization = ${(100 * (voxels.size - convert.size) / voxels.size)}%")

        assertEquals(emptyList<Vec3>(),
                voxels
                .distinct()
                .sortedBy(hashVector())
                .filter { !set.contains(it) })

        assertEquals(
            set.toList() as Any, set.distinct().toList() as Any,
                "Duplicated boxes"
        )
    }

    private fun hashVector(): (Vec3) -> Int? {
        return {
            it.x + it.y * 17 + it.z * 19
        }
    }

}
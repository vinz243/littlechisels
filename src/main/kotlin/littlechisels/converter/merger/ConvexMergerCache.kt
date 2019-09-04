package littlechisels.converter.merger

import littlechisels.converter.math.Vec3
import java.util.*

class ConvexMergerCache {
    private val cache = WeakHashMap<Vec3, ConvexMerger>()
    fun getConvexMerger (size: Vec3): ConvexMerger = cache.computeIfAbsent(size) { ConvexMerger.buildConvexMerger(it) }
}
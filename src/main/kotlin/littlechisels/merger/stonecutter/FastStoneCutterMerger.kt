package littlechisels.merger.stonecutter

import littlechisels.merger.FastBoxInspector
import littlechisels.merger.VoxelMerger

class FastStoneCutterMerger(merger: VoxelMerger) : StoneCutterMerger(merger, FastBoxInspector())
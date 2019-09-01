package littlechisels.converter.merger.stonecutter

import littlechisels.converter.merger.FastBoxInspector
import littlechisels.converter.merger.VoxelMerger

class FastStoneCutterMerger(merger: VoxelMerger) : StoneCutterMerger(merger, FastBoxInspector())
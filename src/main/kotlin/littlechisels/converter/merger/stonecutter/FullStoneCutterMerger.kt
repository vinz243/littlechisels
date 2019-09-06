package littlechisels.converter.merger.stonecutter

import littlechisels.converter.merger.FastBoxInspector
import littlechisels.converter.merger.FullBoxInspector
import littlechisels.converter.merger.VoxelMerger

class FullStoneCutterMerger(merger: VoxelMerger) : StoneCutterMerger(merger, FullBoxInspector())
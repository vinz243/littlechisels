package littlechisels.tiles;

import littlechisels.minecraft.nbt.NBTTagCompound;
import littlechisels.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LittleNBTCompressionTools {

	public static NBTTagList writeTiles(List<LittleTile> tiles) {

		List<LittleTile> groups = new ArrayList<>(tiles);

		NBTTagList list = new NBTTagList();

		while (groups.size() > 0) {
			LittleTile grouping = groups.remove(0);
			NBTTagCompound groupNBT = null;

			for (Iterator iterator2 = groups.iterator(); iterator2.hasNext();) {
				LittleTile littleTile = (LittleTile) iterator2.next();
				if (grouping.canBeNBTGrouped(littleTile)) {
					if (groupNBT == null)
						groupNBT = grouping.startNBTGrouping();
					grouping.groupNBTTile(groupNBT, littleTile);
					iterator2.remove();
				}
			}

			if (groupNBT == null) {
				NBTTagCompound nbt = new NBTTagCompound();
				grouping.saveTile(nbt);
				list.appendTag(nbt);
			} else {
				groupNBT.setBoolean("group", true);
				list.appendTag(groupNBT);
			}
		}

		return list;
	}

}

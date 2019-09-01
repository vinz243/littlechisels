package littlechisels.chisels.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static littlechisels.chisels.entity.LegacyVoxelBlobFactory.FULL_SIZE;

public final class VoxelBlob {
    private final int[] values;

    public VoxelBlob(int[] values) {
        this.values = values;
    }

    public int get(final int x, final int y, final int z) {
        return getBit(x | y << 4 | z << 8);
    }

    private int getBit(final int offset) {
        return values[offset];
    }

    @Override
    public String toString() {
        return "VoxelBlob{" +
                "values=" + Arrays.toString(values) +
                '}';
    }




    public Map<Integer, Integer> getBlockSums() {
        final Map<Integer, Integer> counts = new HashMap<Integer, Integer>();

        int lastType = values[0];
        int firstOfType = 0;

        for (int x = 1; x < FULL_SIZE; x++) {
            final int v = values[x];

            if (lastType != v) {
                final Integer sumx = counts.get(lastType);

                if (sumx == null) {
                    counts.put(lastType, x - firstOfType);
                } else {
                    counts.put(lastType, sumx + (x - firstOfType));
                }

                // new count.
                firstOfType = x;
                lastType = v;
            }
        }

        final Integer sumx = counts.get(lastType);

        if (sumx == null) {
            counts.put(lastType, FULL_SIZE - firstOfType);
        } else {
            counts.put(lastType, sumx + (FULL_SIZE - firstOfType));
        }

        return counts;
    }
}

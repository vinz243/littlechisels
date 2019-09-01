package littlechisels.chisels.entity;

import java.lang.ref.SoftReference;
import java.util.Arrays;

public final class VoxelBlobStateInstance implements Comparable<VoxelBlobStateInstance>
{

	public final int hash;
	public final byte[] voxelBytes;

	private static final int HAS_FLUIDS = 1;
	private static final int HAS_SOLIDS = 2;


	protected SoftReference<VoxelBlob> blob;

	public VoxelBlobStateInstance(
			final byte[] data )
	{
		voxelBytes = data;
		hash = Arrays.hashCode( voxelBytes );
	}

	@Override
	public boolean equals(
			final Object obj )
	{
		return compareTo( (VoxelBlobStateInstance) obj ) == 0;
	}

	@Override
	public int hashCode()
	{
		return hash;
	}

	@Override
	public int compareTo(
			final VoxelBlobStateInstance o )
	{
		if ( o == null )
		{
			return -1;
		}

		int r = Integer.compare( hash, o.hash );

		// length?
		if ( r == 0 )
		{
			r = voxelBytes.length - o.voxelBytes.length;
		}

		// for real then...
		if ( r == 0 )
		{
			for ( int x = 0; x < voxelBytes.length && r == 0; x++ )
			{
				r = voxelBytes[x] - o.voxelBytes[x];
			}
		}

		return r;
	}
}

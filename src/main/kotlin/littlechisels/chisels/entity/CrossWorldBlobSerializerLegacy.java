package littlechisels.chisels.entity;

import littlechisels.minecraft.PacketBuffer;

public class CrossWorldBlobSerializerLegacy extends BlobSerializer
{

	public CrossWorldBlobSerializerLegacy(
			final PacketBuffer toInflate )
	{
		super( toInflate );
	}

	public CrossWorldBlobSerializerLegacy(
			final VoxelBlob toDeflate )
	{
		super( toDeflate );
	}

	@Override
	protected int readStateID(
			final PacketBuffer buffer )
	{
		final String name = buffer.readStringFromBuffer( 512 );

		return buffer.readVarIntFromBuffer();
	}
}

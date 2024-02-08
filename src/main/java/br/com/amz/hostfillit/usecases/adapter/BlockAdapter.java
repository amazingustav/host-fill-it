package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.BlockRepository;
import br.com.amz.hostfillit.usecases.domain.Block;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BlockAdapter {

	private final BlockRepository repository;

	public BlockAdapter(final BlockRepository repository) {
		this.repository = repository;
	}

	public List<Block> findByPropertyId(final UUID propertyId) {
		return repository.findByPropertyIdAndIsActiveTrue(propertyId).stream()
				.map(Block::fromEntity)
				.toList();
	}

	public Block create(final Block data) {
		final var entity = repository.save(data.toEntity());

		return Block.fromEntity(entity);
	}
}

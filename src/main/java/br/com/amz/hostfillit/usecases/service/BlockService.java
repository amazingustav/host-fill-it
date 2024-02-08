package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.BlockAdapter;
import br.com.amz.hostfillit.usecases.domain.Block;
import br.com.amz.hostfillit.usecases.exception.DateOverlapException;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    private final BlockAdapter adapter;

    public BlockService(final BlockAdapter adapter) {
        this.adapter = adapter;
    }

    public Block createBlock(final Block data) {
        return adapter.create(data);
    }

    /**
     * Check if the requested block overlaps with an existing block
     *
     * @param propertyId Property ID
     * @param startDate  Start date of the requested block
     * @param endDate    End date of the requested block
     */
    public void checkBlockOverlap(final UUID propertyId, final LocalDate startDate, final LocalDate endDate) {
        final var existingBlocks = adapter.findByPropertyId(propertyId);

        existingBlocks.forEach((existingBlock) -> {
            if (existingBlock.hasDateOverlap(startDate, endDate)) {
                throw new DateOverlapException("Requested dates overlap with an existing booking");
            }
        });
    }
}

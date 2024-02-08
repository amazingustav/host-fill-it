package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.BookingRepository;
import br.com.amz.hostfillit.usecases.domain.Booking;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BookingAdapter {

	private final BookingRepository repository;

	public BookingAdapter(final BookingRepository repository) {
		this.repository = repository;
	}

	public Booking findById(final UUID bookingId) {
		return repository.findById(bookingId)
				.map(Booking::fromEntity)
				.orElse(null);
	}

	public List<Booking> findByPropertyId(final UUID propertyId) {
		return repository.findByPropertyIdAndIsActiveTrue(propertyId).stream()
				.map(Booking::fromEntity)
				.toList();
	}

	public Booking create(final Booking data) {
		return this.save(data);
	}

	public Booking update(final Booking data) {
		return this.save(data);
	}

	private Booking save(final Booking data) {
		final var entity = repository.save(data.toEntity());
		return Booking.fromEntity(entity);
	}
}

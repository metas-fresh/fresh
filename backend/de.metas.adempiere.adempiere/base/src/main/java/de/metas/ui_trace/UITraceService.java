package de.metas.ui_trace;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UITraceService
{
	@NonNull private final UITraceRepository repository;

	public void create(@NonNull final Collection<UITraceEventCreateRequest> requests)
	{
		repository.create(requests);
	}
}

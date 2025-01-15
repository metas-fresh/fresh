package de.metas.util.web.security;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserAuthTokenFilterConfiguration
{
	private static final Logger logger = LogManager.getLogger(UserAuthTokenFilterConfiguration.class);

	private final CopyOnWriteArrayList<PathMatcher> matchers = new CopyOnWriteArrayList<>();

	public void doNotAuthenticatePathsContaining(@NonNull final String containing)
	{
		addAuthResolutionForPathsContaining(containing, AuthResolution.DO_NOT_AUTHENTICATE);
	}

	public void addAuthResolutionForPathsContaining(@NonNull final String containing, @NonNull final AuthResolution resolution)
	{
		final PathMatcher matcher = PathMatcher.builder()
				.containing(containing)
				.resolution(resolution)
				.build();
		matchers.add(matcher);
		logger.info("Added path authentication resolution matcher: {}", matcher);
	}

	public Optional<AuthResolution> getAuthResolution(@NonNull final HttpServletRequest httpRequest)
	{
		if (matchers.isEmpty())
		{
			return Optional.empty();
		}

		final String path = httpRequest.getServletPath();
		if (path == null)
		{
			return Optional.empty();
		}

		return matchers.stream()
				.filter(matcher -> matcher.isMatching(path))
				.map(PathMatcher::getResolution)
				.findFirst();
	}

	@Value
	@Builder
	private static class PathMatcher
	{
		@NonNull String containing;
		@NonNull AuthResolution resolution;

		public boolean isMatching(final String path) {return path.contains(containing);}
	}

}

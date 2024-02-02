package org.example.events.npmg.util;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class RepositoryUtil {

	public static <T, ID> T findById(JpaRepository<T, ID> repository, ID id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
	}
}
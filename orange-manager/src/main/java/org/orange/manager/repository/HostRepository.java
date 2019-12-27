package org.orange.manager.repository;

import java.util.Optional;

import org.orange.manager.entity.dto.HostDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostDto, Long> {
	Optional<HostDto> findByIp(String ip);
}

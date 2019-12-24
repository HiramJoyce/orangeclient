package org.orange.manager.repository;

import java.util.Optional;

import org.orange.manager.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Integer> {
	Optional<Host> findByIp(String ip);
}

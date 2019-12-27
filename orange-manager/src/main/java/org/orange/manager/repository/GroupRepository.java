package org.orange.manager.repository;

import org.orange.manager.entity.dto.GroupDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hiram 2019年12月27日 11:45
 */
public interface GroupRepository extends JpaRepository<GroupDto, Long> {
}

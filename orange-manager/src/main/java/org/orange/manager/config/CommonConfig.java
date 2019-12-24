package org.orange.manager.config;

import org.orange.manager.entity.Host;
import org.orange.manager.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @author hiram 2019年12月23日 23:07
 */
@Component
class CommonConfig implements CommandLineRunner {

    private final HostRepository hostRepository;

    @Autowired
    public CommonConfig(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("1,192.168.42.197", "2,192.168.42.19").map(x -> x.split(","))
                .forEach(tuple -> hostRepository.save(new Host(Long.valueOf(tuple[0]), tuple[1])));
    }
}
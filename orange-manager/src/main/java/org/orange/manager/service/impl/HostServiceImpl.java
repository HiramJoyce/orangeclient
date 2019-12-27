package org.orange.manager.service.impl;

import org.orange.manager.entity.Host;
import org.orange.manager.entity.dto.HostDto;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.service.CommonService;
import org.orange.manager.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hiram 2019年12月27日 17:28
 */
@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    @Autowired
    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Host> queryHosts() {
        List<HostDto> hostDtos = hostRepository.findAll();
        return CommonService.checkLiveAndConvert(hostDtos);
    }


}

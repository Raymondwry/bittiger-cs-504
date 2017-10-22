package demo.service.impl;

import demo.domain.RunningInformation;
import demo.repository.RunningInformationRepository;
import demo.service.RunningInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RunningInformationServiceImpl implements RunningInformationService {
    RunningInformationRepository repository;

    @Autowired
    public RunningInformationServiceImpl(RunningInformationRepository repository) {
        this.repository = repository;
    }

    public void upload(List<RunningInformation> informations) {
        this.repository.save(informations);
    }

    @Transactional
    public void deleteByRunningId(String runningId) {
        this.repository.deleteByRunningId(runningId);
    }

    public Page<RunningInformation> listAllRunningInformations(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}

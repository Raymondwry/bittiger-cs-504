package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunningInformationService {
    public void upload(List<RunningInformation> runningInformations);

    public void deleteByRunningId(String runningId);

    public Page<RunningInformation> listAllRunningInformations(Pageable pageable);
}

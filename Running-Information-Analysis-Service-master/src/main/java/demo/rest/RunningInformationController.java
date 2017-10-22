package demo.rest;

import demo.domain.RunningInformation;
import demo.service.RunningInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningInformationController {
    RunningInformationService runningInformationService;

    @Autowired
    public RunningInformationController(RunningInformationService runningInformationService) {
        this.runningInformationService = runningInformationService;
    }

    @RequestMapping(value = "/bulk/runningInformations", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInformation> informations) {
        this.runningInformationService.upload(informations);
    }

    @RequestMapping(value = "/runningInformations/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable String runningId) {
        this.runningInformationService.deleteByRunningId(runningId);
    }

    @RequestMapping(value = "/runningInformations", method = RequestMethod.GET)
    public Page<RunningInformation> listAllRunningInformations(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "2") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "healthWarningLevel") String property,
            @RequestParam(name = "desc", required = false, defaultValue = "true") boolean desc) {
        Sort sort = new Sort(desc ? Sort.Direction.DESC : Sort.Direction.ASC, property);
        return this.runningInformationService.listAllRunningInformations(new PageRequest(page, size, sort));
    }
}

package demo.repository;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "runningInformations")
@Transactional
public interface RunningInformationRepository extends JpaRepository<RunningInformation, Long> {
    @RestResource(rel = "delete-by-running-id", description = @Description("Delete by running id"))
    Long deleteByRunningId(@Param("runningId") String runningId);

    @RestResource(rel = "fina-all", description = @Description("Find all order by health level"))
    Page<RunningInformation> findAll(Pageable var1);

    @RestResource(rel = "delete-all", description = @Description("Delete all information"))
    void deleteAll();
}

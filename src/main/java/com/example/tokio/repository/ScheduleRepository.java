package com.example.tokio.repository;

import com.example.tokio.model.ScheduleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleModel, Long> {

    Optional<ScheduleModel> findByUuid(Blob uuid);

    void deleteByUuid(Blob uuid);
}

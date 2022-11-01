
package com.folhasdagua.folhasdagua.repository;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer> {
    List<Flow> findAllBySensor(Sensor sensor);
    @Query("SELECT sum(amount) FROM Flow WHERE sensor = :sensor")
    double sumFlowsAmountBySensor(@Param("sensor") Sensor sensor);
}

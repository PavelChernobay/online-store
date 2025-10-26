package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsOutboxRepository extends JpaRepository<AnalyticsOutbox, Long> {

    List<AnalyticsOutbox> findAllByEventStatus(EventStatus eventStatus);

}

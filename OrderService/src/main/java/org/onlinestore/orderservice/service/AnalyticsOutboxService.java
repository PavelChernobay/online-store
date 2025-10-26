package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.Order;

import java.util.List;

public interface AnalyticsOutboxService {

    void createAnalyticsOutbox(Order order);

    void updateEventStatus(List<AnalyticsOutbox> analyticsOutboxes);

    List<AnalyticsOutbox> getAllAnalyticsOutboxNewStatus();

    void processAnalyticsEvents();

}

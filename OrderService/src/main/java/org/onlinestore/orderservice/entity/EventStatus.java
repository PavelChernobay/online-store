package org.onlinestore.orderservice.entity;

/**
 * Статусы события для AnalyticsOutbox.
 * Используется для отслеживания состояния обработки события в системе.
 */
public enum EventStatus {

    /** Новое событие, еще не обработано */
    NEW,

    /** Событие успешно обработано */
    COMPLETED

}
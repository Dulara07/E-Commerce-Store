package com.freshcart.notificationservice.service;

import com.freshcart.notificationservice.dto.Notification;
import com.freshcart.notificationservice.dto.OrderEventDTO;

public interface NotificationService {

    /**
     * Turns an incoming order event into a Notification and logs it
     * (mock notification - no email/SMS/push provider involved).
     */
    Notification processOrderNotification(OrderEventDTO event);
}

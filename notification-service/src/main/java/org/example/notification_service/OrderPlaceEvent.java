package org.example.notification_service;

import lombok.*;

@NoArgsConstructor
@Data
@Getter
@Setter
public class OrderPlaceEvent {
    private String orderNumber;

    public OrderPlaceEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}

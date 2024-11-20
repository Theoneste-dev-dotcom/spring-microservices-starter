package com.example.order_service.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class OrderPlaceEvent {
    private String orderNumber;


}

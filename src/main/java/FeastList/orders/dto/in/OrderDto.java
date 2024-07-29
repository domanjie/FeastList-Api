package FeastList.orders.dto.in;

import FeastList.orders.domain.OrderItemDetails;
import FeastList.payment.CreditCardPaymentDetails;

import java.util.LinkedHashMap;
import java.util.UUID;

public record OrderDto(LinkedHashMap<UUID, OrderItemDetails> orderItems, String Location, CreditCardPaymentDetails creditCardPaymentDetails)  {
}

package FeastList.orders.dto.in;

import FeastList.payment.CreditCardPaymentDetails;

import java.util.List;

public record OrderDto(String deliveryLocation, List<OrderGroupDto> orderGroupDtos, CreditCardPaymentDetails creditCardPaymentDetails)  {



}

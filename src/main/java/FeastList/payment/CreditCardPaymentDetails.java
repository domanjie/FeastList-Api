package FeastList.payment;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

public record CreditCardPaymentDetails(@CreditCardNumber(message = "invalid credit card number") String ccNumber,
                                       @Digits(integer = 3, fraction = 0, message = "Invalid CVV") String ccCVV,
                                       @Pattern(regexp = "^(0[1-9]|1[0-2])(/)([2-9][0-9])$",
                                               message = "Must be formatted MM/YY") String ccExpiration) {
}
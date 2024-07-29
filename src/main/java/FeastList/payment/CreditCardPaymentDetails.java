package FeastList.payment;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.LuhnCheck;

import java.lang.reflect.Method;

@AllArgsConstructor
@EqualsAndHashCode
public class CreditCardPaymentDetails {
    @CreditCardNumber(message = "invalid credit card number")
    private final String ccNumber;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private final String ccCVV;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private final String ccExpiration;
}

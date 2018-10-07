package org.guoqing.java_functional.function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-23
 */
public class Payment {
    public final CreditCard creditCard;
    public final int amount;

    public Payment(CreditCard creditCard, int amount) {
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public static Payment combine(Payment payment1, Payment payment2) {
        if (payment1.creditCard.equals(payment2.creditCard)) {
            return new Payment(payment1.creditCard, payment1.amount + payment2.amount);
        } else {
            throw new IllegalStateException("Can.t combine payments to different cards");
        }
    }

    public Payment combine(Payment other) {
        if (creditCard.equals(other.creditCard)) {
            return new Payment(creditCard, amount + other.amount);
        } else {
            throw new IllegalStateException("Can.t combine payments to different cards");
        }

    }
}

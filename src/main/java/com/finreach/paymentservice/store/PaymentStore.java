package com.finreach.paymentservice.store;

import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.exceptions.PaymentEntryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentStore {

    private static final Map<String, Payment> PAYMENTS = new ConcurrentHashMap<>();

    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(PAYMENTS.get(id));
    }

    public Payment save(Payment payment) {
        PAYMENTS.put(payment.getId(), payment);
        return PAYMENTS.get(payment.getId());
    }

    public Payment update(Payment payment) throws PaymentEntryNotFoundException {
        if (findById(payment.getId()).isPresent()) {
            PAYMENTS.put(payment.getId(), payment);
        } else {
            throw new PaymentEntryNotFoundException("Payment Entry not found for id :" + payment);
        }
        return PAYMENTS.get(payment.getId());
    }

    public List<Payment> findAll() {
        return new ArrayList<>(PAYMENTS.values());
    }
}

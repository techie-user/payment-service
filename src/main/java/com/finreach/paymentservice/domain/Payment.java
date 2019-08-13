package com.finreach.paymentservice.domain;

import lombok.*;

import java.util.Date;

/**
 * Domain Object holds the payment details to be persisted/retrieved
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Payment {

    private String id;
    private Double amount;
    private String sourceAccountId;
    private String destinationAccountId;
    private Date createdDate;
    private Date updatedDate;
    private String state;

    private Payment(PaymentDTOBuilder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.state = builder.state;
        this.createdDate = builder.createdDate;
        this.updatedDate = builder.lastUpdatedDate;
        this.sourceAccountId = builder.sourceAccountId;
        this.destinationAccountId = builder.destinationAccountId;
    }


    public static class PaymentDTOBuilder {
        private String id;
        private Double amount;
        private String sourceAccountId;
        private String destinationAccountId;
        private Date createdDate;
        private Date lastUpdatedDate;
        private String state;

        public PaymentDTOBuilder(String id) {
            this.id = id;
        }

        public PaymentDTOBuilder withAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public PaymentDTOBuilder withSourceAccountId(String sourceAccountId) {
            this.sourceAccountId = sourceAccountId;
            return this;
        }

        public PaymentDTOBuilder withDestinationAccountId(String destinationAccountId) {
            this.destinationAccountId = destinationAccountId;
            return this;
        }

        public PaymentDTOBuilder withCreatedDt(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public PaymentDTOBuilder withUpdateDt(Date lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public PaymentDTOBuilder withState(String state) {
            this.state = state;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }

    }

    @Override
    public String toString() {
        return "Payment{" + "id='" + id + '\'' + ", amount=" + amount + ", sourceAccountId='" + sourceAccountId + '\'' + ", destinationAccountId='" + destinationAccountId + '\'' + ", createdDt=" + createdDate + ", updatedDt=" + updatedDate + ", state=" + state + '}';
    }

}

package ru.phonemasters.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`Order`")
public class Order {


    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    @Column(
            name = "row_id",
            updatable = false
    )
    private Long rowId;

    @Column
    @NotEmpty
    private Long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private Long phoneNumber;

    @Column
    @NotEmpty
    private String originalComplaint;

    @Column
    @NotNull
    private String realComplaint;

    @Column
    @NotNull
    private BigDecimal originalPrice;

    @Column
    @NotNull
    private BigDecimal agreedPrice;

    @Column
    @NotNull
    private RepairState repairState;

    @Column
    @NotNull
    private PaymentState paymentState;


/*
    @Nullable
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private User assignedUser;
*/


    public enum RepairState {
        ACCEPTED,
        PENDING,
        UNDER_REPAIR,
        REPAIRED
    }

    public enum PaymentState {
        NOT_PAID,
        PARTLY_PAID,
        FULL_PAID
    }
}

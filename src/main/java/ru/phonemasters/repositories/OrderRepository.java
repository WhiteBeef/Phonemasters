package ru.phonemasters.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.phonemasters.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(@NotNull Long id);

    List<Order> findAllByPhoneNumber(@NotNull Long phoneNumber);
}

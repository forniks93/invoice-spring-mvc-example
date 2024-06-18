package pl.fornal.invoice_spring_mvc_example.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}

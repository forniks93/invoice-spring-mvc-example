package pl.fornal.invoice_spring_mvc_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}

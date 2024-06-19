package pl.fornal.invoice_spring_mvc_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.fornal.invoice_spring_mvc_example.controler.dto.Order;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select new pl.fornal.invoice_spring_mvc_example.controler.dto.Order(sum(product.price),client.clientName, invoice.date)" +
            " from Client client" +
            " join client.invoice invoice" +
            " join invoice.product product group by client.clientName")
    List<Order> getAllOrders();

}

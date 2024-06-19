package pl.fornal.invoice_spring_mvc_example.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String StreetName;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", StreetName='" + StreetName + '\'' +
                ", client=" + client.getClientName() +
                '}';
    }
}

package pl.fornal.invoice_spring_mvc_example.controler.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private BigDecimal finalPrice;
    private String clientName;
    private LocalDate date;

    public Order(BigDecimal finalPrice, String clientName, LocalDate date) {
        this.finalPrice = finalPrice;
        this.clientName = clientName;
        this.date = date;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "finalPrice=" + finalPrice +
                ", clientName='" + clientName + '\'' +
                ", date=" + date +
                '}';
    }
}

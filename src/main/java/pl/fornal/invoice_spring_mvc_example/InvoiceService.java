package pl.fornal.invoice_spring_mvc_example;


import java.util.List;

public interface InvoiceService {

    List<Invoice> getAllInvoices();
    void deleteInvoice(Long id);
    void updateInvoice(Invoice newInvoice);
    void saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

}

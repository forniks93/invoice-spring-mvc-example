package pl.fornal.invoice_spring_mvc_example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Client;
import pl.fornal.invoice_spring_mvc_example.repository.entity.ClientRepository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Invoice;
import pl.fornal.invoice_spring_mvc_example.repository.entity.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ClientRepository clientRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;

        init();
    }

    @Transactional
    public void init() {
        Invoice invoice1 = new Invoice();
        invoice1.setDate(LocalDate.of(2024, 6, 18));
        invoice1.setPrice(BigDecimal.valueOf(150));
        invoice1.setName("TEST1");

        Client client1 = new Client();
        client1.setClientName("Piotr");
        invoice1.setClient(client1);
        invoiceRepository.save(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setDate(LocalDate.of(2025, 7, 13));
        invoice2.setPrice(BigDecimal.valueOf(250));
        invoice2.setName("TEST2");

        Client client2 = new Client();
        client2.setClientName("Stefan");
        invoice2.setClient(client2);
        invoiceRepository.save(invoice2);

        System.out.println(invoiceRepository.findAll());
        System.out.println(clientRepository.findAll());
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}

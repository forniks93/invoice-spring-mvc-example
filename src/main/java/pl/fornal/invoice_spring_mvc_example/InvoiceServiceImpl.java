package pl.fornal.invoice_spring_mvc_example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fornal.invoice_spring_mvc_example.repository.AddressRepository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Address;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Client;
import pl.fornal.invoice_spring_mvc_example.repository.ClientRepository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Invoice;
import pl.fornal.invoice_spring_mvc_example.repository.InvoiceRepository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ClientRepository clientRepository, AddressRepository addressRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;

        init();
    }

    @Transactional
    public void init() {

        Address address1 = new Address();
        address1.setStreetName("Brydaka");

        Address address2 = new Address();
        address2.setStreetName("Ropczycka");

        Address address3 = new Address();
        address3.setStreetName("Krakowska");

        //product
        Product productTest1 = new Product();
        productTest1.setPrice(BigDecimal.valueOf(150));
        productTest1.setProductName("Test1");

        Product productTest2 = new Product();
        productTest2.setPrice(BigDecimal.valueOf(250));
        productTest2.setProductName("Test2");

        Invoice invoice1 = new Invoice();
        invoice1.setDate(LocalDate.of(2024, 6, 18));
        invoice1.setProduct(Set.of(productTest1,productTest2));


        Client client1 = new Client();
        client1.setClientName("Piotr");
        invoice1.setClient(client1);
        address1.setClient(client1);
        address2.setClient(client1);

        Invoice invoice2 = new Invoice();
        invoice2.setDate(LocalDate.of(2025, 7, 13));
        invoice2.setProduct(Set.of(productTest2));

        Client client2 = new Client();
        client2.setClientName("Stefan");
        invoice2.setClient(client2);
        address3.setClient(client2);

        invoiceRepository.saveAll(Arrays.asList(invoice1,invoice2));
        addressRepository.saveAll(Arrays.asList(address1,address2,address3));

        System.out.println(invoiceRepository.findAll());
        System.out.println(clientRepository.findAll());
        System.out.println(addressRepository.findAll());

        System.out.println(clientRepository.getAllOrders());
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

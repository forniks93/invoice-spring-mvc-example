package pl.fornal.invoice_spring_mvc_example;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final List<Invoice> invoiceList;
    private final AtomicLong counter = new AtomicLong();

    public InvoiceServiceImpl() {
        this.invoiceList = new ArrayList<>();
        initCounter();
    }

    private void initCounter() {
        long maxId = invoiceList.stream()
                .mapToLong(Invoice::getId)
                .max()
                .orElse(0);
        counter.set(maxId);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceList;
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        // Generowanie ID dla nowej faktury
        invoice.setId(counter.incrementAndGet());
        invoiceList.add(invoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceList.removeIf(invoice -> invoice.getId().equals(id));
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        for (int i = 0; i < invoiceList.size(); i++) {
            if (invoiceList.get(i).getId().equals(invoice.getId())) {
                invoiceList.set(i, invoice);
                return;
            }
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceList.stream()
                .filter(invoice -> invoice.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

package pl.fornal.invoice_spring_mvc_example.controler;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.fornal.invoice_spring_mvc_example.InvoiceService;
import pl.fornal.invoice_spring_mvc_example.controler.dto.Order;
import pl.fornal.invoice_spring_mvc_example.repository.ClientRepository;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Invoice;

import java.util.List;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;
    private ClientRepository clientRepository;

    public InvoiceController(InvoiceService invoiceService, ClientRepository clientRepository) {
        this.invoiceService = invoiceService;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String getAllInvoices(Model model){
        List<Order> allOrders = clientRepository.getAllOrders();
        model.addAttribute("allOrders",allOrders);
        return "gui";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice){
        invoiceService.saveInvoice(invoice);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteInvoice(@RequestParam Long id){
        invoiceService.deleteInvoice(id);
        return "redirect:/";
    }

    @GetMapping("/edit-form")
    public String showEditForm(@RequestParam Long id, Model model) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "edit-form";
    }

    @PostMapping("/edit")
    public String editInvoice(@ModelAttribute Invoice invoice){
        invoiceService.updateInvoice(invoice);
        return "redirect:/";
    }

}

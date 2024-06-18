package pl.fornal.invoice_spring_mvc_example;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.fornal.invoice_spring_mvc_example.repository.entity.Invoice;

import java.util.List;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String getAllInvoices(Model model){
        List<Invoice> allInvoices = invoiceService.getAllInvoices();
        model.addAttribute("allInvoices",allInvoices);
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

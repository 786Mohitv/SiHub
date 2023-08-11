package SI_Recurring_Payment.resource;

import SI_Recurring_Payment.model.Invoice;
import SI_Recurring_Payment.service.InvoiceService;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invoice")
public class InvoiceResource {
    @Autowired
    private InvoiceService service;

    @PostMapping("create")
    public SiHubResponse<?> createInvoice(@RequestBody Invoice invoice){
        return service.createInvoice(invoice);
    }

    @GetMapping("get/{invoiceNo}")
    public SiHubResponse<?> getInvoiceById(@PathVariable("invoiceNo") Long invoiceNO){
        return service.getInvoiceById(invoiceNO);
    }
}

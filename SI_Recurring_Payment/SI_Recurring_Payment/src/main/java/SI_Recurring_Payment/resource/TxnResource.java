package SI_Recurring_Payment.resource;

import SI_Recurring_Payment.model.Transaction;
import SI_Recurring_Payment.service.TxnService;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("txn")
public class TxnResource {
    @Autowired
    TxnService service;

    @PostMapping("create")
    public SiHubResponse<?> createTransaction(@RequestBody Transaction transaction){
        return service.createTxn(transaction);
    }

    @GetMapping("get/{txnId}")
    public SiHubResponse<?> getTxnById(@PathVariable("txnId") Long id ){
        return service.getTransactionById(id);
    }
}

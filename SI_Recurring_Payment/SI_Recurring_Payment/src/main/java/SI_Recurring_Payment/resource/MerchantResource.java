package SI_Recurring_Payment.resource;

import SI_Recurring_Payment.model.Merchant;
import SI_Recurring_Payment.service.MerchantService;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchant")
public class MerchantResource {

    @Autowired
    private MerchantService service;

    @PostMapping("add")
    public SiHubResponse<?> addMerchant(@RequestBody Merchant merchant){
        return service.addMerchant(merchant);
    }
    @PutMapping("update")
    public SiHubResponse<?> updateMerchant(@RequestBody Merchant merchant){
        return service.updateMerchant(merchant);
    }
    @DeleteMapping("delete/{id}")
    public SiHubResponse<?> deleteMerchant(@PathVariable("id") Long id){
        return service.deleteMerchant(id);
    }

    @GetMapping("/{id}")
    public SiHubResponse<?> getMerchantById(@PathVariable("id") Long id){
        return service.getMerchantById(id);
    }

    @GetMapping("/")
    public SiHubResponse<?> getAllMerchants(){
        return service.getAllMerchants();
    }
}

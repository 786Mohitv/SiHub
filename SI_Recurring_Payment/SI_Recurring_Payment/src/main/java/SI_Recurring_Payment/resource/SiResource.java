package SI_Recurring_Payment.resource;

import SI_Recurring_Payment.model.StandingInstruction;
import SI_Recurring_Payment.service.SiService;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import SI_Recurring_Payment.utils.dto.ModifySiReq;
import SI_Recurring_Payment.utils.dto.UpdateSiReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("si")
public class SiResource {
    @Autowired
    private SiService service;

    @PostMapping("create")
    public SiHubResponse<?> createSi(@RequestBody StandingInstruction si){
        return service.createSI(si);
    }

    @DeleteMapping("delete")
    public SiHubResponse<?> deleteSiById(Long id){
        return service.deleteSiById(id);
    }

    @PutMapping("update")
    public SiHubResponse<?> updateSi(@RequestBody UpdateSiReq req){
        return service.updateSI(req);
    }

    @PutMapping("modify")
    public SiHubResponse<?> modifySi(@RequestBody ModifySiReq req){
        return service.modifySi(req);
    }

    @GetMapping("si/{id}")
    public SiHubResponse<?> viewSiById(@PathVariable("id") Long id){
        return service.viewSiById(id);
    }

    @GetMapping("si/list/{mId}")
    public SiHubResponse<?> listSiByMerchantId(@PathVariable("mId") Long id){
        return service.listSiByMerchantId(id);
    }
}

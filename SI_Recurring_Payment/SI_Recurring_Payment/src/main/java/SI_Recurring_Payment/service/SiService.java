package SI_Recurring_Payment.service;

import SI_Recurring_Payment.model.Merchant;
import SI_Recurring_Payment.model.StandingInstruction;
import SI_Recurring_Payment.repository.MerchantRepo;
import SI_Recurring_Payment.repository.SiRepo;
import SI_Recurring_Payment.utils.Response.GenericResponse;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import SI_Recurring_Payment.utils.constants.StatusConstants;
import SI_Recurring_Payment.utils.dto.ModifySiReq;
import SI_Recurring_Payment.utils.dto.UpdateSiReq;
import SI_Recurring_Payment.utils.enums.AmountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SiService {
    @Autowired
    private SiRepo repo;

    @Autowired
    private MerchantRepo mRepo;

    public SiHubResponse<StandingInstruction> createSI(StandingInstruction si){
        if(si.getMerchantId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_001",
                            "Missing/Invalid merchant Id.")
            );
        }
        Optional<Merchant> merchant = mRepo.findById(si.getMerchantId());
        if(merchant.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_001",
                            "No merchant present with merchant Id = "+ si.getMerchantId())
            );
        }


        if(si.getStartDate()==null || (si.getStartDate().compareTo(new Date())<0)){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_002",
                            "Missing/Invalid Start Date.")
            );
        }
        if(si.getEndDate()==null || (si.getEndDate().compareTo(new Date())<=0)){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_003",
                            "Missing/Invalid End Date.")
            );
        }
        if (si.getAmount()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_004",
                            "Missing amount.")
            );
        }

        if(si.getAmountType()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_005",
                            "Missing amount type.")
            );
        }

        if(si.getToken()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_006",
                            "Missing token object.")
            );
        }
        if (si.getToken().getTokenNumber()==null)
        {
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_007",
                            "Missing token number.")
            );
        }
        if (si.getToken().getExpiryMonth()==null)
        {
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_008",
                            "Missing token expiry month.")
            );
        }
        if (si.getToken().getExpiryDay()==null)
        {
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_009",
                            "Missing token expiry day.")
            );
        }
        if (si.getToken().getTokenBin()==null || si.getToken().getTokenBin().isEmpty())
        {
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_010",
                            "Missing/invalid token BIN.")
            );
        }
        if(si.getDescription()==null || si.getDescription().isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_011",
                            "Missing/invalid description.")
            );
        }
        if(si.getSubscriptionId()==null || si.getSubscriptionId().isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_SI_ERROR_012",
                            "Missing/invalid subscription Id.")
            );
        }
        si.getToken().setCreatedOn(new Date());
        si.setCreatedOn(new Date());
        StandingInstruction saved = repo.save(si);
        return new SiHubResponse<>(saved,
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"CREATE_SI_SUCCESS_001",
                        "SI created successfully.")
        );
    }

    public SiHubResponse<StandingInstruction> updateSI(UpdateSiReq req){
        if(req.getSiId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"UPDATE_SI_ERROR_001",
                            "Missing/invalid Si Id.")
            );
        }
        Optional<StandingInstruction> si = repo.findById(req.getSiId());
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"UPDATE_SI_ERROR_002",
                            "No SI present with id = "+ req.getSiId())
            );
        }
        if(req.getStatus()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"UPDATE_SI_ERROR_002",
                            "Missing/invalid value for status.")
            );
        }
        si.get().setStatus(req.getStatus());
        si.get().setModifiedOn(new Date());
        repo.save(si.get());
        return new SiHubResponse<>(si.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"UPDATE_SI_SUCCESS_001",
                        "Si status updated successfully.")
        );
    }

    public SiHubResponse<StandingInstruction> modifySi(ModifySiReq req){
        if(req.getSiId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"MODIFY_SI_ERROR_001",
                            "Invalid/Missing siId.")
            );
        }
        Optional<StandingInstruction> si=repo.findById(req.getSiId());
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"MODIFY_SI_ERROR_002",
                            "No Si is present with siId = "+ req.getSiId())
            );
        }
        if(req.getEndDate()!=null)
            si.get().setEndDate(req.getEndDate());
        si.get().setModifiedOn(new Date());
        repo.save(si.get());
        return new SiHubResponse<>(si.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"MODIFY_SI_SUCCESS_001",
                        "Si modified successfully.")
        );
    }

    public SiHubResponse<StandingInstruction> deleteSiById(Long id){
        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"DELETE_SI_ERROR_001",
                            "Invalid/Missing siId.")
            );
        }

        Optional<StandingInstruction> si=repo.findById(id);
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"DELETE_SI_ERROR_002",
                            "No Si is present with siId = "+ id)
            );
        }

        repo.deleteById(id);
        return new SiHubResponse<>(si.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"DELETE_SI_SUCCESS_001",
                        "Si deleted successfully.")
        );
    }

    public SiHubResponse<StandingInstruction> viewSiById(Long id){
        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"VIEW_SI_ERROR_001",
                            "Invalid/Missing siId.")
            );
        }

        Optional<StandingInstruction> si=repo.findById(id);
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"VIEW_SI_ERROR_002",
                            "No Si is present with siId = "+ id)
            );
        }

        return new SiHubResponse<>(si.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"VIEW_SI_SUCCESS_001",
                        "Si fetched successfully.")
        );
    }

    public SiHubResponse<List<StandingInstruction>> listSiByMerchantId(Long id){
        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"LIST_SI_ERROR_001",
                            "Invalid/Missing Merchant Id.")
            );
        }

        Optional<List<StandingInstruction>> si=repo.getSiByMerchantId(id);
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"VIEW_SI_ERROR_002",
                            "No Si is associated with merchantId = "+ id)
            );
        }

        return new SiHubResponse<>(si.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"VIEW_SI_SUCCESS_001",
                        "Si fetched successfully.")
        );
    }
}

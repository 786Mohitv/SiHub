package SI_Recurring_Payment.service;

import SI_Recurring_Payment.model.Merchant;
import SI_Recurring_Payment.repository.MerchantRepo;
import SI_Recurring_Payment.utils.Response.GenericResponse;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import SI_Recurring_Payment.utils.constants.StatusConstants;
import SI_Recurring_Payment.utils.dto.DeleteMerchantRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepo repo;

    public SiHubResponse<Merchant> addMerchant(Merchant merchant){
        if(merchant.getName()==null || merchant.getName().isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"ADD_MERCHANT_ERROR_001",
                            "Missing/Invalid merchant name.")
            );
        }
        merchant.setCreatedOn(new Date());
        Merchant saved = repo.save(merchant);
        return new SiHubResponse<>(saved,
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"ADD_MERCHANT_SUCCESS_001",
                        "Merchant added successfully.")
        );
    }

    public SiHubResponse<Merchant> updateMerchant(Merchant merchant){
        if(merchant.getId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"UPDATE_MERCHANT_ERROR_001",
                            "Missing/Invalid merchant Id.")
            );
        }
        Optional<Merchant> toUpdate = repo.findById(merchant.getId());
        if(toUpdate.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"UPDATE_MERCHANT_ERROR_002",
                            "No merchant present with Id = "+ merchant.getId())
            );
        }
        if(merchant.getName()!=null && !merchant.getName().isEmpty()){
            toUpdate.get().setName(merchant.getName());
        }
        toUpdate.get().setModifiedOn(new Date());
        repo.save(toUpdate.get());
        return new SiHubResponse<>(toUpdate.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"UPDATE_MERCHANT_SUCCESS_001",
                        "Merchant updated successfully.")
        );
    }

    public SiHubResponse<DeleteMerchantRes> deleteMerchant(Long id){

        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"DELETE_MERCHANT_ERROR_001",
                            "Missing/Invalid merchant Id.")
            );
        }
        Optional<Merchant> toDelete = repo.findById(id);
        if(toDelete.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"DELETE_MERCHANT_ERROR_002",
                            "No merchant present with Id = "+ id)
            );
        }
        repo.deleteById(id);
        return new SiHubResponse<>(new DeleteMerchantRes(toDelete.get().getId(), toDelete.get().getName()),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"DELETE_MERCHANT_SUCCESS_001",
                        "Merchant deleted successfully.")
        );
    }

    public SiHubResponse<Merchant> getMerchantById(Long id){

        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_MERCHANT_ERROR_001",
                            "Missing/Invalid merchant Id.")
            );
        }
        Optional<Merchant> fetched = repo.findById(id);
        if(fetched.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_MERCHANT_ERROR_002",
                            "No merchant present with Id = "+ id)
            );
        }

        return new SiHubResponse<>(fetched.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"GET_MERCHANT_SUCCESS_001",
                        "Merchant fetched successfully.")
        );
    }

    public SiHubResponse<List<Merchant>> getAllMerchants(){
        List<Merchant> list = repo.findAll();
        if(list.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_MERCHANT_ERROR_001",
                            "No merchant present.")
            );
        }
        return new SiHubResponse<>(list,
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"GET_MERCHANT_SUCCESS_001",
                        "Merchants fetched successfully.")
        );
    }
}

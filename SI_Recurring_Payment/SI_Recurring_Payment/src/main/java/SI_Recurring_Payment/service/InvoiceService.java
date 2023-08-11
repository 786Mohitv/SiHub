package SI_Recurring_Payment.service;

import SI_Recurring_Payment.model.Invoice;
import SI_Recurring_Payment.model.Merchant;
import SI_Recurring_Payment.model.StandingInstruction;
import SI_Recurring_Payment.repository.InvoiceRepo;
import SI_Recurring_Payment.repository.MerchantRepo;
import SI_Recurring_Payment.repository.SiRepo;
import SI_Recurring_Payment.utils.Response.GenericResponse;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import SI_Recurring_Payment.utils.constants.StatusConstants;
import SI_Recurring_Payment.utils.enums.InvoiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepo repo;
    @Autowired
    private SiRepo siRepo;
    @Autowired
    private MerchantRepo merchantRepo;
    public SiHubResponse<Invoice> createInvoice(Invoice invoice){
        if(invoice.getMerchantId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_001",
                            "Missing/Invalid merchantId.")
            );
        }

        if(!merchantRepo.existsById(invoice.getMerchantId())){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_002",
                            "No merchant present with merchantId = "+ invoice.getMerchantId())
            );
        }
        if(invoice.getSiId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_003",
                            "Missing/Invalid siId.")
            );
        }
        Optional<StandingInstruction> si = siRepo.findById(invoice.getSiId());
        if(si.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_004",
                            "No Si present with siId = "+ invoice.getSiId())
            );
        }
        if(si.get().getMerchantId()!= invoice.getMerchantId()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_005",
                            "Si does not belong to the specified merchant.")
            );
        }
        if(invoice.getAmount()==null || invoice.getAmount()<0)
        {
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_006",
                            "Missing/Invalid amount.")
            );
        }
        if(invoice.getDebitDate()==null || invoice.getDebitDate().compareTo(new Date())<0){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_007",
                            "Missing/Invalid debitDate.")
            );
        }
        if(invoice.getDueDate()==null || invoice.getDueDate().compareTo(new Date())<0 ||
        invoice.getDueDate().compareTo(invoice.getDebitDate())<0){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_007",
                            "Missing/Invalid debitDate.")
            );
        }

//        if(invoice.getStatus()==null){
//            return new SiHubResponse<>(null,
//                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_008",
//                            "Missing/Invalid Status.")
//            );
//        }
        if(repo.findBySiIdAndMerchantId(invoice.getSiId(),invoice.getMerchantId()).isPresent()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_INVOICE_ERROR_009",
                            "Duplicate entry found for SiId = "+invoice.getSiId()+" in Invoice.")
            );
        }
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setCreateOn(new Date());
        Invoice saved = repo.save(invoice);
        return new SiHubResponse<>(saved,
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"CREATE_INVOICE_SUCCESS_001",
                        "Invoice Created successfully.")
        );
    }

    public SiHubResponse<Invoice> getInvoiceById(Long invoiceNo){
        if(invoiceNo==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_INVOICE_ERROR_001",
                            "Missing/Invalid invoiceNo")
            );
        }
        Optional<Invoice> invoice = repo.findById(invoiceNo);
        if(invoice.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_INVOICE_ERROR_002",
                            "No invoice is present with invoiceNo = "+invoiceNo)
            );
        }
        return new SiHubResponse<>(invoice.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"GET_INVOICE_SUCCESS_001",
                        "Invoice fetched Successfully.")
        );
    }
}

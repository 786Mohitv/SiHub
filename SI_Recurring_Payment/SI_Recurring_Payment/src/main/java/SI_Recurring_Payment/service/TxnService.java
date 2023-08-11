package SI_Recurring_Payment.service;

import SI_Recurring_Payment.model.Invoice;
import SI_Recurring_Payment.model.Transaction;
import SI_Recurring_Payment.repository.InvoiceRepo;
import SI_Recurring_Payment.repository.TxnRepo;
import SI_Recurring_Payment.utils.Response.GenericResponse;
import SI_Recurring_Payment.utils.Response.SiHubResponse;
import SI_Recurring_Payment.utils.constants.StatusConstants;
import SI_Recurring_Payment.utils.enums.InvoiceStatus;
import SI_Recurring_Payment.utils.enums.TxnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TxnService {

    @Autowired
    private InvoiceRepo invoiceRepo;
    @Autowired
    private TxnRepo txnRepo;
    public SiHubResponse<Transaction> createTxn(Transaction txn){

        if(txn.getInvoiceNo()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_001",
                            "Missing/Invalid invoiceNo.")
            );
        }
        Optional<Invoice> invoice = invoiceRepo.findById(txn.getInvoiceNo());
        if(invoice.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_002",
                            "No invoice found with invoiceNo = "+txn.getInvoiceNo())
            );
        }
        if(txn.getSiId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_003",
                            "Missing/Invalid siId.")
            );
        }
        if(txn.getMerchantId()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_004",
                            "Missing/Invalid merchantId.")
            );
        }
        if(txn.getMerchantId()!=invoice.get().getMerchantId() || txn.getSiId()!=invoice.get().getSiId()){
                return new SiHubResponse<>(null,
                        new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_005",
                                "No invoice is present in association with given Merchant and Si.")
                );
        }
        if(txn.getAmount()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_006",
                            "Missing/Invalid amount.")
            );
        }
        if(txn.getAuthStatus()==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"CREATE_TRANSACTION_ERROR_007",
                            "Missing/Invalid authStatus")
            );
        }
        if(txn.getAuthStatus()== TxnStatus.SUCCESS){
            invoice.get().setStatus(InvoiceStatus.PAID);
            invoice.get().setModifiedOn(new Date());
            invoiceRepo.save(invoice.get());
        }
        txn.setCreatedOn(new Date());

        Transaction transaction = txnRepo.save(txn);
        return new SiHubResponse<>(transaction,
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"CREATE_TRANSACTION_SUCCESS_001",
                        "Transaction created Successfully.")
        );
    }

    public SiHubResponse<Transaction> getTransactionById(Long id){
        if(id==null){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_TRANSACTION_ERROR_001",
                            "Missing/Invalid id.")
            );
        }
        Optional<Transaction> transaction = txnRepo.findById(id);
        if(transaction.isEmpty()){
            return new SiHubResponse<>(null,
                    new GenericResponse(StatusConstants.STATUS_FAILURE,"GET_TRANSACTION_ERROR_002",
                            "No transaction is present with id = "+id)
            );
        }
        return new SiHubResponse<>(transaction.get(),
                new GenericResponse(StatusConstants.STATUS_SUCCESS,"GET_TRANSACTION_SUCCESS_001",
                        "Transaction fetched Successfully.")
        );
    }
}

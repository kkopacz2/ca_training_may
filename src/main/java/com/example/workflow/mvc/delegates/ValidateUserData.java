package com.example.workflow.mvc.delegates;

import com.example.workflow.mvc.entity.Client;
import com.example.workflow.mvc.entity.LoanOptions;
import com.example.workflow.mvc.service.ClientService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidateUserData implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ClientService clientService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("delegate: ValidateUserData");
        delegateExecution.setVariable("isDataValid", true);
        return;

//        Long userIdentificationNumber = (Long) delegateExecution.getVariable("userIdentificationNumber");
//        String loanOption = (String) delegateExecution.getVariable("loanOption");
//        String currency = (String) delegateExecution.getVariable("currency");
//        String street = (String) delegateExecution.getVariable("street");
//        Long phoneNumber = (Long) delegateExecution.getVariable("phoneNumber");
//        String declaredIncome = (String) delegateExecution.getVariable("declaredIncome");
////        String firstName = (String) delegateExecution.getVariable("firstName");
////        String lastName = (String) delegateExecution.getVariable("lastName");
//
//
//        if (isLoanOptionInvalid(delegateExecution, loanOption)) return;
//
//        Optional<Client> optClient = clientService.findCientById(userIdentificationNumber);
//
//        if (optClient.isPresent()) {
//            Client client = optClient.get();
//            if (!isCustomerCorrect(client, currency, street, phoneNumber, declaredIncome)) {
//                delegateExecution.setVariable("isDataValid", false);
//                return;
//            }
//            System.out.println("Customer is correct in db.");
//        } else {
//            System.out.println("Customer doesn't exist in db. Assuming it is not ok");
//            delegateExecution.setVariable("isDataValid", false);
//            return;
//        }
//
//        delegateExecution.setVariable("isDataValid", true);
    }

    private boolean isCustomerCorrect(Client client, String currency, String street, Long phoneNumber, String declaredIncome) {
        if (!Objects.equals(client.getCurrency(), currency)) return false;
        if (!Objects.equals(client.getStreet(), street)) return false;
//        if (!Objects.equals(client.getPhoneNumber(), phoneNumber)) return false;
        if (!Objects.equals(client.getDeclaredIncome(), declaredIncome)) return false;
        return true;
    }

    private boolean isLoanOptionInvalid(DelegateExecution delegateExecution, String loanOption) {
        try {
            LoanOptions.valueOf(loanOption);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid loan option: " + loanOption);
            delegateExecution.setVariable("isDataValid", false);
            return true;
        }
        return false;
    }
}

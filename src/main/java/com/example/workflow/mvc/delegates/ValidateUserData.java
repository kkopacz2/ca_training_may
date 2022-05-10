package com.example.workflow.mvc.delegates;

import com.example.workflow.mvc.entity.Client;
import com.example.workflow.mvc.entity.LoanOptions;
import com.example.workflow.mvc.service.ClientService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidateUserData implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ClientService clientService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("delegate: ValidateUserData");

        Long userIdentificationNumber = (Long) delegateExecution.getVariable("userIdentificationNumber");
        String firstName = (String) delegateExecution.getVariable("firstName");
        String lastName = (String) delegateExecution.getVariable("lastName");
        String currency = (String) delegateExecution.getVariable("currency");
        String street = (String) delegateExecution.getVariable("street");
        String phoneNumber = (String) delegateExecution.getVariable("phoneNumber");
        String declaredIncome = (String) delegateExecution.getVariable("declaredIncome");

        Optional<Client> client = clientService.findCientById(userIdentificationNumber);

        String loanOption = (String) delegateExecution.getVariable("loanOption");

        try {
            LoanOptions.valueOf(loanOption);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid loan option: " + loanOption);
            delegateExecution.setVariable("isDataValid", false);
            return;
        }

        delegateExecution.setVariable("isDataValid", true);
    }
}

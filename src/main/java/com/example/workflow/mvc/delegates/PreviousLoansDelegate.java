package com.example.workflow.mvc.delegates;

import com.example.workflow.mvc.entity.Loan;
import com.example.workflow.mvc.service.LoanService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreviousLoansDelegate implements JavaDelegate {
    private static final String CLIENT_ID = "clientId";
    private static final String LOANS = "loans";

    LoanService loanService;

    @Autowired
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int clientId = (int) delegateExecution.getVariable(CLIENT_ID);
        List<Loan> loansByClientId = loanService.getLoansByClientId(clientId);
        delegateExecution.setVariable(LOANS, loansByClientId);
    }

}

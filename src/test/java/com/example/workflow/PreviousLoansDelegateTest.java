package com.example.workflow;

import com.example.workflow.mvc.delegates.PreviousLoansDelegate;
import com.example.workflow.mvc.entity.Loan;
import com.example.workflow.mvc.service.LoanService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class PreviousLoansDelegateTest {

    @Mock
    LoanService loanService;

    @Mock
    private DelegateExecution delegateExecution;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDelegate() throws Exception {

        int clientId = 1;
        Mockito.when(delegateExecution.getVariable(Mockito.anyString())).thenReturn(clientId);

        List<Loan> loansByClientId = Arrays.asList();
        Mockito.when(loanService.getLoansByClientId(Mockito.anyInt())).thenReturn(loansByClientId);

        PreviousLoansDelegate previousLoansDelegate = new PreviousLoansDelegate();
        previousLoansDelegate.setLoanService(this.loanService);
        previousLoansDelegate.execute(this.delegateExecution);

        Mockito.verify(delegateExecution).setVariable("loans", loansByClientId);
    }
}

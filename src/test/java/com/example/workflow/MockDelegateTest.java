package com.example.workflow;

import com.example.workflow.mvc.delegates.MockDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.community.mockito.delegate.DelegateExecutionFake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MockDelegateTest {

    @Mock
    private DelegateExecution delegateExecution;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDelegate() {
        MockDelegate mockDelegate = new MockDelegate();
        mockDelegate.execute(this.delegateExecution);

        Mockito.verify(delegateExecution).setVariable("previousLoanData", "false");

    }


    @Test
    public void testDelegate2() {
        MockDelegate mockDelegate = new MockDelegate();
        DelegateExecutionFake delegateExecutionFake = DelegateExecutionFake.of().withId("123");
        mockDelegate.execute(delegateExecutionFake);

        Assert.assertEquals("false", delegateExecutionFake.getVariable("previousLoanData"));

    }
}

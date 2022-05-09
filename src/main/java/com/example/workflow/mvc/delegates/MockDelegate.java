package com.example.workflow.mvc.delegates;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockDelegate implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // System.out.println(delegateExecution.getVariable("pizza"));
        // delegateExecution.getSuperExecution().getVariable("pizza");
        // delegateExecution.getVariableLocal("pizza");

        System.out.println(runtimeService.getVariable(delegateExecution.getProcessInstanceId(), "pizza"));


    }
}
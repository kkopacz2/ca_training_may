package com.example.workflow;

import com.example.workflow.mvc.delegates.PreviousLoansDelegate;
import com.example.workflow.mvc.entity.Loan;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.camunda.community.mockito.DelegateExpressions;
import org.camunda.community.mockito.ProcessExpressions;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Deployment(resources = {"processes/ShortTermLoanProcess.bpmn"})
public class ShortTermLoanProcessTest extends AbstractProcessEngineRuleTest {
    @Mock
    PreviousLoansDelegate previousLoansDelegate;

    @Test
    public void shouldExecuteHappyPath() {
        // given
        MockitoAnnotations.openMocks(this);

        VariableMap subprcessVariables = Variables.createVariables();
        subprcessVariables.put("rates", "");
        ProcessExpressions.registerCallActivityMock("BITCOIN_RATE_PROCESS")
                .onExecutionSetVariables(subprcessVariables)
                .deploy(processEngine);

        List<Loan> loansByClientId = Arrays.asList();

        DelegateExpressions.registerJavaDelegateMock("previousLoansDelegate")
                .onExecutionSetVariable("loans", loansByClientId);

        VariableMap variables = Variables.createVariables();
        variables.put("clientId", 123);
        variables.put("isApplicable", true);
        variables.put("isConfirmed", true);
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("SHORT_TERM_LOAN_PROCESS", variables);

        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("Activity_0b90t4h");
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        BpmnAwareTests.complete(task);
        assertThat(processInstance).hasPassed("Activity_0b90t4h");

        // timer
        execute(job("Event_00gwx09"));

        task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        BpmnAwareTests.complete(task);
        assertThat(processInstance).hasPassed("Activity_0xdsc78");

        task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        BpmnAwareTests.complete(task);
        assertThat(processInstance).hasPassed("Activity_1qx9wlk");

        assertThat(processInstance).isEnded();
    }
}

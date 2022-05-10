package com.example.workflow;

import com.example.workflow.mvc.delegates.StartProcessListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;

@Deployment(resources = {"processes/diagram_2.bpmn"})
public class PizzaProcessTest extends AbstractProcessEngineRuleTest {

    @Mock
    StartProcessListener startProcessListener;


    @Test
    public void shouldExecuteHappyPath() {
        // given
        MockitoAnnotations.openMocks(this);

        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("PR_01_PIZZA_CHOICE");

/*
        String firstTaskId = taskService()
                .createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult()
                .getId();

        taskService().complete(firstTaskId);*/

        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("SP01_ST1_PIZZA");
    }

}

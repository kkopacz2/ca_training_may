package com.example.workflow;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Deployment(resources = {"processes/diagram_3.bpmn"})
public class SimpleProcessTest extends AbstractProcessEngineRuleTest {


    @Test
    public void shouldExecuteHappyPath() {
        // given
        MockitoAnnotations.openMocks(this);

        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("PR_003_USER_TASK");


        assertThat(processInstance).isStarted()
                .task()
                .hasDefinitionKey("FirstTask");

        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        BpmnAwareTests.complete(task);

        assertThat(processInstance).hasPassed("FirstTask");
        assertThat(processInstance).isEnded();

    }

}

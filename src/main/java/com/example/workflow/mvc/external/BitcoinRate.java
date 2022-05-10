package com.example.workflow.mvc.external;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("bitcoinRateTopic") // create a subscription for this topic name
public class BitcoinRate implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String currency = (String) externalTask.getVariable("currency");
        System.out.println("bitcoin rate execution started");

//        ResponseEntity<String> response = restTemplate.getForEntity("https://api.coingecko.com/api/v3/exchange_rates", String.class);
//        response.getBody().s

        String rate = "1.22";
        VariableMap variables = Variables.createVariables();
        variables.put("bitcoinRate", rate);
        externalTaskService.complete(externalTask, variables);
    }
}

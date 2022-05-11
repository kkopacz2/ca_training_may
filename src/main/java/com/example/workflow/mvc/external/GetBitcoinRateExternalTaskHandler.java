package com.example.workflow.mvc.external;

import com.example.workflow.mvc.entity.Client;
import com.example.workflow.mvc.service.ClientService;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Map;

@Component
@ExternalTaskSubscription("getBitcoinRate")
public class GetBitcoinRateExternalTaskHandler implements ExternalTaskHandler {
    private static final String CLIENT_ID = "clientId";

    @Autowired
    ClientService clientService;

    RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        System.out.println("Started external task");

        Long clientId = ((Integer) externalTask.getVariable(CLIENT_ID)).longValue();
        Client client = clientService.getClientById(clientId);

        double rate = 0;
        try {
            String body = restTemplate.getForEntity("https://api.coingecko.com/api/v3/exchange_rates", String.class).getBody();
            rate = new JSONObject(body).getJSONObject("rates").getJSONObject(client.getCurrency().toLowerCase()).getDouble("value");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        externalTaskService.complete(externalTask, Map.of("rates", rate));
    }
}

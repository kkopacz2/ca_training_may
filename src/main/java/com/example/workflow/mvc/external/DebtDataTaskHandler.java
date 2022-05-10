package com.example.workflow.mvc.external;

import com.example.workflow.mvc.external.model.ExchangeCurrModel;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

@Component
@ExternalTaskSubscription("externalDebtData")
public class DebtDataTaskHandler implements ExternalTaskHandler {


    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {


        String debt = externalTask.getVariable("debtData");

        ResponseEntity<ExchangeCurrModel> currModel = restTemplate.getForEntity("https://api.coingecko.com/api/v3/exchange_rates", ExchangeCurrModel.class);
        ExchangeCurrModel.Rate pln = currModel.getBody().getRates().get("pln");

        BigDecimal debtInBtc = calculatePlnToBTC(debt, pln);
        System.out.println("----------------------------------- " + debtInBtc);
        externalTaskService.setVariables(externalTask, Collections.singletonMap("debtInBtc", debtInBtc));
        externalTaskService.complete(externalTask);
    }

    private BigDecimal calculatePlnToBTC(String debt, ExchangeCurrModel.Rate btc) {
        return new BigDecimal(debt).divide(btc.getValue(), RoundingMode.DOWN);
    }
}

package com.example.workflow.mvc.service;

import com.example.workflow.mvc.entity.Client;
import com.example.workflow.mvc.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RuntimeService runtimeService;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void correlateMessage(String messageId) {
        runtimeService.correlateMessage(messageId);
    }

    public Optional<Client> findCientById(Long clientId) {
        return clientRepository.findById(clientId);
    }
}

/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.getlime.security.powerauth.app.nextstep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.getlime.security.powerauth.app.nextstep.repository.OperationHistoryRepository;
import io.getlime.security.powerauth.app.nextstep.repository.OperationRepository;
import io.getlime.security.powerauth.app.nextstep.repository.model.entity.OperationEntity;
import io.getlime.security.powerauth.app.nextstep.repository.model.entity.OperationHistoryEntity;
import io.getlime.security.powerauth.lib.nextstep.model.entity.AuthStep;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthMethod;
import io.getlime.security.powerauth.lib.nextstep.model.request.CreateOperationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.UpdateOperationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CreateOperationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.UpdateOperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This service handles conversion of operation request/response objects into operation entities.
 * Operation entities are persisted, so that they can be later retrieved from the database.
 *
 * @author Roman Strobl
 */
@Service
public class OperationPersistenceService {

    private ObjectMapper objectMapper;
    private IdGeneratorService idGeneratorService;
    private OperationRepository operationRepository;
    private OperationHistoryRepository operationHistoryRepository;

    @Autowired
    public OperationPersistenceService(IdGeneratorService idGeneratorService, OperationRepository operationRepository,
                                       OperationHistoryRepository operationHistoryRepository) {
        this.objectMapper = new ObjectMapper();
        this.idGeneratorService = idGeneratorService;
        this.operationRepository = operationRepository;
        this.operationHistoryRepository = operationHistoryRepository;
    }

    /**
     * Converts a CreateOperationRequest and CreateOperationResponse into OperationEntity and OperationHistoryEntity.
     * Both entities are persisted to store both the operation and its history in the database.
     *
     * @param request  create request received from the client
     * @param response create response generated for the client
     */
    public void createOperation(CreateOperationRequest request, CreateOperationResponse response) {
        OperationEntity operation = new OperationEntity();
        operation.setOperationName(request.getOperationName());
        operation.setOperationData(request.getOperationData());
        operation.setOperationId(response.getOperationId());
        operation.setResult(response.getResult());
        try {
            // Store display details as serialized JSON string.
            operation.setOperationDisplayDetails(objectMapper.writeValueAsString(request.getDisplayDetails()));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error while serializing operation display details", ex);
        }
        operation.setTimestampCreated(response.getTimestampCreated());
        operation.setTimestampExpires(response.getTimestampExpires());
        operationRepository.save(operation);

        OperationHistoryEntity operationHistory = new OperationHistoryEntity(operation.getOperationId(),
                idGeneratorService.generateOperationHistoryId(operation.getOperationId()));
        operationHistory.setResponseResult(response.getResult());
        operationHistory.setResponseResultDescription(response.getResultDescription());
        try {
            // Params and steps are saved as JSON for now - new entities would be required to store this data.
            // We can add these entities later in case they are needed.
            operationHistory.setRequestParams(objectMapper.writeValueAsString(request.getParams()));
            operationHistory.setResponseSteps(objectMapper.writeValueAsString(response.getSteps()));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error while serializing operation history", ex);
        }
        operationHistory.setResponseTimestampCreated(response.getTimestampCreated());
        operationHistory.setResponseTimestampExpires(response.getTimestampExpires());
        operationHistoryRepository.save(operationHistory);
    }

    /**
     * Converts an UpdateOperationRequest and UpdateOperationResponse into OperationEntity and OperationHistoryEntity.
     * Both entities are persisted to update the status of processed operation as well as update its history.
     *
     * @param request  create request received from the client
     * @param response create response generated for the client
     */
    public void updateOperation(UpdateOperationRequest request, UpdateOperationResponse response) {
        OperationEntity operation = operationRepository.findOne(response.getOperationId());
        operation.setUserId(request.getUserId());
        operation.setResult(response.getResult());
        // operation expiration time matches current response expiration time
        operation.setTimestampExpires(response.getTimestampExpires());
        operationRepository.save(operation);

        OperationHistoryEntity operationHistory = new OperationHistoryEntity(operation.getOperationId(),
                idGeneratorService.generateOperationHistoryId(operation.getOperationId()));
        operationHistory.setRequestAuthMethod(request.getAuthMethod());
        operationHistory.setRequestAuthStepResult(request.getAuthStepResult());
        operationHistory.setResponseResult(response.getResult());
        operationHistory.setResponseResultDescription(response.getResultDescription());
        try {
            // Params and steps are saved as JSON for now - new entities would be required to store this data.
            // We can add these entities later in case they are needed.
            operationHistory.setRequestParams(objectMapper.writeValueAsString(request.getParams()));
            operationHistory.setResponseSteps(objectMapper.writeValueAsString(response.getSteps()));
        } catch (JsonProcessingException e) {
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE,
                    "Error occurred while serializing operation history",
                    e
            );
        }
        operationHistory.setResponseTimestampCreated(response.getTimestampCreated());
        operationHistory.setResponseTimestampExpires(response.getTimestampExpires());
        operationHistoryRepository.save(operationHistory);
    }

    /**
     * Retrieve an OperationEntity for given operationId from database.
     *
     * @param operationId id of an operation
     * @return OperationEntity loaded from database
     */
    public OperationEntity getOperation(String operationId) {
        return operationRepository.findOne(operationId);
    }

    /**
     * Retrieve list of pending operations for given user id and authentication method from database.
     * Parameter authMethod can be null to return all pending operations for given user.
     *
     * @param userId     user id
     * @param authMethod authentication method
     * @return list of operations which match the query
     */
    public List<OperationEntity> getPendingOperations(String userId, AuthMethod authMethod) {
        List<OperationEntity> entities = operationRepository.findPendingOperationsForUser(userId);
        if (authMethod == null) {
            return entities;
        }
        List<OperationEntity> filteredList = new ArrayList<>();
        for (OperationEntity operation : entities) {
            List<AuthMethod> responseAuthMethods = getResponseAuthMethods(operation);
            if (responseAuthMethods.contains(authMethod)) {
                filteredList.add(operation);
            }
        }
        return filteredList;
    }

    /**
     * Gets the list of @{link AuthStep} for an operation. Steps from the current response are returned.
     * In case no history is available, empty list is returned.
     *
     * @param operation operation entity
     * @return list of {@link AuthStep}
     */
    public List<AuthStep> getResponseAuthSteps(OperationEntity operation) {
        List<AuthStep> steps = new ArrayList<>();
        if (operation == null) {
            return steps;
        }
        OperationHistoryEntity currentHistory = operation.getCurrentOperationHistoryEntity();
        if (currentHistory == null) {
            return steps;
        }
        // get steps from the current response
        String responseSteps = operation.getCurrentOperationHistoryEntity().getResponseSteps();
        if (responseSteps == null) {
            return steps;
        }
        try {
            steps.addAll(objectMapper.readValue(responseSteps, new TypeReference<List<AuthStep>>() {
            }));
            return steps;
        } catch (IOException e) {
            // in case of an error empty list is returned
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE,
                    "Error occurred while deserializing response steps",
                    e
            );
        }
        return steps;
    }

    /**
     * Gets the list of @{link AuthMethod} for an operation. Authentication methods from the current response
     * are returned. In case no history is available, empty list is returned.
     *
     * @param operation operation entity
     * @return list of @{link AuthMethod}
     */
    private List<AuthMethod> getResponseAuthMethods(OperationEntity operation) {
        List<AuthMethod> authMethods = new ArrayList<>();
        List<AuthStep> responseSteps = getResponseAuthSteps(operation);
        if (responseSteps.isEmpty()) {
            return authMethods;
        }
        for (AuthStep step : responseSteps) {
            authMethods.add(step.getAuthMethod());
        }
        return authMethods;
    }
}
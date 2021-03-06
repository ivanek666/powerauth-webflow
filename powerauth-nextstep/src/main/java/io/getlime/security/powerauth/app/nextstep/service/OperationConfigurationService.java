/*
 * Copyright 2017 Wultra s.r.o.
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

import io.getlime.security.powerauth.app.nextstep.converter.OperationConfigConverter;
import io.getlime.security.powerauth.app.nextstep.repository.OperationConfigRepository;
import io.getlime.security.powerauth.app.nextstep.repository.model.entity.OperationConfigEntity;
import io.getlime.security.powerauth.lib.nextstep.model.exception.OperationNotConfiguredException;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOperationConfigResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOperationConfigsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service which handles retrieval of operation configuration.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Service
public class OperationConfigurationService {

    private final OperationConfigRepository operationConfigRepository;
    private final OperationConfigConverter configConverter = new OperationConfigConverter();

    /**
     * Service constructor.
     *
     * @param operationConfigRepository Operation configuration repository.
     */
    @Autowired
    public OperationConfigurationService(OperationConfigRepository operationConfigRepository) {
        this.operationConfigRepository = operationConfigRepository;
    }

    /**
     * Get operation configuration.
     * @param operationName Operation name.
     * @return Operation configuration.
     * @throws OperationNotConfiguredException Thrown when operation is not configured.
     */
    public GetOperationConfigResponse getOperationConfig(String operationName) throws OperationNotConfiguredException {
        Optional<OperationConfigEntity> operationConfigOptional = operationConfigRepository.findById(operationName);
        if (!operationConfigOptional.isPresent()) {
            throw new OperationNotConfiguredException("Operation not configured, operation name: " + operationName);
        }
        OperationConfigEntity operationConfig = operationConfigOptional.get();
        return configConverter.fromOperationConfigEntity(operationConfig);
    }

    /**
     * Get all operation configurations.
     * @return All operation configurations.
     */
    public GetOperationConfigsResponse getOperationConfigs() {
        GetOperationConfigsResponse configsResponse = new GetOperationConfigsResponse();
        Iterable<OperationConfigEntity> allConfigs = operationConfigRepository.findAll();
        for (OperationConfigEntity operationConfig: allConfigs) {
            GetOperationConfigResponse config = configConverter.fromOperationConfigEntity(operationConfig);
            configsResponse.addOperationConfig(config);
        }
        return configsResponse;
    }

}

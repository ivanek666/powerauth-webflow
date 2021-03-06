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
package io.getlime.security.powerauth.lib.nextstep.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object used for getting operation configurations.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class GetOperationConfigsResponse {

    private List<GetOperationConfigResponse> operationConfigs = new ArrayList<>();

    /**
     * Get operation configurations.
     * @return Operation configurations.
     */
    public List<GetOperationConfigResponse> getOperationConfigs() {
        return operationConfigs;
    }

    /**
     * Set operation configurations.
     * @param operationConfigs Operation configurations.
     */
    public void setOperationConfigs(List<GetOperationConfigResponse> operationConfigs) {
        this.operationConfigs = operationConfigs;
    }

    /**
     * Add operation configuration.
     * @param operationConfig Add operation configuration.
     */
    public void addOperationConfig(GetOperationConfigResponse operationConfig) {
        operationConfigs.add(operationConfig);
    }
}

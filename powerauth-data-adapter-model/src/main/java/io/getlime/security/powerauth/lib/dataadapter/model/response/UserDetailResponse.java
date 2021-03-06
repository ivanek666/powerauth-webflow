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

package io.getlime.security.powerauth.lib.dataadapter.model.response;

/**
 * Response with user details.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class UserDetailResponse {

    private String id;
    private String givenName;
    private String familyName;

    /**
     * Get user ID.
     * @return User ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set user ID.
     * @param id User ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get user's given name.
     * @return Given (first) name.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Set user's given name.
     * @param givenName Given (first) name.
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Get user's family name.
     * @return User's family (last) name.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Set user's family name.
     * @param familyName User's family (last) name.
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}

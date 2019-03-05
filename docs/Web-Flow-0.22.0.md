# Migration from 0.21.0 to 0.22.0

## Changes Since 0.21.0

### Database Changes

We added `NOT NULL` definitions to database tables to improve data integrity.

DDL update script for Oracle:
```
ALTER TABLE NS_AUTH_METHOD MODIFY AUTH_METHOD NOT NULL;
ALTER TABLE NS_AUTH_METHOD MODIFY ORDER_NUMBER NOT NULL;
ALTER TABLE NS_AUTH_METHOD MODIFY CHECK_AUTH_FAILS NOT NULL;
ALTER TABLE NS_USER_PREFS MODIFY USER_ID NOT NULL;
ALTER TABLE NS_OPERATION_CONFIG MODIFY OPERATION_NAME NOT NULL;
ALTER TABLE NS_OPERATION_CONFIG MODIFY TEMPLATE_VERSION NOT NULL;
ALTER TABLE NS_OPERATION_CONFIG MODIFY TEMPLATE_ID NOT NULL;
ALTER TABLE NS_OPERATION_CONFIG MODIFY MOBILE_TOKEN_MODE NOT NULL;
ALTER TABLE NS_OPERATION MODIFY OPERATION_ID NOT NULL;
ALTER TABLE NS_OPERATION MODIFY OPERATION_NAME NOT NULL;
ALTER TABLE NS_OPERATION MODIFY OPERATION_DATA NOT NULL;
ALTER TABLE NS_OPERATION_HISTORY MODIFY OPERATION_ID NOT NULL;
ALTER TABLE NS_OPERATION_HISTORY MODIFY RESULT_ID NOT NULL;
ALTER TABLE NS_OPERATION_HISTORY MODIFY REQUEST_AUTH_METHOD NOT NULL;
ALTER TABLE NS_OPERATION_HISTORY MODIFY REQUEST_AUTH_STEP_RESULT NOT NULL;
ALTER TABLE NS_OPERATION_HISTORY MODIFY RESPONSE_RESULT NOT NULL;
ALTER TABLE NS_STEP_DEFINITION MODIFY STEP_DEFINITION_ID NOT NULL;
ALTER TABLE NS_STEP_DEFINITION MODIFY OPERATION_NAME NOT NULL;
ALTER TABLE NS_STEP_DEFINITION MODIFY OPERATION_TYPE NOT NULL;
ALTER TABLE NS_STEP_DEFINITION MODIFY RESPONSE_PRIORITY NOT NULL;
ALTER TABLE NS_STEP_DEFINITION MODIFY RESPONSE_RESULT NOT NULL;
ALTER TABLE WF_OPERATION_SESSION MODIFY OPERATION_ID NOT NULL;
ALTER TABLE WF_OPERATION_SESSION MODIFY HTTP_SESSION_ID NOT NULL;
ALTER TABLE WF_OPERATION_SESSION MODIFY RESULT NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY MESSAGE_ID NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY OPERATION_ID NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY USER_ID NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY OPERATION_NAME NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY AUTHORIZATION_CODE NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY SALT NOT NULL;
ALTER TABLE DA_SMS_AUTHORIZATION MODIFY MESSAGE_TEXT NOT NULL;
```

DDL update script for MySQL:
```
ALTER TABLE `ns_auth_method` MODIFY `auth_method` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_auth_method` MODIFY `order_number` INTEGER NOT NULL;
ALTER TABLE `ns_auth_method` MODIFY `check_auth_fails` BOOLEAN NOT NULL;
ALTER TABLE `ns_user_prefs` MODIFY `user_id` VARCHAR(256) NOT NULL;
ALTER TABLE `ns_operation_config` MODIFY `operation_name` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_operation_config` MODIFY `template_version` CHAR NOT NULL;
ALTER TABLE `ns_operation_config` MODIFY `template_id` INTEGER NOT NULL;
ALTER TABLE `ns_operation_config` MODIFY `mobile_token_mode` VARCHAR(256) NOT NULL;
ALTER TABLE `ns_operation` MODIFY `operation_id` VARCHAR(256) NOT NULL;
ALTER TABLE `ns_operation` MODIFY `operation_name` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_operation` MODIFY `operation_data` TEXT NOT NULL;
ALTER TABLE `ns_operation_history` MODIFY `operation_id` VARCHAR(256) NOT NULL;
ALTER TABLE `ns_operation_history` MODIFY `result_id` INTEGER NOT NULL;
ALTER TABLE `ns_operation_history` MODIFY `request_auth_method` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_operation_history` MODIFY `request_auth_step_result` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_operation_history` MODIFY `response_result` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_step_definition` MODIFY `step_definition_id` INTEGER NOT NULL;
ALTER TABLE `ns_step_definition` MODIFY `operation_name` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_step_definition` MODIFY `operation_type` VARCHAR(32) NOT NULL;
ALTER TABLE `ns_step_definition` MODIFY `response_priority` INTEGER NOT NULL;
ALTER TABLE `ns_step_definition` MODIFY `response_result` VARCHAR(32) NOT NULL;
ALTER TABLE `wf_operation_session` MODIFY `operation_id` VARCHAR(256) NOT NULL;
ALTER TABLE `wf_operation_session` MODIFY `http_session_id` VARCHAR(256) NOT NULL;
ALTER TABLE `wf_operation_session` MODIFY `result` VARCHAR(32) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `message_id` VARCHAR(256) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `operation_id` VARCHAR(256) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `user_id` VARCHAR(256) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `operation_name` VARCHAR(32) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `authorization_code` VARCHAR(32) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `salt` VARBINARY(16) NOT NULL;
ALTER TABLE `da_sms_authorization` MODIFY `message_text` TEXT NOT NULL;
```
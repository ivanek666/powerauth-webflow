# NextStep Server RESTful API Reference

PowerAuth Web Flow communicates with the Next Step Server via a REST API to resolve the next step in the authentication process. This chapter defines the REST API implemented by Next Step Server and consumed by the Web Flow Server during authentication. The REST API can be also used by other components.

The Next Step API can list available authentication methods and enable/disable authentication methods per user. Authentication method configuration can be updated - for instance the activation ID of registered user device is set for the Mobile Token authentication method.

The Next Step API is also used by other components involved in the authentication process (e.g. Mobile Token which uses the API indirectly through Web Flow or a party initiating a new operation). The API can be used to query operation details, create an operation, update an operation (move it to the next step) as well as update operation form data and set chosen authentication method as the user progresses in the authentication and authorization process.

Following topics are covered in this chapter:
- [Status codes and error handling](#status-codes-and-error-handling)
- [Service status](#service-status)
- [Authentication methods](#authentication-methods)
  - [List authentication methods](#list-authentication-methods)
  - [List authentication methods enabled for given user](#list-authentication-methods-enabled-for-given-user)
  - [Enable an authentication method for given user](#enable-an-authentication-method-for-given-user)
  - [Disable an authentication method for given user](#disable-an-authentication-method-for-given-user)
- [Operations](#operations)
  - [Operation form data](#operation-formdata)
  - [Create an operation](#create-an-operation)
  - [Update an operation](#update-an-operation)
  - [Operation detail](#operation-detail)
  - [List pending operations](#list-pending-operations)
  - [Update operation form data](#update-operation-formdata)
  - [Set chosen authentication method](#set-chosen-authentication-method)

You can access the generated REST API documentation in deployed Next Step:

```
http[s]://[host]:[port]/powerauth-nextstep/swagger-ui.html
```

## Status codes and error handling

PowerAuth Web Flow Server uses a unified format for error response body, accompanied with an appropriate HTTP status code. Besides the HTTP error codes that application server may return regardless of server application (such as 404 when resource is not found or 503 when server is down).

The list of error status codes:

| Code | Description |
|------|-------------|
| 200  | OK response - REST API call succeeded |
| 500  | Server error - details in the message |

All error responses that are produced by the Next Step Server have following body:

```json
{
    "status": "ERROR",
    "responseObject": {
        "code": "ERROR_CODE",
        "message": "ERROR_MESSAGE_I18N_KEY"
    }
}
```

## Service Status

Get a system status response, with basic information about the running application.

<table>
<tr>
<td>Method</td>
<td><code>GET</code></td>
</tr>
<tr>
<td>Resource URI</td>
<td>/api/service/status</td>
</tr>
</table>

#### **Response**

```json
{
    "status" : "OK",
    "responseObject": {
        "applicationName" : "powerauth-nextstep",
        "applicationDisplayName" : "PowerAuth Next Step Server",
        "applicationEnvironment" : "",
        "version" : "0.20.0",
        "buildTime" : "2017-03-11T09:34:52Z",
        "timestamp" : "2017-03-14T14:54:14Z"
    }  
}
```

- `applicationName` - Application name.
- `applicationDisplayName` - Application display name.
- `applicationEnvironment` - Application environment.
- `version` - Version of Next Step.
- `buildTime` - Timestamp when powerauth-nextstep.war file was created.
- `timestamp` - Response timestamp.

## Authentication methods

### List authentication methods

Lists all authentication methods supported by the server.

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/auth-method/list</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "authMethods": [
      {
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null
      },
      {
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword"
      },
      {
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail"
      },
      {
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken"
      },
      {
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey"
      }
    ]
  }
}
```


### List authentication methods enabled for given user

Lists all authentication methods enabled for given user.

<table>
    <tr>
        <td>Method</td>
    <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method/list</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "roman"
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "roman",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken",
        "config": {
          "activationId": "26c94bf8-f594-4bd8-9c51-93449926b644"
        }
      },
      {
        "userId": "roman",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "config": null
      }
    ]
  }
}
```

### Enable an authentication method for given user

Enables an authentication method for given user and lists all authentication methods enabled for given user after the authentication method has been enabled.

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method</code></td>
    </tr>
</table>

#### Request

The request contains three parameters:
* userId - identification of the user
* authMethod - name of the authentication method
* config - configuration of the authentication method

Currently the only supported configuration is in the POWERAUTH_TOKEN method and it contains activationId, as seen on the sample request below.

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "roman",
    "authMethod": "POWERAUTH_TOKEN",
    "config": {
      "activationId": "26c94bf8-f594-4bd8-9c51-93449926b644"
    }
  }
}
```

For other authentication methods use the following configuration:
```
{
  "requestObject": {
    "userId": "roman",
    "authMethod": "SMS_KEY",
    "config": null
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "roman",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "POWERAUTH_TOKEN",
        "hasUserInterface": true,
        "displayNameKey": "method.powerauthToken",
        "config": {
          "activationId": "26c94bf8-f594-4bd8-9c51-93449926b644"
        }
      },
      {
        "userId": "roman",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "config": null
      }
    ]
  }
}
```


### Disable an authentication method for given user

Disables an authentication method for given user and lists all authentication methods enabled for given user after the authentication method has been disabled.

<table>
    <tr>
        <td>Method</td>
        <td><code>DELETE</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/auth-method</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "userId": "roman",
    "authMethod": "POWERAUTH_TOKEN"
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "userAuthMethods": [
      {
        "userId": "roman",
        "authMethod": "INIT",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USER_ID_ASSIGN",
        "hasUserInterface": false,
        "displayNameKey": null,
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "hasUserInterface": true,
        "displayNameKey": "method.usernamePassword",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "SHOW_OPERATION_DETAIL",
        "hasUserInterface": true,
        "displayNameKey": "method.showOperationDetail",
        "config": null
      },
      {
        "userId": "roman",
        "authMethod": "SMS_KEY",
        "hasUserInterface": true,
        "displayNameKey": "method.smsKey",
        "config": null
      }
    ]
  }
}
```

## Operations

Operation detail contains following data:
* **operationId** - unique ID of the operation, it is either set while creating an operation or it is generated (field is required, value is optional, for generated operation use null as value)
* **operationName** - name of the operations based on the purpose of the operation - different steps are defined for each operation name (required)
* **userId** - ID of user in case the user has been already authorized (optional)
* **result** - result of the last authentication step: CONTINUE, FAILED or DONE (required)
* **timestampCreated** - timestamp when operation was created (required)
* **timestampExpires** - timestamp when operation expires (required)
* **operationData** - arbitrary string which contains data related to this operation, this data is not used during authorization and authentication (required). Since Web Flow version 0.20.0 the [structure of operation data is specified](./Off-line-Signatures-QR-Code.md#operation-data) for easier interpretation of data in Mobile token.
* **steps** - next steps for the operation (required)
* **history** - operation history with completed authentication steps (required)
* **formData** - data displayed by the UI as well as data gathered from the user responses (required, discussed in details below)
* **chosenAuthMethod** - authentication method chosen in current authentication step (optional)
* **remainingAttempts** - remaining attempts for current authentication step (optional)
* **expired** - whether operation was expired at the time of generating response (optional)

Example of complete operation detail:

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "3b1ec892-5267-4afe-9554-0707cad66854",
    "operationName": "authorize_payment",
    "userId": null,
    "result": "CONTINUE",
    "timestampCreated": "2018-06-28T12:04:00+0000",
    "timestampExpires": "2018-06-28T12:04:15+0000",
    "operationData": "A1*A100CZK*Q238400856/0300**D20170629*NUtility Bill Payment - 05/2017",
    "steps": [
      {
        "authMethod": "USER_ID_ASSIGN",
        "params": []
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "params": []
      }
    ],
    "history": [
      {
        "authMethod": "INIT",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValue": null,
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValue": null,
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValue": null,
          "value": "2017-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValue": null,
          "note": "Utility Bill Payment - 05/2017"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {}
    },
    "chosenAuthMethod": null,
    "remainingAttempts": null,
    "expired": false
  }
}
```

### Operation formData

Operations contain formData which is a generic structure for storing input and output data for the operation.

The formData contains following sections:
* **static data** - this data is set when the operation is created (required)
* **dynamic data** - this data is added as the operation progresses (optional)
* **user input** - this data contains gathered inputs from the user as the authentication and authorization progresses (optional)

The **static part of formData** contains data related to the operation known when operation is initiated. For instance in case of a payment, the static data contains information about the payment such as title, amount, currency, target account and message to display to the user in the following structure:

```json
{
  "formData": {
      "title": {
        "id": "operation.title",
        "message": "Confirm Payment"
      },
      "greeting": {
        "id": "operation.greeting",
        "message": "Hello,\nplease confirm following payment:"
      },
      "summary": {
        "id": "operation.summary",
        "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": "Amount",
          "valueFormatType": "AMOUNT",
          "formattedValue": "100.00 CZK",
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": "To Account",
          "valueFormatType": "ACCOUNT",
          "formattedValue": "238400856/0300",
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": "Due Date",
          "valueFormatType": "DATE",
          "formattedValue": "Jun 29, 2017",
          "value": "2017-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": "Note",
          "valueFormatType": "TEXT",
          "formattedValue": "Utility Bill Payment - 05/2017",
          "note": "Utility Bill Payment - 05/2017"
        },
        {
          "type": "HEADING",
          "id": "operation.heading",
          "label": null,
          "valueFormatType": "LOCALIZED_TEXT",
          "formattedValue": "Confirm Payment",
          "value": "operation.title"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": { 
      }
   }
}
```

The usage of static formData:
* **title** - displayed as title on the page with operation details
  * field is required
  * id is the localization key
  * value is the localized text displayed on the page
* **greeting** - displayed as a greeting message in the web application without operation details
  * field is required
  * id is the localization key
  * value is the localized text displayed on the page
* **summary** - displayed as a summary message in the push message sent to mobile device
  * field is required
  * id is the localization key
  * value is the localized text displayed in the push message
* **config** - configures individual form fields (e.g. default values, enabled/disabled state, etc.)
  * field is required, however the config list can be empty
* **banners** - banners which can be displayed above form
* **parameters** - operation parameters which are displayed on the page with operation details
  * field is required, however the parameter list can be empty

Following parameter types are available:
* **AMOUNT** - contains information about amount in this operation including currency
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * amount is displayed next to the label
  * currency is displayed next to the amount
  * currencyId is used internally for localization
* **NOTE** - contains text message related to the operation
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * note is the text message displayed next to the label
* **KEY_VALUE**
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is the displayed localized text
  * valueFormatType specifies the format type
  * formattedValue is the formatted value based on format type
  * value is the text displayed next to the label
* **HEADING**
  * field is optional
  * id is used both for field identification as well as the localization key  
  * label is ignored
  * value contains heading text
  * valueFormatType specifies the format type
  * formattedValue is the formatted heading text based on format type

The **dynamic part of formData** contains additional data which is loaded once the user is authenticated. For instance in case of a payment, the dynamic data can contain choice of bank accounts available for the user with their balances:

```json
{
  "formData": {
    "parameters": [
      {
        "type": "BANK_ACCOUNT_CHOICE",
        "id": "operation.bankAccountChoice",
        "label": "From Your Account",
        "bankAccounts": [
          {
            "number": "12345678/1234",
            "accountId": "CZ4012340000000012345678",
            "name": "Běžný účet v CZK",
            "balance": 24394.52,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "87654321/4321",
            "accountId": "CZ4043210000000087654321",
            "name": "Spořící účet v CZK",
            "balance": 158121.1,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "44444444/1111",
            "accountId": "CZ4011110000000044444444",
            "name": "Spořící účet v EUR",
            "balance": 1.9,
            "currency": "EUR",
            "usableForPayment": false,
            "unusableForPaymentReason": "Low account balance"
          }
        ],
        "enabled": true,
        "defaultValue": "CZ4012340000000012345678"
      }
    ]
  }
}
```
Following parameter types are available:
* **BANK_ACCOUNT_CHOICE**
  * field is optional
  * id is used both for field identification as well as the localization key
  * label is the displayed localized text
  * bankAccounts list is required when BANK_ACCOUNT_CHOICE parameter is specified, however it can be empty

Bank account details:
  * **number** - required, account number in human readable format
  * **name** - required, account name
  * **balance** - required, account balance
  * **currency** - required, account currency
  * **usableForPayment** - required, whether account can be used for payment, in case value is false, unusableForPaymentReason is displayed
  * **unusableForPaymentReason** - optional when usableForPayment = false, otherwise it is required, field explains reason why account is unusable for payment

When dynamic form data is loaded, the formData structure contains following data:

```json
{
  "formData": {
    "dynamicDataLoaded": true
  }
}
```

Dynamic formData may not be loaded because it is required only for specific steps such as operation review. In this case the value is:
```json
{
  "formData": {
    "dynamicDataLoaded": false
  }
}
```

The form fields can be configured in the **config** section as follows:
```json
{
  "formData": {
    "config" : [ {
      "id" : "operation.bankAccountChoice",
      "enabled" : false,
      "defaultValue" : "CZ4043210000000087654321"
    } ]
  }
}
```

Each configuration item contains following fields:
  * **id** - id is used for field identification, same as id used in parameters
  * **enabled** - whether the field is enabled or disabled (default value = true)
  * **defaultValue** - default value of the field (default value = null)

The formData uses userInput JSON structure while **gathering input from the user** as the operation progresses:
```json
{
  "formData": {
    "userInput": {
      "operation.bankAccountChoice": "CZ4012340000000012345678",
      "operation.bankAccountChoice.disabled": "true"
    }
  }
}
```

The userInput part of formData is optional - empty value of userInput is:
```json
{
  "formData": {
    "userInput": {
    }
  }
}
```

Chosen authentication method for current step is stored in formData in case it is available:
```json
{
  "formData": {
    "chosenAuthMethod": "POWERAUTH_TOKEN"
  }
}
```

Null value is used when authentication method has not been chosen for current step:
```json
{
  "formData": {
    "chosenAuthMethod": null
  }
}
```

### Create an operation

Creates an operation in Next Step server.

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationName": "authorize_payment",
    "operationId": null,
    "operationData": "A1*A100CZK*Q238400856/0300**D20170629*NUtility Bill Payment - 05/2017",
    "params": [],
    "formData": {
      "title": {
        "id": "operation.title",
        "value": null
      },
      "greeting": {
        "id": "operation.greeting",
        "value": null
      },
      "summary": {
        "id": "operation.summary",
        "value": null
      },
      "config": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValue": null,
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValue": null,
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValue": null,
          "value": "2017-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValue": null,
          "note": "Utility Bill Payment - 05/2017"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {}
    }
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "51045c7d-1d6b-4de8-9313-cd4af7316c0b",
    "operationName": "authorize_payment",
    "result": "CONTINUE",
    "resultDescription": null,
    "timestampCreated": "2018-06-20T10:17:31+0000",
    "timestampExpires": "2018-06-20T10:22:31+0000",
    "operationData": null,
    "steps": [
      {
        "authMethod": "USER_ID_ASSIGN",
        "params": []
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "params": []
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValue": null,
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValue": null,
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValue": null,
          "value": "2017-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValue": null,
          "note": "Utility Bill Payment - 05/2017"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {}
    },
    "expired": false
  }
}
```

### Update an operation

Updates an operation in Next Step server.

<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "4e02b39b-1ecb-440a-a942-cc27bc07d203",
    "userId": "roman",
    "authMethod": "USERNAME_PASSWORD_AUTH",
    "authStepResult": "CONFIRMED",
    "authStepResultDescription": null,
    "params": []
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "4e02b39b-1ecb-440a-a942-cc27bc07d203",
    "operationName": "authorize_payment",
    "userId": "roman",
    "result": "CONTINUE",
    "resultDescription": null,
    "timestampCreated": "2018-06-28T12:20:28+0000",
    "timestampExpires": "2018-06-28T12:20:43+0000",
    "steps": [
      {
        "authMethod": "SMS_KEY",
        "params": []
      }
    ],
    "expired": false
  }
}
```

### Operation detail

Retrieves detail of an operation in the Next Step server.

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/detail</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject" : {
    "operationId" : "4e02b39b-1ecb-440a-a942-cc27bc07d203"
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status": "OK",
  "responseObject": {
    "operationId": "4e02b39b-1ecb-440a-a942-cc27bc07d203",
    "operationName": "authorize_payment",
    "userId": "roman",
    "result": "CONTINUE",
    "timestampCreated": "2018-06-28T12:20:26+0000",
    "timestampExpires": "2018-06-28T12:20:43+0000",
    "operationData": "A1*A100CZK*Q238400856/0300**D20170629*NUtility Bill Payment - 05/2017",
    "steps": [
      {
        "authMethod": "SMS_KEY",
        "params": []
      }
    ],
    "history": [
      {
        "authMethod": "INIT",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      },
      {
        "authMethod": "USERNAME_PASSWORD_AUTH",
        "authResult": "CONTINUE",
        "requestAuthStepResult": "CONFIRMED"
      }
    ],
    "formData": {
      "title": {
        "id": "operation.title",
        "message": null
      },
      "greeting": {
        "id": "operation.greeting",
        "message": null
      },
      "summary": {
        "id": "operation.summary",
        "message": null
      },
      "config": [],
      "banners": [],
      "parameters": [
        {
          "type": "AMOUNT",
          "id": "operation.amount",
          "label": null,
          "valueFormatType": "AMOUNT",
          "formattedValue": null,
          "amount": 100,
          "currency": "CZK",
          "currencyId": "operation.currency"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.account",
          "label": null,
          "valueFormatType": "ACCOUNT",
          "formattedValue": null,
          "value": "238400856/0300"
        },
        {
          "type": "KEY_VALUE",
          "id": "operation.dueDate",
          "label": null,
          "valueFormatType": "DATE",
          "formattedValue": null,
          "value": "2017-06-29"
        },
        {
          "type": "NOTE",
          "id": "operation.note",
          "label": null,
          "valueFormatType": "TEXT",
          "formattedValue": null,
          "note": "Utility Bill Payment - 05/2017"
        }
      ],
      "dynamicDataLoaded": false,
      "userInput": {
        "operation.bankAccountChoice": "CZ4012340000000012345678",
        "operation.bankAccountChoice.disabled": "true"
      }
    },
    "chosenAuthMethod": "SMS_KEY",
    "remainingAttempts": 3,
    "expired": true
  }
}
```

### List pending operations

Lists pending operation for given user and authentication method.

<table>
    <tr>
        <td>Method</td>
        <td><code>POST</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/user/operation/list</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject" : {
    "userId" : "roman",
    "authMethod" : "POWERAUTH_TOKEN"
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK",
  "responseObject" : [ {
    "operationId" : "07fc3e10-24ed-4938-b172-9c88fb47d6ec",
    "operationName" : "authorize_payment",
    "userId" : "roman",
    "result" : "CONTINUE",
    "timestampCreated" : "2018-03-19T10:10:38Z",
    "timestampExpires" : "2018-03-19T10:15:40Z",
    "operationData" : "A1*A100CZK*Q238400856/0300**D20170629*NUtility Bill Payment - 05/2017",
    "steps" : [ ],
    "history" : [ ],
    "formData" : {
      "title" : {
        "id" : "operation.title",
        "value" : null
      },
      "greeting" : {
        "id" : "operation.greeting",
        "value" : null
      },
      "summary" : {
        "id" : "operation.summary",
        "value" : null
      },
      "config" : [ ],
      "parameters" : [ {
        "type" : "AMOUNT",
        "id" : "operation.amount",
        "label" : null,
        "valueFormatType" : "AMOUNT",
        "formattedValue" : null,
        "amount" : 100,
        "currency" : "CZK",
        "currencyId" : "operation.currency"
      }, {
        "type" : "KEY_VALUE",
        "id" : "operation.account",
        "label" : null,
        "valueFormatType" : "ACCOUNT",
        "formattedValue" : null,
        "value" : "238400856/0300"
      }, {
        "type" : "KEY_VALUE",
        "id" : "operation.dueDate",
        "label" : null,
        "valueFormatType" : "DATE",
        "formattedValue" : null,
        "value" : "2017-06-29"
      }, {
        "type" : "NOTE",
        "id" : "operation.note",
        "label" : null,
        "valueFormatType" : "TEXT",
        "formattedValue" : null,
        "note" : "Utility Bill Payment - 05/2017"
      } ],
      "dynamicDataLoaded" : false,
      "userInput" : {
        "operation.bankAccountChoice" : "CZ4012340000000012345678",
        "operation.bankAccountChoice.disabled" : "true"
      }
    },
    "chosenAuthMethod" : null,
    "remainingAttempts" : null,
    "expired" : false
  } ]
}
```

### Update operation formData

Updates operation formData for given operation. Only the userInput part of formData can be currently updated by the clients.

<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/formData</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "formData": {
    "title": {
      "id": "operation.title",
      "message": "Confirm Payment"
    },
    "greeting": {
      "id": "operation.greeting",
      "message": "Hello,\nplease confirm following payment:"
    },
    "summary": {
      "id": "operation.summary",
      "message": "Hello, please confirm payment 100 CZK to account 238400856/0300."
    },
    "config": [],
    "banners": [],
    "parameters": [
      {
        "type": "AMOUNT",
        "id": "operation.amount",
        "label": "Amount",
        "valueFormatType": "AMOUNT",
        "formattedValue": "100.00 CZK",
        "amount": 100,
        "currency": "CZK",
        "currencyId": "operation.currency"
      },
      {
        "type": "KEY_VALUE",
        "id": "operation.account",
        "label": "To Account",
        "valueFormatType": "ACCOUNT",
        "formattedValue": "238400856/0300",
        "value": "238400856/0300"
      },
      {
        "type": "KEY_VALUE",
        "id": "operation.dueDate",
        "label": "Due Date",
        "valueFormatType": "DATE",
        "formattedValue": "Jun 29, 2017",
        "value": "2017-06-29"
      },
      {
        "type": "NOTE",
        "id": "operation.note",
        "label": "Note",
        "valueFormatType": "TEXT",
        "formattedValue": "Utility Bill Payment - 05/2017",
        "note": "Utility Bill Payment - 05/2017"
      },
      {
        "type": "BANK_ACCOUNT_CHOICE",
        "id": "operation.bankAccountChoice",
        "label": "From Your Account",
        "bankAccounts": [
          {
            "number": "12345678/1234",
            "accountId": "CZ4012340000000012345678",
            "name": "Běžný účet v CZK",
            "balance": 24394.52,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "87654321/4321",
            "accountId": "CZ4043210000000087654321",
            "name": "Spořící účet v CZK",
            "balance": 158121.1,
            "currency": "CZK",
            "usableForPayment": false,
            "unusableForPaymentReason": null
          },
          {
            "number": "44444444/1111",
            "accountId": "CZ4011110000000044444444",
            "name": "Spořící účet v EUR",
            "balance": 1.9,
            "currency": "EUR",
            "usableForPayment": false,
            "unusableForPaymentReason": "Low account balance"
          }
        ],
        "enabled": true,
        "defaultValue": "CZ4012340000000012345678"
      }
    ],
    "dynamicDataLoaded": true,
    "userInput": {
      "operation.bankAccountChoice": "CZ4012340000000012345678"
    }
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK",
  "responseObject" : null
}
```

### Set chosen authentication method

Sets chosen authentication method for current operation step.

<table>
    <tr>
        <td>Method</td>
        <td><code>PUT</code></td>
    </tr>
    <tr>
        <td>Resource URI</td>
        <td><code>/operation/chosenAuthMethod</code></td>
    </tr>
</table>

#### Request

- Headers:
    - `Content-Type: application/json`

```json
{
  "requestObject": {
    "operationId": "3e87f071-2f08-4341-9034-47cb5f8a3fb4",
    "chosenAuthMethod": "POWERAUTH_TOKEN"
  }
}
```

#### Response
- Status Code: `200`
- Headers:
    - `Content-Type: application/json`

```json
{
  "status" : "OK",
  "responseObject" : null
}
```
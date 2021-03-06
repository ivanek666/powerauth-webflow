/*
 * Copyright 2016 Wultra s.r.o.
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
import React from "react";
import {connect} from "react-redux";
// Actions
import {authenticate, cancel, getOperationData, init} from "../actions/smsAuthActions";
// Components
import OperationDetail from "./operationDetail";
import {FormGroup, Panel} from "react-bootstrap";
import Spinner from 'react-tiny-spin';
// i18n
import {FormattedMessage} from "react-intl";

/**
 * Authorization of operation using SMS OTP key.
 */
@connect((store) => {
    return {
        context: store.dispatching.context
    }
})
export default class SMSAuthorization extends React.Component {

    constructor() {
        super();
        this.init = this.init.bind(this);
        this.handleAuthCodeChange = this.handleAuthCodeChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.state = {authCode: ''};
    }

    componentWillMount() {
        this.init();
    }

    init() {
        this.props.dispatch(init());
        this.props.dispatch(getOperationData());
    }

    handleAuthCodeChange(event) {
        this.setState({authCode: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.dispatch(authenticate(this.state.authCode));
    }

    handleCancel(event) {
        event.preventDefault();
        this.props.dispatch(cancel());
    }

    render() {
        return (
            <div id="operation">
                <form onSubmit={this.handleSubmit}>
                    <Panel>
                        <OperationDetail/>
                        <div className="auth-actions">
                            {(this.props.context.message) ? (
                                <FormGroup
                                    className={(this.props.context.error ? "message-error" : "message-information" )}>
                                    <FormattedMessage id={this.props.context.message}/>
                                    {(this.props.context.remainingAttempts > 0) ? (
                                        <div>
                                            <FormattedMessage id="authentication.attemptsRemaining"/> {this.props.context.remainingAttempts}
                                        </div>
                                    ) : (
                                        undefined
                                    )}
                                </FormGroup>
                            ) : (
                                undefined
                            )}
                            <div className="attribute row">
                                <div className="message-information">
                                    <FormattedMessage id="smsAuthorization.authCodeText"/>
                                </div>
                            </div>
                            <div className="attribute row">
                                <div className="col-xs-12">
                                    <input autoFocus className="form-control" type="text" value={this.state.authCode} onChange={this.handleAuthCodeChange}/>
                                </div>
                            </div>
                            <div className="buttons">
                                <div className="attribute row">
                                    <div className="col-xs-12">
                                        <a href="#" onClick={this.handleSubmit} className="btn btn-lg btn-success">
                                            <FormattedMessage id="operation.confirm"/>
                                        </a>
                                    </div>
                                </div>
                                <div className="attribute row">
                                    <div className="col-xs-12">
                                        <a href="#" onClick={this.handleCancel} className="btn btn-lg btn-default">
                                            <FormattedMessage id="operation.cancel"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Panel>
                </form>
                {this.props.context.loading ? <Spinner/> : undefined}
            </div>
        )
    }
}
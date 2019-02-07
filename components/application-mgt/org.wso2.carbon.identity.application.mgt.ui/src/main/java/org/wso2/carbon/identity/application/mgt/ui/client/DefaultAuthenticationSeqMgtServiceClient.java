/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.identity.application.mgt.ui.client;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.model.xsd.DefaultAuthenticationSequence;
import org.wso2.carbon.identity.application.mgt.defaultsequence.stub.IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException;
import org.wso2.carbon.identity.application.mgt.defaultsequence.stub.IdentityDefaultSeqManagementServiceStub;
import org.wso2.carbon.identity.application.mgt.ui.util.ApplicationMgtUIConstants;

import java.rmi.RemoteException;

/**
 * Client for DefaultAuthSeqMgtService.
 */
public class DefaultAuthenticationSeqMgtServiceClient {

    private IdentityDefaultSeqManagementServiceStub stub;
    private static final Log log = LogFactory.getLog(DefaultAuthenticationSeqMgtServiceClient.class);

    public DefaultAuthenticationSeqMgtServiceClient(String cookie, String backendServerURL,
                                                    ConfigurationContext configCtx) throws Exception {

        String serviceURL = backendServerURL + "IdentityDefaultSeqManagementService";
        stub = new IdentityDefaultSeqManagementServiceStub(configCtx, serviceURL);

        ServiceClient client = stub._getServiceClient();
        Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);

        if (log.isDebugEnabled()) {
            log.debug("Invoking service " + serviceURL);
        }
    }

    /**
     * Create default authentication sequence.
     *
     * @param authenticationSequence default authentication sequence
     * @throws IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException
     */
    public void createDefaultAuthenticationSeq(DefaultAuthenticationSequence authenticationSequence)
            throws IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException {

        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating default authentication sequence.");
            }
            stub.createDefaultAuthenticationSeq(authenticationSequence);
        } catch (RemoteException e) {
            log.error("Error occurred when creating default authentication sequence.", e);
            throw new IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException("Server error occurred.");
        }
    }

    /**
     * Check existence of default authentication sequence.
     *
     * @return true if a default authentication sequence exists for the tenant
     * @throws IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException
     */
    public boolean isExistingDefaultAuthenticationSequence()
            throws IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException {

        try {
            if (log.isDebugEnabled()) {
                log.debug("Checking existence of default authentication sequence.");
            }
            return stub.isExistingDefaultAuthenticationSequence(ApplicationMgtUIConstants.DEFAULT_AUTH_SEQ);
        } catch (RemoteException e) {
            log.error("Error while check existence of default authentication sequence.", e);
            throw new IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException("Server error occurred.");
        }
    }

    /**
     * Update default authentication sequence.
     *
     * @throws IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException
     */
    public void updateDefaultAuthenticationSeq(DefaultAuthenticationSequence sequence) throws
            IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException {

        try {
            if (log.isDebugEnabled()) {
                log.debug("Updating default authentication sequence.");
            }
            stub.updateDefaultAuthenticationSeq(ApplicationMgtUIConstants.DEFAULT_AUTH_SEQ, sequence);
        } catch (RemoteException e) {
            log.error("Error occurred when updating default authentication sequence.", e);
            throw new IdentityDefaultSeqManagementServiceDefaultAuthSeqMgtException("Server error occurred.");
        }
    }
}

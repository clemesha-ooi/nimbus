/*
 * Copyright 1999-2008 University of Chicago
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.nimbustools.messaging.gt4_0_elastic.v2008_05_05.rm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nimbustools.api._repr._CreateRequest;
import org.nimbustools.api.brain.ModuleLocator;
import org.nimbustools.api.repr.Caller;
import org.nimbustools.api.repr.CannotTranslateException;
import org.nimbustools.api.repr.CreateRequest;
import org.nimbustools.api.repr.CreateResult;
import org.nimbustools.api.repr.ReprFactory;
import org.nimbustools.api.repr.vm.VM;
import org.nimbustools.api.services.metadata.MetadataServer;
import org.nimbustools.api.services.rm.ManageException;
import org.nimbustools.api.services.rm.Manager;
import org.nimbustools.messaging.gt4_0.common.AddCustomizations;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.DescribeInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.DescribeInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RebootInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RebootInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.ReservationInfoType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RunInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.TerminateInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.TerminateInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.v2008_05_05.ServiceRM;

import java.rmi.RemoteException;

public class ServiceRMImpl implements ServiceRM {

    // -------------------------------------------------------------------------
    // STATIC VARIABLES
    // -------------------------------------------------------------------------

    private static final Log logger =
            LogFactory.getLog(ServiceRMImpl.class.getName());


    // -------------------------------------------------------------------------
    // INSTANCE VARIABLES
    // -------------------------------------------------------------------------

    // nimbus API:
    protected final Manager manager; // the real RM
    protected final ReprFactory repr;
    protected final MetadataServer mdServer;

    // internal API:
    protected final Run run;
    protected final Terminate terminate;
    protected final Reboot reboot;
    protected final Describe describe;
    protected final ContainerInterface container;

    
    // -------------------------------------------------------------------------
    // CONSTRUCTORS
    // -------------------------------------------------------------------------

    public ServiceRMImpl(Run runImpl,
                         Terminate terminateImpl,
                         Reboot rebootImpl,
                         Describe describeImpl,
                         ContainerInterface containerImpl,
                         ModuleLocator locator) throws Exception {

        if (runImpl == null) {
            throw new IllegalArgumentException("runImpl may not be null");
        }
        this.run = runImpl;

        if (terminateImpl == null) {
            throw new IllegalArgumentException("terminateImpl may not be null");
        }
        this.terminate = terminateImpl;

        if (rebootImpl == null) {
            throw new IllegalArgumentException("rebootImpl may not be null");
        }
        this.reboot = rebootImpl;

        if (describeImpl == null) {
            throw new IllegalArgumentException("describeImpl may not be null");
        }
        this.describe = describeImpl;

        if (containerImpl == null) {
            throw new IllegalArgumentException("containerImpl may not be null");
        }
        this.container = containerImpl;

        if (locator == null) {
            throw new IllegalArgumentException("locator may not be null");
        }

        this.manager = locator.getManager();
        this.repr = locator.getReprFactory();
        this.mdServer = locator.getMetadataServer();
    }

    
    // -------------------------------------------------------------------------
    // RUN
    // -------------------------------------------------------------------------

    public ReservationInfoType runInstances(RunInstancesType req)
            throws RemoteException {

        if (req == null) {
            throw new RemoteException("RunInstancesType request is missing");
        }
        
        final Caller caller = this.container.getCaller();

        final CreateResult result;
        try {
            CreateRequest creq =
                    this.run.translateRunInstances(req, caller);
            AddCustomizations.addAll((_CreateRequest)creq,
                                     this.repr, this.mdServer);
            result = this.manager.create(creq, caller);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }

        final String keyname = req.getKeyName();
        try {
            return this.run.translateCreateResult(result, caller, keyname);
        } catch (Exception e) {
            final String err = "Problem translating valid creation " +
                    "result into elastic protocol.  Backout required. " +
                    " Error: " + e.getMessage();
            logger.error(err, e);
            this.terminate.backOutCreateResult(result, caller, this.manager);
            // gets caught by Throwable hook:
            throw new RuntimeException(err, e);
        }
    }


    // -------------------------------------------------------------------------
    // REBOOT
    // -------------------------------------------------------------------------

    public RebootInstancesResponseType rebootInstances(RebootInstancesType req)
            throws RemoteException {

        if (req == null) {
            throw new RemoteException("DescribeInstancesType request is missing");
        }

        final Caller caller = this.container.getCaller();
        final boolean result = this.reboot.reboot(req, caller, this.manager);
        return new RebootInstancesResponseType(result);
    }


    // -------------------------------------------------------------------------
    // DESCRIBE
    // -------------------------------------------------------------------------
    
    public DescribeInstancesResponseType describeInstances(
                        DescribeInstancesType req)
            throws RemoteException {

        if (req == null) {
            throw new RemoteException("DescribeInstancesType request is missing");
        }

        final Caller caller = this.container.getCaller();
        try {
            final String[] instanceIDs = this.describe.findQueryIDs(req);
            final VM[] vms = this.manager.getAllByCaller(caller);
            final String ownerID = this.container.getOwnerID(caller);
            return this.describe.translate(vms, instanceIDs, ownerID);
        } catch (ManageException e) {
            throw new RemoteException(e.getMessage(), e);
        } catch (CannotTranslateException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }


    // -------------------------------------------------------------------------
    // TERMINATE
    // -------------------------------------------------------------------------

    public TerminateInstancesResponseType terminateInstances(
                                        TerminateInstancesType req)
            throws RemoteException {

        if (req == null) {
            throw new RemoteException("TerminateInstancesType request is missing");
        }
        final Caller caller = this.container.getCaller();
        return this.terminate.terminate(req, caller, this.manager);
    }
}

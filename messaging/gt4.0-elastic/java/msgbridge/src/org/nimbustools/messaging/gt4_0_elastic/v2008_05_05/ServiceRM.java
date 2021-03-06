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

package org.nimbustools.messaging.gt4_0_elastic.v2008_05_05;

import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.DescribeInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.DescribeInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RebootInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RebootInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.ReservationInfoType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.RunInstancesType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.TerminateInstancesResponseType;
import org.nimbustools.messaging.gt4_0_elastic.generated.v2008_05_05.TerminateInstancesType;

import java.rmi.RemoteException;

public interface ServiceRM {

    // -------------------------------------------------------------------------
    // RM RELATED
    // -------------------------------------------------------------------------

    public ReservationInfoType runInstances(
                        RunInstancesType runInstancesRequestMsg)
            throws RemoteException;

    public TerminateInstancesResponseType terminateInstances(
                        TerminateInstancesType terminateInstancesRequestMsg)
            throws RemoteException;

    public RebootInstancesResponseType rebootInstances(
                        RebootInstancesType rebootInstancesRequestMsg)
            throws RemoteException;

    public DescribeInstancesResponseType describeInstances(
                        DescribeInstancesType describeInstancesRequestMsg)
            throws RemoteException;
}

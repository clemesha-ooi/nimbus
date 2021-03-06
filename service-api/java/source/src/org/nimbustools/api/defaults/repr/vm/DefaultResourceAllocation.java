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

package org.nimbustools.api.defaults.repr.vm;

import org.nimbustools.api._repr.vm._ResourceAllocation;

public class DefaultResourceAllocation implements _ResourceAllocation {

    // -------------------------------------------------------------------------
    // INSTANCE VARIABLES
    // -------------------------------------------------------------------------

    private String architecture;
    private int indCpuSpeed = -1;
    private int cpuPercentage = -1;
    private int memory;
    private int nodeNumber;


    // -------------------------------------------------------------------------
    // implements org.nimbustools.api.repr.vm.ResourceAllocation
    // -------------------------------------------------------------------------

    public String getArchitecture() {
        return this.architecture;
    }

    public int getIndCpuSpeed() {
        return this.indCpuSpeed;
    }

    public int getCpuPercentage() {
        return this.cpuPercentage;
    }

    public int getMemory() {
        return this.memory;
    }

    public int getNodeNumber() {
        return this.nodeNumber;
    }
    

    // -------------------------------------------------------------------------
    // implements org.nimbustools.api.repr.vm.ResourceAllocation
    // -------------------------------------------------------------------------

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public void setIndCpuSpeed(int indCpuSpeed) {
        this.indCpuSpeed = indCpuSpeed;
    }

    public void setCpuPercentage(int cpuPercentage) {
        this.cpuPercentage = cpuPercentage;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }


    // -------------------------------------------------------------------------
    // DEBUG STRING
    // -------------------------------------------------------------------------

    public String toString() {
        return "DefaultResourceAllocation{" +
                "architecture='" + architecture + '\'' +
                ", indCpuSpeed=" + indCpuSpeed +
                ", cpuPercentage=" + cpuPercentage +
                ", memory=" + memory +
                ", nodeNumber=" + nodeNumber +
                '}';
    }
}

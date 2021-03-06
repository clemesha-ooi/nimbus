/*
 * Copyright 1999-2009 University of Chicago
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
package org.nimbustools.gateway.admin;

public class ParameterProblem extends Exception {
    public ParameterProblem() {
        super();
    }

    public ParameterProblem(String message) {
        super(message);
    }

    public ParameterProblem(String message, Exception e) {
        super(message, e);
    }

    public ParameterProblem(String message, Throwable e) {
        super(message, e);
    }

    public ParameterProblem(Exception e) {
        super("", e);
    }
}

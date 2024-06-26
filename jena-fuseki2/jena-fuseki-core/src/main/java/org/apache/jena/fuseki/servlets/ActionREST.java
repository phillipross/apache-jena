/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.fuseki.servlets;

import static org.apache.jena.atlas.lib.Lib.uppercase;
import static org.apache.jena.fuseki.servlets.ActionExecLib.incCounter;
import static org.apache.jena.riot.web.HttpNames.*;

import org.apache.jena.fuseki.server.CounterName;
import org.apache.jena.sparql.core.DatasetGraph;

/** Common point for operations that are "REST"ish (use GET/PUT etc as operations). */
public abstract class ActionREST extends ActionService
{
    public ActionREST() {
        super();
    }

    @Override
    public void execute(HttpAction action) {
        // Intercept to put counters around calls.
        String method = uppercase(action.getRequestMethod());
        switch(method) {
            case METHOD_GET ->      doGet$(action);
            case METHOD_HEAD ->     doHead$(action);
            case METHOD_POST ->     doPost$(action);
            case METHOD_PATCH ->    doPatch$(action);
            case METHOD_PUT ->      doPut$(action);
            case METHOD_DELETE ->   doDelete$(action);
            case METHOD_OPTIONS ->  doOptions$(action);
            case METHOD_TRACE ->    doTrace$(action);
            default -> ServletOps.errorNotImplemented("Unknown method: "+method);
        }
    }

    /**
     * Decide on the dataset to use for the operation. This can be overridden
     * by specialist subclasses e.g. data access control.
     */
    protected DatasetGraph decideDataset(HttpAction action) {
        return action.getActiveDSG();
    }

    // Counter wrappers

    private final void doGet$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPget);
        try {
            doGet(action);
            incCounter(action.getEndpoint(), CounterName.HTTPgetGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPgetBad);
            throw ex;
        }
    }

    private final void doHead$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPhead);
        try {
            doHead(action);
            incCounter(action.getEndpoint(), CounterName.HTTPheadGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPheadBad);
            throw ex;
        }
    }

    private final void doPost$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPpost);
        try {
            doPost(action);
            incCounter(action.getEndpoint(), CounterName.HTTPpostGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPpostBad);
            throw ex;
        }
    }

    private final void doPatch$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPpatch);
        try {
            doPatch(action);
            incCounter(action.getEndpoint(), CounterName.HTTPpatchGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPpatchBad);
            throw ex;
        }
    }

    private final void doDelete$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPdelete);
        try {
            doDelete(action);
            incCounter(action.getEndpoint(), CounterName.HTTPdeleteGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPdeleteBad);
            throw ex;
        }
    }

    private final void doPut$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPput);
        try {
            doPut(action);
            incCounter(action.getEndpoint(), CounterName.HTTPputGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPputBad);
            throw ex;
        }
    }

    private final void doOptions$(HttpAction action) {
        incCounter(action.getEndpoint(), CounterName.HTTPoptions);
        try {
            doOptions(action);
            incCounter(action.getEndpoint(), CounterName.HTTPoptionsGood);
        } catch ( ActionErrorException ex) {
            incCounter(action.getEndpoint(), CounterName.HTTPoptionsBad);
            throw ex;
        }
    }

    private final void doTrace$(HttpAction action) {
        ServletOps.errorMethodNotAllowed("TRACE");
    }

    protected abstract void doGet(HttpAction action);
    protected abstract void doHead(HttpAction action);
    protected abstract void doPost(HttpAction action);
    protected abstract void doPut(HttpAction action);
    protected abstract void doDelete(HttpAction action);
    protected abstract void doPatch(HttpAction action);
    protected abstract void doOptions(HttpAction action);

    // If not final in ActionBase
    @Override public void process(HttpAction action)      { executeLifecycle(action); }

    @Override public void execAny(String methodName, HttpAction action)     { executeLifecycle(action); }
}

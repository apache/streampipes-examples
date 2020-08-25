package org.apache.streampipes.pe.examples.jvm.python;/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import com.google.gson.JsonObject;
import org.apache.streampipes.commons.exceptions.SpRuntimeException;
import org.apache.streampipes.wrapper.context.EventProcessorRuntimeContext;
import org.apache.streampipes.wrapper.runtime.ExternalEventProcessor;

import java.util.UUID;

import static org.apache.streampipes.pe.examples.jvm.python.Route.post;

public class GreeterPython implements ExternalEventProcessor<GreeterParameters> {
    private String invocationId;

    @Override
    public void onInvocation(GreeterParameters parameters, EventProcessorRuntimeContext runtimeContext) throws SpRuntimeException {

        // use invocationId to keep track of started instances of
        this.invocationId = UUID.randomUUID().toString();

        // construct JSON request from Java -> Python
        JsonObject json = GraphParameterExtractor.toJson(
                GreeterPythonController.PROCESSOR_ID,
                this.invocationId,
                parameters);

        JsonObject staticProperties = new JsonObject();
        staticProperties.addProperty("greeting", parameters.getGreeting());

        json.add("static_properties", staticProperties);

        // send invocation request to python
        post(Route.INVOCATION, json.toString());
    }

    @Override
    public void onDetach() throws SpRuntimeException {
        JsonObject json = new JsonObject();
        json.addProperty("invocation_id", this.invocationId);

        // send detach request to python to stop processor with invocationId
        post(Route.DETACH, this.invocationId);
    }
}

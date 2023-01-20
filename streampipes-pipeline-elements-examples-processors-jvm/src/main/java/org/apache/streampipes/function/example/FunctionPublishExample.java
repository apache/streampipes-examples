/*
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

package org.apache.streampipes.function.example;

import org.apache.streampipes.model.function.FunctionId;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.schema.PropertyScope;
import org.apache.streampipes.sdk.builder.DataStreamBuilder;
import org.apache.streampipes.sdk.helpers.EpProperties;
import org.apache.streampipes.sdk.helpers.Formats;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.Protocols;
import org.apache.streampipes.vocabulary.SO;
import org.apache.streampipes.wrapper.routing.SpOutputCollector;
import org.apache.streampipes.wrapper.standalone.function.FunctionConfig;
import org.apache.streampipes.wrapper.standalone.function.FunctionConfigBuilder;
import org.apache.streampipes.wrapper.standalone.function.FunctionContext;
import org.apache.streampipes.wrapper.standalone.function.StreamPipesFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FunctionPublishExample extends StreamPipesFunction {

  private static final Logger LOG = LoggerFactory.getLogger(FunctionPublishExample.class);

  private static final String FUNCTION_ID = "org.apache.streampipes.example.function.publish";
  private static final String STREAM_APP_ID = "example-output-stream-1";

  private SpOutputCollector outputCollector;

  @Override
  public List<String> requiredStreamIds() {
    return List.of("urn:streampipes.apache.org:eventstream:EtMUkN");
  }

  @Override
  public void onServiceStarted(FunctionContext context) {
    LOG.info("Service started");
    this.outputCollector = context.getOutputCollectors().get(STREAM_APP_ID);
  }

  @Override
  public void onEvent(Event event, String streamId) {
    LOG.info("on event");
    var ev = new Event();
    ev.addField("timestamp", System.currentTimeMillis());
    ev.addField("example-property", "abc");
    this.outputCollector.collect(ev);
  }

  @Override
  public void onServiceStopped() {
    LOG.info("service stopped");
  }

  @Override
  public FunctionConfig getFunctionConfig() {

    return FunctionConfigBuilder
        .create(FunctionId.from(FUNCTION_ID, 1))
        .withOutputStream(DataStreamBuilder.create(STREAM_APP_ID, "My Function Stream", "")
            .property(EpProperties.timestampProperty("timestamp"))
            .property(EpProperties.stringEp(
                Labels.from("my-example-property", "test", "test"),
                "example-property",
                SO.TEXT,
                PropertyScope.MEASUREMENT_PROPERTY))
            .format(Formats.jsonFormat())
            .protocol(Protocols.kafka("localhost", 9094, STREAM_APP_ID))
            .build())
        .build();
  }
}

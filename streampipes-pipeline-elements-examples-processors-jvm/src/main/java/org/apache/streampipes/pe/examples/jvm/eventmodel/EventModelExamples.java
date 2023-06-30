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
package org.apache.streampipes.pe.examples.jvm.eventmodel;

import org.apache.streampipes.commons.exceptions.SpRuntimeException;
import org.apache.streampipes.extensions.api.pe.IStreamPipesDataProcessor;
import org.apache.streampipes.extensions.api.pe.config.IDataProcessorConfiguration;
import org.apache.streampipes.extensions.api.pe.context.EventProcessorRuntimeContext;
import org.apache.streampipes.extensions.api.pe.param.IDataProcessorParameters;
import org.apache.streampipes.extensions.api.pe.routing.SpOutputCollector;
import org.apache.streampipes.model.runtime.Event;

import java.util.List;

public class EventModelExamples implements IStreamPipesDataProcessor {

  /**
   * Example event:
   * { "timestamp" : 12345, "temperature" : 45.6, "deviceId" : "sensor1", "running" : true,
   * "location" : {"latitude" : 34.4, "longitude" : -47}, "lastValues" : [45, 22, 21]}
   */

  private String temperatureSelector;
  private String runningSelector;
  private String deviceIdSelector;
  private String latitudeSelector;
  private String lastValueSelector;

  @Override
  public IDataProcessorConfiguration declareConfig() {
    return null;
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {

  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) throws SpRuntimeException {

    Float temperatureValue = event.getFieldBySelector(temperatureSelector).getAsPrimitive().getAsFloat();
    String deviceId = event.getFieldBySelector(deviceIdSelector).getAsPrimitive().getAsString();

    Double latitude = event.getFieldBySelector(latitudeSelector).getAsPrimitive().getAsDouble();

    Boolean running = event.getFieldBySelector(runningSelector).getAsPrimitive().getAsBoolean();

    List<Integer> lastValues = event.getFieldBySelector(lastValueSelector).getAsList().parseAsSimpleType(Integer.class);


    event.addField("city", "Karlsruhe");
    event.removeFieldBySelector(temperatureSelector);
    event.addField("fahrenheit", 48);

  }

  @Override
  public void onPipelineStopped() {

  }


}

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
package org.apache.streampipes.pe.examples.jvm.requirements;

import org.apache.streampipes.extensions.api.pe.IStreamPipesDataProcessor;
import org.apache.streampipes.extensions.api.pe.config.IDataProcessorConfiguration;
import org.apache.streampipes.extensions.api.pe.context.EventProcessorRuntimeContext;
import org.apache.streampipes.extensions.api.pe.param.IDataProcessorParameters;
import org.apache.streampipes.extensions.api.pe.routing.SpOutputCollector;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.schema.PropertyScope;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.builder.processor.DataProcessorConfiguration;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.OutputStrategies;

public class NestedListRequirementsController implements IStreamPipesDataProcessor {
  
  @Override
  public IDataProcessorConfiguration declareConfig() {
    return DataProcessorConfiguration.create(
        NestedListRequirementsController::new,
        ProcessingElementBuilder.create("org.apache.streampipes.examples.requirements.nestedlist",
                "Nested list mapping example", "")
            .requiredStream(StreamRequirementsBuilder.
                create()
                .requiredPropertyWithUnaryMapping(EpRequirements.nestedListRequirement(EpRequirements.stringReq()),
                    Labels.from("key-value", "Key", ""),
                    PropertyScope.NONE)
                .requiredPropertyWithUnaryMapping(EpRequirements.nestedListRequirement(EpRequirements.integerReq()),
                    Labels.from("count-value", "Count", ""),
                    PropertyScope.NONE)
                .build())
            .outputStrategy(OutputStrategies.keep())
            .build()
    );
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {

  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) {

  }

  @Override
  public void onPipelineStopped() {

  }
}

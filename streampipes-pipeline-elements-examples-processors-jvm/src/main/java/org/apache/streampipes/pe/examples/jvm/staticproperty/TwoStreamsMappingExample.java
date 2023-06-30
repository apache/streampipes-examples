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
package org.apache.streampipes.pe.examples.jvm.staticproperty;

import org.apache.streampipes.extensions.api.pe.IStreamPipesDataProcessor;
import org.apache.streampipes.extensions.api.pe.context.EventProcessorRuntimeContext;
import org.apache.streampipes.extensions.api.pe.param.IDataProcessorParameters;
import org.apache.streampipes.extensions.api.pe.routing.SpOutputCollector;
import org.apache.streampipes.model.DataProcessorType;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.schema.PropertyScope;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.builder.processor.DataProcessorConfiguration;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.OutputStrategies;
import org.apache.streampipes.sdk.utils.Assets;

import java.util.List;

public class TwoStreamsMappingExample  implements IStreamPipesDataProcessor {

  private static final String KEY_STREAM_1 = "stream-1-key";
  private static final String PROPERTIES_STREAM_1 = "stream-1-properties";
  private static final String KEY_STREAM_2 = "stream-2-key";
  private static final String PROPERTIES_STREAM_2 = "stream-2-properties";

  private static final String ID = "org.apache.streampipes.examples.staticproperty.twostreamsmapping";
  @Override
  public DataProcessorConfiguration declareConfig() {
    return DataProcessorConfiguration.create(
        TwoStreamsMappingExample::new,
        ProcessingElementBuilder.create(ID, "Two Streams", "")
            .withAssets(Assets.DOCUMENTATION, Assets.ICON)
            .category(DataProcessorType.ALGORITHM)
            .requiredStream(StreamRequirementsBuilder
                .create()
                .requiredPropertyWithNaryMapping(
                    EpRequirements.numberReq(),
                    Labels.from(PROPERTIES_STREAM_1, "S1 Properties", ""),
                    PropertyScope.NONE)
                .requiredPropertyWithUnaryMapping(EpRequirements.numberReq(),
                    Labels.from(KEY_STREAM_1, "S1 Key", ""), PropertyScope.NONE)
                .build())
            .requiredStream(StreamRequirementsBuilder
                .create()
                .requiredPropertyWithNaryMapping(EpRequirements.numberReq(),
                    Labels.from(PROPERTIES_STREAM_2, "S2 Properties", ""),
                    PropertyScope.NONE)
                .requiredPropertyWithUnaryMapping(EpRequirements.numberReq(),
                    Labels.from(KEY_STREAM_2, "S2 Key", ""),
                    PropertyScope.NONE)
                .build())
            .outputStrategy(OutputStrategies.keep())
            .build()
    );
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {
    var extractor = params.extractor();
    List<String> selProps1 = extractor.mappingPropertyValues(PROPERTIES_STREAM_1);
    List<String> selProps = extractor.mappingPropertyValues(PROPERTIES_STREAM_2);

    String selKey1 = extractor.mappingPropertyValue(KEY_STREAM_1);
    String selKey2 = extractor.mappingPropertyValue(KEY_STREAM_2);

  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) {

  }

  @Override
  public void onPipelineStopped() {

  }

}

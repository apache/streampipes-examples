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

import org.apache.streampipes.model.graph.DataProcessorDescription;
import org.apache.streampipes.model.graph.DataProcessorInvocation;
import org.apache.streampipes.model.schema.PropertyScope;
import org.apache.streampipes.pe.examples.jvm.base.DummyEngine;
import org.apache.streampipes.pe.examples.jvm.base.DummyParameters;
import org.apache.streampipes.sdk.StaticProperties;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.apache.streampipes.sdk.helpers.*;
import org.apache.streampipes.wrapper.standalone.ConfiguredEventProcessor;
import org.apache.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

import java.util.List;

public class CollectionMappingExample extends
        StandaloneEventProcessingDeclarer<DummyParameters> {

  private static final String MAPPING_PROPERTY_ID = "mapping-property";
  private static final String FIELDS_KEY = "fields";

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("org.apache.streampipes.examples.collection.mapping",
            "Collection with mapping properties", "")
            .requiredStream(StreamRequirementsBuilder.
                    create()
                    .requiredProperty(EpRequirements.withMappingPropertyId(MAPPING_PROPERTY_ID, EpRequirements.numberReq()))
                    .build())
            .requiredCollection(Labels.from(FIELDS_KEY, "Field Mappings", ""),
                    StaticProperties.mappingPropertyUnary(Labels.from(MAPPING_PROPERTY_ID, "Field", ""),
                            RequirementsSelector.FIRST_INPUT_STREAM,
                            PropertyScope.NONE))
            .outputStrategy(OutputStrategies.keep())
            .build();
  }

  @Override
  public ConfiguredEventProcessor<DummyParameters> onInvocation(DataProcessorInvocation graph, ProcessingElementParameterExtractor extractor) {

    // Extract the mapping property value
    List<String> selectedMappings = extractor.getUnaryMappingsFromCollection(FIELDS_KEY);

    return new ConfiguredEventProcessor<>(new DummyParameters(graph), DummyEngine::new);
  }
}

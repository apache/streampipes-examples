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
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.schema.PropertyScope;
import org.apache.streampipes.model.staticproperty.FreeTextStaticProperty;
import org.apache.streampipes.model.staticproperty.MappingPropertyUnary;
import org.apache.streampipes.model.staticproperty.OneOfStaticProperty;
import org.apache.streampipes.model.staticproperty.Option;
import org.apache.streampipes.model.staticproperty.StaticPropertyGroup;
import org.apache.streampipes.sdk.StaticProperties;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.builder.processor.DataProcessorConfiguration;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.Options;
import org.apache.streampipes.sdk.helpers.OutputStrategies;
import org.apache.streampipes.sdk.helpers.RequirementsSelector;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionMappingGroupExample implements IStreamPipesDataProcessor {

  private static final String MAPPING_PROPERTY_ID = "mapping-property";
  private static final String FIELDS_KEY = "fields";
  private static final String COMPARATOR_ID = "comparator";

  @Override
  public DataProcessorConfiguration declareConfig() {
    return DataProcessorConfiguration.create(
        CollectionMappingGroupExample::new,
        ProcessingElementBuilder.create("org.apache.streampipes.examples.collection.mapping.group",
                "Collection with mapping properties and additional properties", "")
            .requiredStream(StreamRequirementsBuilder.
                create()
                .requiredProperty(EpRequirements.withMappingPropertyId(MAPPING_PROPERTY_ID, EpRequirements.numberReq()))
                .build())
            .requiredCollection(Labels.from(FIELDS_KEY, "Field Mappings", ""),
                StaticProperties.group(Labels.from("group", "Group", ""), false,
                    StaticProperties.singleValueSelection(Labels.from(COMPARATOR_ID, "Comparator", ""),
                        Options.from("<", "<=", ">", ">=", "==", "*")),
                    StaticProperties.mappingPropertyUnary(Labels.from(MAPPING_PROPERTY_ID, "Field", ""),
                        RequirementsSelector.FIRST_INPUT_STREAM,
                        PropertyScope.NONE),
                    StaticProperties.doubleFreeTextProperty(Labels.from("weight", "Weight", ""))))
            .outputStrategy(OutputStrategies.keep())
            .build()
    );
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {
    var extractor = params.extractor();
    List<StaticPropertyGroup> groupItems = extractor.collectionMembersAsGroup(FIELDS_KEY);

    List<String> fields = groupItems
        .stream()
        .map(group -> (extractor
            .extractGroupMember(MAPPING_PROPERTY_ID, group)
            .as(MappingPropertyUnary.class))
            .getSelectedProperty())
        .collect(Collectors.toList());

    List<Double> weights = groupItems
        .stream()
        .map(group -> (extractor
            .extractGroupMember("weight", group)
            .as(FreeTextStaticProperty.class))
            .getValue())
        .map(Double::parseDouble)
        .collect(Collectors.toList());

    List<String> comparators = groupItems
        .stream()
        .map(group -> (extractor
            .extractGroupMember(COMPARATOR_ID, group)
            .as(OneOfStaticProperty.class))
            .getOptions()
            .stream()
            .filter(Option::isSelected).findFirst().get().getName())
        .collect(Collectors.toList());

  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) {

  }

  @Override
  public void onPipelineStopped() {

  }
}

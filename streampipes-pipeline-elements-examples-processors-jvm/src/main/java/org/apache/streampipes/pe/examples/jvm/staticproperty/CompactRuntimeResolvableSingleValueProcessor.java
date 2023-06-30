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

import org.apache.streampipes.commons.exceptions.SpConfigurationException;
import org.apache.streampipes.extensions.api.extractor.IStaticPropertyExtractor;
import org.apache.streampipes.extensions.api.pe.IStreamPipesDataProcessor;
import org.apache.streampipes.extensions.api.pe.context.EventProcessorRuntimeContext;
import org.apache.streampipes.extensions.api.pe.param.IDataProcessorParameters;
import org.apache.streampipes.extensions.api.pe.routing.SpOutputCollector;
import org.apache.streampipes.extensions.api.runtime.ResolvesContainerProvidedOptions;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.staticproperty.Option;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.builder.processor.DataProcessorConfiguration;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.Options;
import org.apache.streampipes.sdk.helpers.OutputStrategies;
import org.apache.streampipes.sdk.helpers.SupportedFormats;
import org.apache.streampipes.sdk.helpers.SupportedProtocols;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class CompactRuntimeResolvableSingleValueProcessor implements IStreamPipesDataProcessor, ResolvesContainerProvidedOptions {

  private static final String KafkaHost = "kafka-host";
  private static final String KafkaPort = "kafka-port";

  private static final Logger LOG = LoggerFactory.getLogger(CompactRuntimeResolvableSingleValueProcessor.class);

  @Override
  public DataProcessorConfiguration declareConfig() {
    return DataProcessorConfiguration.create(
        CompactRuntimeResolvableSingleValueProcessor::new,
        ProcessingElementBuilder.create("org.apache.streampipes.examples.staticproperty" +
                ".compactruntimeresolvable", "Compact Runtime-resolvable single value example", "")
            .requiredStream(StreamRequirementsBuilder.
                create()
                .requiredProperty(EpRequirements.anyProperty())
                .build())
            .outputStrategy(OutputStrategies.keep())
            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())
            .requiredTextParameter(Labels.from(KafkaHost, "Kafka Host", ""))
            .requiredIntegerParameter(Labels.from(KafkaPort, "Kafka Port", ""))

            // create a single value selection parameter that is resolved at runtime
            .requiredSingleValueSelectionFromContainer(Labels.from("id", "Example Name", "Example " +
                "Description"), Arrays.asList(KafkaHost, KafkaPort))

            .build()
    );
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {
    String selectedSingleValue = params.extractor().selectedSingleValue("id", String.class);
    LOG.info(selectedSingleValue);

  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) {
    MapUtils.debugPrint(System.out, "event", event.getRaw());
    collector.collect(event);
  }

  @Override
  public void onPipelineStopped() {

  }

  @Override
  public List<Option> resolveOptions(String staticPropertyInternalName,
                                     IStaticPropertyExtractor parameterExtractor) throws SpConfigurationException {
    return Options.from("A", "B");
  }
}

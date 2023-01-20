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

import org.apache.streampipes.model.graph.DataProcessorDescription;
import org.apache.streampipes.model.graph.DataProcessorInvocation;
import org.apache.streampipes.pe.examples.jvm.base.DummyParameters;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.OutputStrategies;
import org.apache.streampipes.sdk.helpers.SupportedFormats;
import org.apache.streampipes.sdk.helpers.SupportedProtocols;
import org.apache.streampipes.vocabulary.SO;
import org.apache.streampipes.wrapper.standalone.ConfiguredEventProcessor;
import org.apache.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

public class SimpleStreamRequirementsController extends StandaloneEventProcessingDeclarer<DummyParameters> {

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("org.apache.streampipes.examples.requirements" +
            ".simple", "Simple requirements specification examples", "")
            .requiredStream(StreamRequirementsBuilder.
                    create()
                    .requiredProperty(EpRequirements.numberReq()) // any number
                    .requiredProperty(EpRequirements.doubleReq()) // any field of type double
                    .requiredProperty(EpRequirements.booleanReq()) // any field of type boolean
                    .requiredProperty(EpRequirements.integerReq()) // any field of type integer
                    .requiredProperty(EpRequirements.stringReq()) // any field of type string
                    .requiredProperty(EpRequirements.anyProperty()) // any field allowed (no restriction)
                    .requiredProperty(EpRequirements.timestampReq())  // any timestamp field

                    .requiredProperty(EpRequirements.domainPropertyReq(SO.LATITUDE))
                    .requiredProperty(EpRequirements.domainPropertyReq(SO.LONGITUDE))
                    .build())


            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())
            .outputStrategy(OutputStrategies.keep())

            .build();
  }

  @Override
  public ConfiguredEventProcessor<DummyParameters> onInvocation(DataProcessorInvocation graph, ProcessingElementParameterExtractor extractor) {
    return null;
  }
}

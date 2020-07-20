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

import org.apache.streampipes.model.graph.DataProcessorDescription;
import org.apache.streampipes.model.graph.DataProcessorInvocation;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.apache.streampipes.sdk.helpers.*;
import org.apache.streampipes.vocabulary.SO;
import org.apache.streampipes.wrapper.standalone.ConfiguredExternalEventProcessor;
import org.apache.streampipes.wrapper.standalone.declarer.StandaloneExternalEventProcessingDeclarer;

public class GreeterPythonController extends StandaloneExternalEventProcessingDeclarer<GreeterParameters> {

    private static final String GREETER_KEY = "greeter-key";
    public static final String PROCESSOR_ID = "org.apache.streampipes.examples.python.processor";

    @Override
    public DataProcessorDescription declareModel() {
        return ProcessingElementBuilder.create(PROCESSOR_ID, "Python Greeter", "")
                .requiredStream(StreamRequirementsBuilder.
                        create()
                        .requiredProperty(EpRequirements.anyProperty())
                        .build())

                // create a simple text parameter
                .requiredTextParameter(Labels.withId(GREETER_KEY), "greeting")

                // Append greeting to event stream
                .outputStrategy(OutputStrategies.append(
                        EpProperties.stringEp(Labels.empty(),"greeting", SO.Text)))

                // NOTE: currently one Kafka transport protocol is supported
                .supportedProtocols(SupportedProtocols.kafka())
                .supportedFormats(SupportedFormats.jsonFormat())
                .build();
    }

    @Override
    public ConfiguredExternalEventProcessor<GreeterParameters> onInvocation(DataProcessorInvocation graph, ProcessingElementParameterExtractor extractor) {

        // Extract the greeting value
        String greeting = extractor.singleValueParameter(GREETER_KEY, String.class);

        // now the text parameter would be added to a parameter class (omitted for this example)
        return new ConfiguredExternalEventProcessor<>(new GreeterParameters(graph, greeting), GreeterPython::new);
    }
}

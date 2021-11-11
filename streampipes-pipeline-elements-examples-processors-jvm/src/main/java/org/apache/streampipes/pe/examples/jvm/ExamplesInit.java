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
package org.apache.streampipes.pe.examples.jvm;

import org.apache.streampipes.container.model.SpServiceDefinition;
import org.apache.streampipes.container.model.SpServiceDefinitionBuilder;
import org.apache.streampipes.container.standalone.init.StandaloneModelSubmitter;
import org.apache.streampipes.dataformat.json.JsonDataFormatFactory;
import org.apache.streampipes.messaging.jms.SpJmsProtocolFactory;
import org.apache.streampipes.messaging.kafka.SpKafkaProtocolFactory;
import org.apache.streampipes.pe.examples.jvm.engine.ExampleExternalEngineController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.*;
import org.apache.streampipes.pe.examples.jvm.requirements.NestedListRequirementsController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.*;

public class ExamplesInit extends StandaloneModelSubmitter {

  public static void main(String[] args) {
    new ExamplesInit().init();
  }

  @Override
  public SpServiceDefinition provideServiceDefinition() {
    return SpServiceDefinitionBuilder.create("org.apache.streampipes.processors.examples.jvm",
            "StreamPipes Code Examples",
            "",
            8090)
            .registerMessagingProtocols(new SpKafkaProtocolFactory(), new SpJmsProtocolFactory())
            .registerMessagingFormats(new JsonDataFormatFactory())
            .registerPipelineElement(new TextParameterExampleController())
            .registerPipelineElement(new NumberParameterExampleController())
            .registerPipelineElement(new NumberParameterWithRangeExampleController())
            .registerPipelineElement(new UnaryMappingPropertyExampleController())
            .registerPipelineElement(new NaryMappingPropertyExampleController())
            .registerPipelineElement(new SingleValueSelectionExampleController())
            .registerPipelineElement(new MultiValueSelectionExampleController())
            .registerPipelineElement(new CollectionExampleController())
            .registerPipelineElement(new RuntimeResolvableSingleValue())
            .registerPipelineElement(new RuntimeResolvableAnyStaticPropertyController())
            .registerPipelineElement(new StaticPropertyAlternativesController())
            .registerPipelineElement(new SecretStaticPropertyExampleController())
            .registerPipelineElement(new CodeInputExampleController())
            .registerPipelineElement(new ColorPickerExampleController())
            .registerPipelineElement(new CollectionMappingExample())
            .registerPipelineElement(new NestedListRequirementsController())
            .registerPipelineElement(new TwoStreamsMappingExample())
            .registerPipelineElement(new CompactRuntimeResolvableSingleValueProcessor())

            .registerPipelineElement(new AppendOutputController())
            .registerPipelineElement(new CustomOutputController())
            .registerPipelineElement(new FixedOutputController())
            .registerPipelineElement(new CustomTransformOutputController())
            .registerPipelineElement(new TransformOutputController())
            .registerPipelineElement(new KeepOutputController())
            .registerPipelineElement(new CollectionMappingGroupExample())

            .registerPipelineElement(new ExampleExternalEngineController())
            .build();


  }
}

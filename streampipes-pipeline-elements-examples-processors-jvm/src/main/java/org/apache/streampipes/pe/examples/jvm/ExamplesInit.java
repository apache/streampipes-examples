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


import org.apache.streampipes.dataformat.json.JsonDataFormatFactory;
import org.apache.streampipes.extensions.management.model.SpServiceDefinition;
import org.apache.streampipes.extensions.management.model.SpServiceDefinitionBuilder;
import org.apache.streampipes.messaging.jms.SpJmsProtocolFactory;
import org.apache.streampipes.messaging.kafka.SpKafkaProtocolFactory;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.AppendOutputController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.CustomOutputController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.CustomTransformOutputController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.FixedOutputController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.KeepOutputController;
import org.apache.streampipes.pe.examples.jvm.outputstrategy.TransformOutputController;
import org.apache.streampipes.pe.examples.jvm.requirements.NestedListRequirementsController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.CodeInputExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.CollectionExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.CollectionMappingExample;
import org.apache.streampipes.pe.examples.jvm.staticproperty.CollectionMappingGroupExample;
import org.apache.streampipes.pe.examples.jvm.staticproperty.ColorPickerExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.CompactRuntimeResolvableSingleValueProcessor;
import org.apache.streampipes.pe.examples.jvm.staticproperty.MultiValueSelectionExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.NaryMappingPropertyExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.NumberParameterExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.NumberParameterWithRangeExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.RuntimeResolvableAnyStaticPropertyController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.RuntimeResolvableSingleValue;
import org.apache.streampipes.pe.examples.jvm.staticproperty.SecretStaticPropertyExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.SingleValueSelectionExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.StaticPropertyAlternativesController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.TextParameterExampleController;
import org.apache.streampipes.pe.examples.jvm.staticproperty.TreeInputSink;
import org.apache.streampipes.pe.examples.jvm.staticproperty.TwoStreamsMappingExample;
import org.apache.streampipes.pe.examples.jvm.staticproperty.UnaryMappingPropertyExampleController;
import org.apache.streampipes.service.extensions.ExtensionsModelSubmitter;
import org.apache.streampipes.tutorial.VehicleStream;


public class ExamplesInit extends ExtensionsModelSubmitter {

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
        .registerPipelineElement(new TreeInputSink())

        .registerPipelineElement(new AppendOutputController())
        .registerPipelineElement(new CustomOutputController())
        .registerPipelineElement(new FixedOutputController())
        .registerPipelineElement(new CustomTransformOutputController())
        .registerPipelineElement(new TransformOutputController())
        .registerPipelineElement(new KeepOutputController())
        .registerPipelineElement(new CollectionMappingGroupExample())

        .registerPipelineElement(new VehicleStream())
        .build();


  }
}

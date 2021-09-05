package org.apache.streampipes.pe.examples.jvm.staticproperty;

import org.apache.streampipes.model.graph.DataProcessorDescription;
import org.apache.streampipes.model.graph.DataProcessorInvocation;
import org.apache.streampipes.pe.examples.jvm.base.DummyEngine;
import org.apache.streampipes.pe.examples.jvm.base.DummyParameters;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.apache.streampipes.sdk.helpers.*;
import org.apache.streampipes.wrapper.standalone.ConfiguredEventProcessor;
import org.apache.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

public class CodeInputExampleController extends StandaloneEventProcessingDeclarer<DummyParameters> {

  private static final String CODE_KEY = "code-key";
  private static final String CODE_PYTHON_KEY = "code-python-key";
  private static final String CODE_EMPTY_KEY = "code-empty-key";

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("org.apache.streampipes.examples.staticproperty" +
            ".codeinput", "Code Input Example", "")
            .requiredStream(StreamRequirementsBuilder.
                    create()
                    .requiredProperty(EpRequirements.anyProperty())
                    .build())
            .outputStrategy(OutputStrategies.userDefined())
            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())

            // create a required code block
            .requiredCodeblock(Labels.from(CODE_KEY, "Code JS", ""), CodeLanguage.Javascript)

            // python example
            .requiredCodeblock(Labels.from(CODE_PYTHON_KEY, "Code Python", ""), CodeLanguage.Python)

            // no specific language
            .requiredCodeblock(Labels.from(CODE_EMPTY_KEY, "Any Code", ""), CodeLanguage.None)

            .build();
  }

  @Override
  public ConfiguredEventProcessor<DummyParameters> onInvocation(DataProcessorInvocation graph, ProcessingElementParameterExtractor extractor) {

    // Extract the code parameter value
    String code = extractor.codeblockValue(CODE_KEY);

    // now the text parameter would be added to a parameter class (omitted for this example)

    return new ConfiguredEventProcessor<>(new DummyParameters(graph), DummyEngine::new);
  }
}


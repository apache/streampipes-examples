package org.apache.streampipes.pe.examples.jvm.staticproperty;

import org.apache.streampipes.extensions.api.pe.IStreamPipesDataProcessor;
import org.apache.streampipes.extensions.api.pe.config.IDataProcessorConfiguration;
import org.apache.streampipes.extensions.api.pe.context.EventProcessorRuntimeContext;
import org.apache.streampipes.extensions.api.pe.param.IDataProcessorParameters;
import org.apache.streampipes.extensions.api.pe.routing.SpOutputCollector;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.sdk.builder.ProcessingElementBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.builder.processor.DataProcessorConfiguration;
import org.apache.streampipes.sdk.helpers.CodeLanguage;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.OutputStrategies;
import org.apache.streampipes.sdk.helpers.SupportedFormats;
import org.apache.streampipes.sdk.helpers.SupportedProtocols;

public class CodeInputExampleController implements IStreamPipesDataProcessor {

  private static final String CODE_KEY = "code-key";
  private static final String CODE_PYTHON_KEY = "code-python-key";
  private static final String CODE_EMPTY_KEY = "code-empty-key";

  @Override
  public IDataProcessorConfiguration declareConfig() {
    return DataProcessorConfiguration.create(
        CodeInputExampleController::new,
        ProcessingElementBuilder.create("org.apache.streampipes.examples.staticproperty" +
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

            .build()
    );
  }

  @Override
  public void onPipelineStarted(IDataProcessorParameters params, SpOutputCollector collector, EventProcessorRuntimeContext runtimeContext) {
    // Extract the code parameter value
    String code = params.extractor().codeblockValue(CODE_KEY);
  }

  @Override
  public void onEvent(Event event, SpOutputCollector collector) {

  }

  @Override
  public void onPipelineStopped() {

  }


}


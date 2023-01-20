package org.apache.streampipes.function.example;

import org.apache.streampipes.model.function.FunctionId;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.wrapper.standalone.function.FunctionConfig;
import org.apache.streampipes.wrapper.standalone.function.FunctionConfigBuilder;
import org.apache.streampipes.wrapper.standalone.function.FunctionContext;
import org.apache.streampipes.wrapper.standalone.function.StreamPipesFunction;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StreamPipesFunctionExample extends StreamPipesFunction {

  private static final Logger LOG = LoggerFactory.getLogger(StreamPipesFunctionExample.class);

  @Override
  public List<String> requiredStreamIds() {
    return List.of("urn:streampipes.apache.org:eventstream:VrqOIM");
  }

  @Override
  public FunctionConfig getFunctionConfig() {
    return FunctionConfigBuilder
        .create(FunctionId.from("org.apache.streampipes.example.function", 1))
        .build();
  }

  @Override
  public void onServiceStarted(FunctionContext context) {
    LOG.info("Service started!");
  }

  @Override
  public void onEvent(Event event, String streamId) {
    MapUtils.debugPrint(System.out, "", event.getRaw());
  }

  @Override
  public void onServiceStopped() {
    LOG.info("Service stopped!");
  }
}

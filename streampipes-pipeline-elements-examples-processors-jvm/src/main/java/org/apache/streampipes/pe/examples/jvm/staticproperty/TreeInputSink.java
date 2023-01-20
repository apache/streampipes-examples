package org.apache.streampipes.pe.examples.jvm.staticproperty;

import org.apache.streampipes.commons.exceptions.SpRuntimeException;
import org.apache.streampipes.extensions.api.runtime.SupportsRuntimeConfig;
import org.apache.streampipes.model.graph.DataSinkDescription;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.model.staticproperty.RuntimeResolvableTreeInputStaticProperty;
import org.apache.streampipes.model.staticproperty.StaticProperty;
import org.apache.streampipes.model.staticproperty.TreeInputNode;
import org.apache.streampipes.sdk.builder.DataSinkBuilder;
import org.apache.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.apache.streampipes.sdk.extractor.StaticPropertyExtractor;
import org.apache.streampipes.sdk.helpers.EpRequirements;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.SupportedFormats;
import org.apache.streampipes.sdk.helpers.SupportedProtocols;
import org.apache.streampipes.wrapper.context.EventSinkRuntimeContext;
import org.apache.streampipes.wrapper.standalone.SinkParams;
import org.apache.streampipes.wrapper.standalone.StreamPipesDataSink;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeInputSink extends StreamPipesDataSink implements SupportsRuntimeConfig {

  private static final String SP_KEY = "example-key";
  private static final String TREE_KEY = "tree-key";

  @Override
  public DataSinkDescription declareModel() {
    return DataSinkBuilder.create("org.apache.streampipes.examples.treeinput", "Tree Input Example", "")
            .requiredStream(StreamRequirementsBuilder.
                    create()
                    .requiredProperty(EpRequirements.anyProperty())
                    .build())
            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())

            // create a simple text parameter
            .requiredTextParameter(Labels.from(SP_KEY, "Example Key", "required by tree input"))
            .requiredRuntimeResolvableTreeInput(
                    Labels.from(TREE_KEY, "Tree", "The tree input"),
                    Collections.singletonList(SP_KEY))

            .build();
  }

  @Override
  public void onInvocation(SinkParams parameters, EventSinkRuntimeContext runtimeContext) throws SpRuntimeException {

  }

  @Override
  public void onEvent(Event event) throws SpRuntimeException {

  }

  @Override
  public void onDetach() throws SpRuntimeException {

  }

  @Override
  public StaticProperty resolveConfiguration(String staticPropertyInternalName,
                                             StaticPropertyExtractor extractor) {
    RuntimeResolvableTreeInputStaticProperty treeInput = extractor
            .getStaticPropertyByName(
                    staticPropertyInternalName,
                    RuntimeResolvableTreeInputStaticProperty.class);

    List<TreeInputNode> nodes = buildSampleNodes();
    treeInput.setNodes(nodes);

    return treeInput;
  }

  private List<TreeInputNode> buildSampleNodes() {
    return Arrays.asList(buildExpandableNode("Fruit", buildFruitNodes()), buildExpandableNode("Vegetable", buildVegetableNodes()));
  }

  private TreeInputNode buildExpandableNode(String nodeName, List<TreeInputNode> children) {
    TreeInputNode node = new TreeInputNode();
    node.setNodeName(nodeName);
    node.setChildren(children);

    return node;
  }

  private List<TreeInputNode> buildFruitNodes() {
    return Arrays.asList(
            buildNode("Apple"),
            buildNode("Banana"),
            buildNode("Orange"),
            buildExpandableNode("Sour", Arrays.asList(buildNode("Lemon"), buildNode("Guava"), buildNode("Cranberry")))
    );
  }

  private List<TreeInputNode> buildVegetableNodes() {
    return Arrays.asList(
            buildNode("Tomato"),
            buildNode("Pepper"),
            buildNode("Carrot")
    );
  }

  private TreeInputNode buildNode(String nodeName) {
    TreeInputNode node = new TreeInputNode();
    node.setNodeName(nodeName);

    return node;
  }
}

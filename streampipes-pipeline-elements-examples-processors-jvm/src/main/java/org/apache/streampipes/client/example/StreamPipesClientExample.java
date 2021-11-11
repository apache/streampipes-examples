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
package org.apache.streampipes.client.example;

import org.apache.commons.collections.MapUtils;
import org.apache.streampipes.client.StreamPipesClient;
import org.apache.streampipes.client.StreamPipesCredentials;
import org.apache.streampipes.client.credentials.CredentialsProvider;
import org.apache.streampipes.client.live.KafkaConfig;
import org.apache.streampipes.model.SpDataStream;
import org.apache.streampipes.model.graph.DataSinkInvocation;
import org.apache.streampipes.model.pipeline.Pipeline;
import org.apache.streampipes.model.pipeline.PipelineOperationStatus;
import org.apache.streampipes.model.template.PipelineElementTemplate;

import java.util.List;

public class
StreamPipesClientExample {

  public static void main(String[] args) {

    // First, go to the StreamPipes UI and create an API key (user -> profile in the upper right corner)
    
    // Create credentials by providing a user (the email) and the API key
    CredentialsProvider credentials = StreamPipesCredentials
            .withApiKey(System.getenv("user"), System.getenv("apiKey"));

    // Create an instance of the StreamPipes client
    StreamPipesClient client = StreamPipesClient
            .create("localhost", 80, credentials, true);

    // Get all pipelines
    List<Pipeline> pipelines = client.pipelines().all();

    // Start a pipeline
    PipelineOperationStatus message = client.pipelines().start(pipelines.get(0));

    // Get all pipeline element templates
    List<PipelineElementTemplate> templates = client.pipelineElementTemplates().all();

    // Get all data sinks
    List<DataSinkInvocation> dataSinks = client.sinks().all();

    // Get all data streams
    List<SpDataStream> dataStreams = client.streams().all();

    // Subscribe to a data stream
    client.streams().subscribe(dataStreams.get(0), event -> MapUtils.debugPrint(System.out, "event", event.getRaw()));

    // Subscribe to a data stream and provide an additional Kafka config (e.g., for access from outside the StreamPipes network)
    client.streams().subscribe(dataStreams.get(0), KafkaConfig.create("localhost", 9094), event -> {
      MapUtils.debugPrint(System.out, "event", event.getRaw());
    });
  }
}

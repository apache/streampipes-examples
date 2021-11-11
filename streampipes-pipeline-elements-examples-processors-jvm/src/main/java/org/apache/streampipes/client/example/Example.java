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

import org.apache.streampipes.client.StreamPipesClient;
import org.apache.streampipes.client.StreamPipesCredentials;
import org.apache.streampipes.client.credentials.CredentialsProvider;
import org.apache.streampipes.model.pipeline.Pipeline;

import java.util.List;

public class Example {

  public static void main(String[] args) {
//    CredentialsProvider credentials = StreamPipesCredentials
//            .withApiKey(System.getenv("user"), System.getenv("apiKey"));

    CredentialsProvider credentials = StreamPipesCredentials.withServiceToken("sp-service-client", "my-apache-streampipes-secret-key-change-me");

    // Create an instance of the StreamPipes client
    StreamPipesClient client = StreamPipesClient
            .create("localhost", 8082, credentials, true);

    // Get all pipelines
    List<Pipeline> pipelines = client.pipelines().all();
    System.out.println(pipelines.size());

//    // Start a pipeline
//    PipelineOperationStatus message = client.pipelines().start(pipelines.get(0));
//
//    // Get all data streams
//    List<SpDataStream> dataStreams = client.streams().all();
//
//    // Subscribe to a data stream
//    client.streams().subscribe(dataStreams.get(0), event -> MapUtils.debugPrint(System.out, "event", event.getRaw()));
  }
}

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

package org.apache.streampipes.pe.examples.jvm.config;


import org.apache.streampipes.container.model.PeConfig;
import org.apache.streampipes.svcdiscovery.SpServiceDiscovery;
import org.apache.streampipes.svcdiscovery.api.SpConfig;

public enum ExamplesJvmConfig implements PeConfig {
  INSTANCE;

  private SpConfig config;

  public final static String serverUrl;
  public final static String iconBaseUrl;

  private final static String service_id = "pe/org.apache.streampipes.processors.examples.jvm";
  private final static String service_name = "Processors Examples JVM";
  private final static String service_container_name = "processors-examples-jvm";

  ExamplesJvmConfig() {
    config = SpServiceDiscovery.getSpConfig(service_id);
    config.register(ConfigKeys.HOST, service_container_name, "Hostname for the pe esper");
    config.register(ConfigKeys.PORT, 8090, "Port for the pe esper");

    config.register(ConfigKeys.ICON_HOST, "backend", "Hostname for the icon host");
    config.register(ConfigKeys.ICON_PORT, 80, "Port for the icons in nginx");

    config.register(ConfigKeys.SERVICE_NAME_KEY, service_name, "The name of the service");

  }

  static {
    serverUrl = ExamplesJvmConfig.INSTANCE.getHost() + ":" + ExamplesJvmConfig.INSTANCE.getPort();
    iconBaseUrl = "http://" + ExamplesJvmConfig.INSTANCE.getIconHost() + ":" + ExamplesJvmConfig.INSTANCE.getIconPort() + "/assets/img/pe_icons";
  }

  public static final String getIconUrl(String pictureName) {
    return iconBaseUrl + "/" + pictureName + ".png";
  }

  @Override
  public String getHost() {
    return config.getString(ConfigKeys.HOST);
  }

  @Override
  public int getPort() {
    return config.getInteger(ConfigKeys.PORT);
  }

  public String getIconHost() {
    return config.getString(ConfigKeys.ICON_HOST);
  }

  public int getIconPort() {
    return config.getInteger(ConfigKeys.ICON_PORT);
  }

  @Override
  public String getId() {
    return service_id;
  }

  @Override
  public String getName() {
    return config.getString(ConfigKeys.SERVICE_NAME_KEY);
  }


}

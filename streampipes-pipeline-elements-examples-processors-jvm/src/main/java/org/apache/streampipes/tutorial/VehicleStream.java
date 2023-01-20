

package org.apache.streampipes.tutorial;

import org.apache.streampipes.messaging.kafka.SpKafkaProducer;
import org.apache.streampipes.model.SpDataStream;
import org.apache.streampipes.sdk.builder.DataStreamBuilder;
import org.apache.streampipes.sdk.helpers.EpProperties;
import org.apache.streampipes.sdk.helpers.Formats;
import org.apache.streampipes.sdk.helpers.Labels;
import org.apache.streampipes.sdk.helpers.Protocols;
import org.apache.streampipes.sources.AbstractAdapterIncludedStream;
import org.apache.streampipes.vocabulary.Geo;

import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VehicleStream extends AbstractAdapterIncludedStream {

  @Override
  public SpDataStream declareModel() {
    return DataStreamBuilder.create("org.apache.streampipes.tutorial.vehicle.position", "Vehicle Position", "An event stream " +
            "that produces current vehicle positions")
        .property(EpProperties.timestampProperty("timestamp"))
        .property(EpProperties.stringEp(Labels.from("plate-number", "Plate Number", "Denotes the plate number of the vehicle"), "plateNumber", "http://my.company/plateNumber"))
        .property(EpProperties.doubleEp(Labels.from("latitude", "Latitude", "Denotes the latitude value of the vehicle's position"), "latitude", Geo.LAT))
        .property(EpProperties.doubleEp(Labels.from("longitude", "Longitude", "Denotes the longitude value of the vehicle's position"), "longitude", Geo.LNG))
        .format(Formats.jsonFormat())
        .protocol(Protocols.kafka("localhost", 9094, "org.apache.streampipes.tutoria.vehicle"))
        .build();
  }

  @Override
  public void executeStream() {
    SpKafkaProducer producer = new SpKafkaProducer("localhost:9094", "org.apache.streampipes.tutoria.vehicle", Collections.emptyList());
    Random random = new Random();
    Runnable runnable = () -> {
      for (;;) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", System.currentTimeMillis());
        jsonObject.addProperty("plateNumber", "KA-SP 1");
        jsonObject.addProperty("latitude", random.nextDouble());
        jsonObject.addProperty("longitude", random.nextDouble());

        producer.publish(jsonObject.toString());

        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    };

    new Thread(runnable).start();
  }
}

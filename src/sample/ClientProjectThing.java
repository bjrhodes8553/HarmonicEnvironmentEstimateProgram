package sample;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class ClientProjectThing {
  private String client;
  private String project;

  public ClientProjectThing(String client, String project) {
    this.client = client;
    this.project = project;
  }

  public String getClient(){
    return client;
  }

  public String getProject(){
    return project;
  }
}

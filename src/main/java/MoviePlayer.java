public class MoviePlayer extends Product implements MultimediaControl {

  MonitorType monitorType;
  Screen screen;

  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.monitorType = monitorType;
    this.screen = screen;
  }

  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {
    return
        "Name: " + getName() + "\n" +
            "Manufacturer: " + getManufacturer() + "\n" +
            "Type: " + getItemType() + "\n" +
            "Screen: " + screen + "\n" +
            "Monitor Type: " + monitorType;
  }
}
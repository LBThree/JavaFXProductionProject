/**
 * A subset of a product.
 *
 * @author Logan Bahr
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * The type of monitor.
   */
  final MonitorType monitorType;
  /**
   * The screen's specifications.
   */
  final Screen screen;

  /**
   * Creates a movie player.
   *
   * @param name         the name of the movie player
   * @param manufacturer the manufacturer of the movie player
   * @param screen       the specifications of the screen
   * @param monitorType  the type of monitor
   * @deprecated not used
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.monitorType = monitorType;
    this.screen = screen;
  }

  /**
   * Prints to the console.
   *
   * @deprecated not used
   */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Prints to the console.
   *
   * @deprecated not used
   */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Prints to the console.
   *
   * @deprecated not used
   */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Prints to the console.
   *
   * @deprecated not used
   */
  @Override
  public void next() {
    System.out.println("Next");
  }

  /**
   * A format to print to the console the characteristics of the movie player.
   *
   * @return a line to the console
   */
  @Override
  public String toString() {
    return
        "Name: " + getName() + "\n"
            + "Manufacturer: "
            + getManufacturer()
            + "\n"
            + "Type: "
            + getType()
            + "\n"
            + "Screen: "
            + screen
            + "\n"
            + "Monitor Type: "
            + monitorType;
  }
}
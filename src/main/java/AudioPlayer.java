/**
 * Describes the characteristics of a subclass of a Product.
 *
 * @author Logan Bahr
 */
public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * The formats that are supported by this audio player.
   */
  final String supportedAudioFormats;
  /**
   * The playlists that are supported by this audio player.
   */
  final String supportedPlaylistFormats;

  /**
   * Creates an Audio Player product.
   *
   * @param name                     the name of the product
   * @param manufacturer             the manufacturer of the product
   * @param supportedAudioFormats    what formats the product supports
   * @param supportedPlaylistFormats what playlists the product supports
   * @deprecated not used
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Prints the console.
   *
   * @deprecated not used
   */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Prints the console.
   *
   * @deprecated not used
   */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Prints the console.
   *
   * @deprecated not used
   */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Prints the console.
   *
   * @deprecated not used
   */
  @Override
  public void next() {
    System.out.println("Next");
  }

  /**
   * Prints the console a formatted line that describes the Audio Player.
   *
   * @return a line on the console about the Audio Player
   */
  @Override
  public String toString() {
    return
        "Name: " + getName()
            + "\n"
            + "Manufacturer: "
            + getManufacturer()
            + "\n" + "Type: "
            + getType()
            + "\n"
            + "Supported Audio Formats: "
            + supportedAudioFormats
            + "\n"
            + "Supported Playlist Formats: "
            + supportedPlaylistFormats;
  }
}

/**
 * Defines the characteristics of a screen.
 *
 * @author Logan Bahr
 */
public class Screen implements ScreenSpec {

  /**
   * The resolution for the screen.
   */
  final String resolution;
  /**
   * The refresh rate for the screen.
   */
  final int refreshRate;
  /**
   * The response time for the screen.
   */
  final int responseTime;

  /**
   * Creates a screen.
   *
   * @param resolution   the resolution for the screen
   * @param refreshRate  the refresh rate for the screen
   * @param responseTime the response time for the screen
   * @deprecated Constructor never used.
   */
  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Gets the resolution for the screen.
   *
   * @return the screens resolution
   * @deprecated Method never used.
   */
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * Gets the refresh rate for the screen.
   *
   * @return the screens refresh rate
   * @deprecated Method never used.
   */
  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Gets the response time for the screen.
   *
   * @return the screens response time
   * @deprecated Method never used.
   */
  @Override
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * A formatted console output to describe the characteristics of the screen.
   *
   * @return a single line to the console
   */
  @Override
  public String toString() {
    return
        "\n"
            + "Resolution: "
            + resolution
            + "\n"
            + "Refresh rate: "
            + refreshRate
            + "\n"
            + "Response time: "
            + responseTime;
  }
}

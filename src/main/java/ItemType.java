/**
 * An enumeration for the different kinds of Products that can be produced.
 *
 * @author Logan Bahr
 */

public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AudioMobile("AM"),
  VisualMobile("VM");

  /**
   * The two letter abbreviation for the item type.
   */
  public final String code;

  /**
   * Constructs an item type.
   *
   * @param code the two letter abbreviation for the type of item
   */
  ItemType(String code) {
    this.code = code;
  }
}

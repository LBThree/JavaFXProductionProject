public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AudioMobile("AM"),
  VisualMobile("VM");

  public String code;

  ItemType(String code) {
    this.code = code;
  }
}

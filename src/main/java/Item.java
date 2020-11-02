public interface Item {

  int getId();

  ItemType getType();

  String getName();

  String getManufacturer();

  void setName(String name);

  void setManufacturer(String manufacturer);

  void setType(ItemType type);
}

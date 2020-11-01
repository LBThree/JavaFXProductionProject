public interface Item {

  int getId();

  ItemType getItemType();

  String getName();

  String getManufacturer();

  void setName(String name);

  void setManufacturer(String manufacturer);
}


public class Widget extends Product {



  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }

  public Widget (String name, String manufacturer, String type){
    super(name,manufacturer, ItemType.valueOf(type));

  };


}






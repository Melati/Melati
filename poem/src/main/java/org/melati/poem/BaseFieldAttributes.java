package org.melati.poem;

public class BaseFieldAttributes implements FieldAttributes {

  private String name;
  private String displayName;
  private String description;
  private PoemType type;
  private String renderInfo;

  public BaseFieldAttributes(String name, String displayName,
                             String description, PoemType type,
                             String renderInfo) {
    this.name = name;
    this.displayName = displayName;
    this.description = description;
    this.type = type;
    this.renderInfo = renderInfo;
  }

  public BaseFieldAttributes(FieldAttributes other, boolean nullable) {
    this(other.getName(), other.getDisplayName(), other.getDescription(),
	 other.getType().withNullable(nullable), other.getRenderInfo());
  }

  public BaseFieldAttributes(String name, PoemType type) {
    this(name, name, null, type, null);
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public PoemType getType() {
    return type;
  }

  public boolean getIndexed() {
    return false;
  }

  public boolean getUserEditable() {
    return true;
  }

  public boolean getUserCreateable() {
    return false;
  }

  public String getRenderInfo() {
    return renderInfo;
  }
}

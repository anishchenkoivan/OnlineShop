package org.example.entity;

public enum Product {
  WATER("water", 0),
  FLOUR("flour", 0),
  BREAD("bread", 0),
  VODKA("vodka", 0),
  COOKIE("cookie", 0),
  WAFFLES("waffles", 0),
  RICE("rice", 0),
  PASTA("pasta", 0);

  private String unit;
  private final int value;

  private Product(String unit, int value) {
    this.unit = unit;
    this.value = value;
  }

  public String getUnit() {
    return unit;
  }
}

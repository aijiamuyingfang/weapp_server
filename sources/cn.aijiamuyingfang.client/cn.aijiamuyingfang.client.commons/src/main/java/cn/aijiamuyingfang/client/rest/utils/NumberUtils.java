package cn.aijiamuyingfang.client.rest.utils;

public class NumberUtils {

  public static int toInt(String str, int defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      return defaultValue;
    }
  }
}

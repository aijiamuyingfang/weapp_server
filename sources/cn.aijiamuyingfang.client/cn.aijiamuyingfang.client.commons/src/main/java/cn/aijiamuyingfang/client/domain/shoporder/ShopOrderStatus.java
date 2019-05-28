package cn.aijiamuyingfang.client.domain.shoporder;

import com.google.gson.annotations.SerializedName;

import cn.aijiamuyingfang.client.domain.BaseEnum;

/**
 * [描述]:
 * <p>
 * 订单状态
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-27 16:03:34
 */
public enum ShopOrderStatus implements BaseEnum {

  /**
   * 未知类型
   */
  @SerializedName("0")
  UNKNOW(0),
  /**
   * 预订
   */
  @SerializedName("1")
  PREORDER(1),
  /**
   * 未开始
   */
  @SerializedName("2")
  UNSTART(2),

  /**
   * 进行中
   */
  @SerializedName("3")
  DOING(3),

  /**
   * 已完成
   */
  @SerializedName("4")
  FINISHED(4),

  /**
   * 超时(例如:订单超时未取货就会变为超时的状态)
   */
  @SerializedName("5")
  OVERTIME(5);

  private int value;

  private ShopOrderStatus(int value) {
    this.value = value;
  }

  public static ShopOrderStatus fromValue(int value) {
    for (ShopOrderStatus type : ShopOrderStatus.values()) {
      if (type.value == value) {
        return type;
      }
    }
    return UNKNOW;
  }

  @Override
  public int getValue() {
    return value;
  }
}
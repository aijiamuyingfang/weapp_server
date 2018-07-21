package cn.aijiamuyingfang.server.commons.domain;

import com.google.gson.annotations.SerializedName;

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
   * 预订
   */
  @SerializedName("0")
  PREORDER(0),
  /**
   * 未开始
   */
  @SerializedName("1")
  UNSTART(1),

  /**
   * 进行中
   */
  @SerializedName("2")
  DOING(2),

  /**
   * 已完成
   */
  @SerializedName("3")
  FINISHED(3),

  /**
   * 超时(例如:订单超时未取货就会变为超时的状态)
   */
  @SerializedName("4")
  OVERTIME(4),

  /**
   * 未知类型
   */
  @SerializedName("-1")
  UNKNOW(-1);

  private int value;

  private ShopOrderStatus(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

}
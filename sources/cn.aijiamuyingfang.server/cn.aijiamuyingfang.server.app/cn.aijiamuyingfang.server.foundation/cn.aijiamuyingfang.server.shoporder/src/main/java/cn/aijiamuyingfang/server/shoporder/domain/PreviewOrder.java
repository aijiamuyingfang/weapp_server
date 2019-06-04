package cn.aijiamuyingfang.server.shoporder.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * [描述]:
 * <p>
 * 预览的订单,在系统中一个用户只能有一个预览订单
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-27 15:54:00
 */
@Entity
@Data
public class PreviewOrder {
  @Id
  @GeneratedValue(generator = "strategy_uuid")
  @GenericGenerator(name = "strategy_uuid", strategy = "uuid")
  private String id;

  /**
   * 订单所属用户
   */
  private String userId;

  /**
   * 收货地址
   */
  private String recieveAddressId;

  /**
   * 预览的商品项
   */
  @OneToMany(cascade = CascadeType.ALL)
  private List<PreviewOrderItem> orderItemList = new ArrayList<>();

  /**
   * 添加预览项
   * 
   * @param item
   *          预览项
   */
  public void addOrderItem(PreviewOrderItem item) {
    if (null == item) {
      return;
    }
    synchronized (this) {
      if (null == this.orderItemList) {
        this.orderItemList = new ArrayList<>();
      }
    }
    this.orderItemList.add(item);
  }
}

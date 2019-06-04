package cn.aijiamuyingfang.server.shoporder.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.aijiamuyingfang.server.domain.SendType;
import cn.aijiamuyingfang.server.domain.ShopOrderStatus;
import cn.aijiamuyingfang.server.shoporder.domain.ShopOrder;

/**
 * [描述]:
 * <p>
 * 订单的数据仓库
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-25 21:15:03
 */
@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, String> {

  /**
   * 计算不同状态和送货方式下订单总数
   * 
   * @param userId
   *          用户id
   * @param statusList
   * @param sendTypeList
   * @return
   */
  int countByUserIdAndStatusInAndSendtypeIn(String userId, List<ShopOrderStatus> statusList,
      List<SendType> sendTypeList);

  /**
   * 根据订单状态、配送方式分页查找用户的订单
   * 
   * @param userId
   *          用户id
   * @param statusList
   * @param sendTypeList
   * @param pageable
   *          分页信息
   * @return
   */
  Page<ShopOrder> findByUserIdAndStatusInAndSendtypeIn(String userId, List<ShopOrderStatus> statusList,
      List<SendType> sendTypeList, Pageable pageable);

  /**
   * 根据订单状态、配送方式分页查找所有订单
   * 
   * @param statusList
   * @param sendTypeList
   * @param pageable
   *          分页信息
   * @return
   */
  Page<ShopOrder> findByStatusInAndSendtypeIn(List<ShopOrderStatus> statusList, List<SendType> sendTypeList,
      Pageable pageable);

  /**
   * 根据状态和分页条件,查找订单
   * 
   * @param status
   * @param pageable
   *          分页信息
   * @return
   */
  Page<ShopOrder> findByStatus(ShopOrderStatus status, Pageable pageable);

  /**
   * 根据状态查找订单(非分页)
   * 
   * @param status
   * @return
   */
  List<ShopOrder> findByStatus(ShopOrderStatus status);

  /**
   * 查找包含某件商品的预约单
   * 
   * @param goodId
   *          商品id
   * @return
   */
  @Query(
      value = "select * from shop_order where status=1 and id in (select shop_order_id from shop_order_order_item_list where "
          + "order_item_list_id in (select id from shop_order_item  where good_id=:good_id))",
      nativeQuery = true)
  List<ShopOrder> findPreOrderContainsGoodid(@Param("good_id") String goodId);
}

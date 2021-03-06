package cn.aijiamuyingfang.server.feign;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aijiamuyingfang.vo.coupon.GoodVoucher;
import cn.aijiamuyingfang.vo.coupon.PagableGoodVoucherList;
import cn.aijiamuyingfang.vo.coupon.PagableUserVoucherList;
import cn.aijiamuyingfang.vo.coupon.PagableVoucherItemList;
import cn.aijiamuyingfang.vo.coupon.UserVoucher;
import cn.aijiamuyingfang.vo.coupon.VoucherItem;
import cn.aijiamuyingfang.vo.response.ResponseBean;

@FeignClient(name = "coupon-service")
public interface CouponClient {
  /**
   * 分页获取用户兑换券
   * 
   * @param username
   * @param currentPage
   * @param pageSize
   * @return
   */
  @GetMapping(value = "/user/{username}/coupon/uservoucher")
  ResponseBean<PagableUserVoucherList> getUserVoucherList(@PathVariable("username") String username,
      @RequestParam("current_page") int currentPage, @RequestParam("page_size") int pageSize);

  /**
   * 更新用户兑换券
   * 
   * @param username
   * @param userVoucherList
   */
  @PutMapping(value = "/user/{username}/coupon/uservoucher")
  ResponseBean<Void> updateUserVoucherList(@PathVariable("username") String username,
      @RequestBody List<UserVoucher> userVoucherList);

  /**
   * 获得用户的兑换券
   * 
   * @param username
   * @param voucherId
   * @return
   */
  @GetMapping(value = "/user/{username}/coupon/uservoucher/{voucher_id}")
  ResponseBean<UserVoucher> getUserVoucher(@PathVariable("username") String username,
      @PathVariable("voucher_id") String voucherId);

  /**
   * 获得用户用户GoodVoucher的兑换券
   * 
   * @param username
   * @param voucherId
   * @return
   */
  @GetMapping(value = "/user/{username}/coupon/uservoucher/goodvoucher/{voucher_id}")
  ResponseBean<UserVoucher> getUserVoucherForGoodVoucher(@PathVariable("username") String username,
      @PathVariable("voucher_id") String voucherId);

  /**
   * 分页获取商品兑换项
   * 
   * @param currentPage
   * @param pageSize
   * @return
   */
  @GetMapping(value = "/coupon/goodvoucher")
  ResponseBean<PagableGoodVoucherList> getGoodVoucherList(@RequestParam(value = "current_page") int currentPage,
      @RequestParam("page_size") int pageSize);

  /**
   * 获取商品兑换项
   * 
   * @param voucherId
   * @return
   */
  @GetMapping(value = "/coupon/goodvoucher/{voucher_id}")
  ResponseBean<GoodVoucher> getGoodVoucher(@PathVariable("voucher_id") String voucherId);

  /**
   * 废弃商品兑换券
   * 
   * @param voucherId
   */
  @DeleteMapping(value = "/coupon/goodvoucher/{voucher_id}")
  Future<ResponseBean<Void>> deprecateGoodVoucher(@PathVariable("voucher_id") String voucherId);

  /**
   * 分页获取可选的兑换方式
   * 
   * @param currentPage
   * @param pageSize
   * @return
   */
  @GetMapping(value = "/coupon/voucher_item")
  ResponseBean<PagableVoucherItemList> getVoucherItemList(@RequestParam("current_page") int currentPage,
      @RequestParam("page_size") int pageSize);

  /**
   * 获取兑换方式
   * 
   * @param voucherItemId
   * @return
   */
  @GetMapping(value = "/coupon/voucher_item/{voucher_item_id}")
  ResponseBean<VoucherItem> getVoucherItem(@PathVariable("voucher_item_id") String voucherItemId);
}

package cn.aijiamuyingfang.client.rest.api;

import java.util.List;

import cn.aijiamuyingfang.vo.response.ResponseBean;
import cn.aijiamuyingfang.vo.store.PagableStoreList;
import cn.aijiamuyingfang.vo.store.Store;
import cn.aijiamuyingfang.vo.store.StoreAddress;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * [描述]:
 * <p>
 * StoreController客户端调用接口
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-12 16:13:34
 */
public interface StoreControllerApi {

  /**
   * 分页获取在使用中的Store
   * 
   * @param currentPage
   *          当前页 默认值:1 (currentPage必须&ge;1,否则重置为1)
   * @param pageSize
   *          每页大小 默认值:10(pageSize必须&gt;0,否则重置为1)
   * @return
   */
  @GET(value = "/goods-service/store")
  public Observable<ResponseBean<PagableStoreList>> getInUseStoreList(
      @Query(value = "current_page") int currentPage, @Query(value = "page_size") int pageSize);

  /**
   * 创建门店
   * 
   * @param storeRequest
   * @param accessToken
   * @return
   */
  @POST(value = "/goods-service/store")
  public Observable<ResponseBean<Store>> createStore(@Body MultipartBody storeRequest,
      @Query("access_token") String accessToken);

  /**
   * 获取门店信息
   * 
   * @param storeId
   * @return
   */
  @GET(value = "/goods-service/store/{store_id}")
  public Observable<ResponseBean<Store>> getStore(@Path("store_id") String storeId);

  /**
   * 获取门店地址信息
   * 
   * @param addressId
   * @return
   */
  @GET(value = "/goods-service/storeaddress/{address_id}")
  public Observable<ResponseBean<StoreAddress>> getStoreAddressByAddressId(@Path("address_id") String addressId);

  /**
   * 更新门店信息
   * 
   * @param storeId
   * @param storeRequest
   * @param accessToken
   * @return
   */
  @PUT(value = "/goods-service/store/{store_id}")
  public Observable<ResponseBean<Store>> updateStore(@Path("store_id") String storeId, @Body Store storeRequest,
      @Query("access_token") String accessToken);

  /**
   * 废弃门店
   * 
   * @param storeId
   * @param accessToken
   * @return
   */
  @DELETE(value = "/goods-service/store/{store_id}")
  public Observable<ResponseBean<Void>> deprecateStore(@Path("store_id") String storeId,
      @Query("access_token") String accessToken);

  /**
   * 获取默认门店Id
   * 
   * @return
   */
  @GET(value = "/goods-service/store/defaultid")
  public Observable<ResponseBean<String>> getDefaultStoreId();

  /**
   * 获取在哪些城市有门店分布
   * 
   * @return
   */
  @GET(value = "/goods-service/store/city")
  public Observable<ResponseBean<List<String>>> getStoresCity();
}

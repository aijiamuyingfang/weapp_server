package cn.aijiamuyingfang.server.client.itapi;

import cn.aijiamuyingfang.server.client.annotation.HttpApi;
import cn.aijiamuyingfang.server.commons.constants.AuthConstants;
import cn.aijiamuyingfang.server.commons.controller.bean.ResponseBean;
import cn.aijiamuyingfang.server.domain.goods.StoreRequest;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
@HttpApi(baseurl = "weapp.host_name")
@SuppressWarnings("rawtypes")
public interface StoreControllerApi {

  /**
   * 分页获取在使用中的Store
   * 
   * @param token
   * @param currentpage
   *          当前页 默认值:1 (currentpage必须>=1,否则重置为1)
   * @param pagesize
   *          每页大小 默认值:10(pagesize必须>0,否则重置为1)
   * @return
   */
  @GET(value = "/store")
  public Call<ResponseBean> getInUseStoreList(@Header(AuthConstants.HEADER_STRING) String token,
      @Query(value = "currentpage") int currentpage, @Query(value = "pagesize") int pagesize);

  /**
   * 创建门店
   * 
   * @param token
   * @param coverImage
   * @param detailImages
   * @param storeRequest
   * @return
   */
  @POST(value = "/store")
  public Call<ResponseBean> createStore(@Header(AuthConstants.HEADER_STRING) String token,
      @Body MultipartBody storeRequest);

  /**
   * 获取门店信息
   * 
   * @param token
   * @param storeid
   * @return
   */
  @GET(value = "/store/{storeid}")
  public Call<ResponseBean> getStore(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("storeid") String storeid);

  /**
   * 更新门店信息
   * 
   * @param token
   * @param storeid
   * @param storeRequest
   * @return
   */
  @PUT(value = "/store/{storeid}")
  public Call<ResponseBean> updateStore(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("storeid") String storeid, @Body StoreRequest storeRequest);

  /**
   * 废弃门店
   * 
   * @param token
   * @param storeid
   * @return
   */
  @DELETE(value = "/store/{storeid}")
  public Call<ResponseBean> deprecateStore(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("storeid") String storeid);

  /**
   * 获取默认门店Id
   * 
   * @param token
   * @return
   */
  @GET(value = "/store/defaultid")
  public Call<ResponseBean> getDefaultStoreId(@Header(AuthConstants.HEADER_STRING) String token);

  /**
   * 获取在哪些城市有门店分布
   * 
   * @param token
   * @return
   */
  @GET(value = "/store/city")
  public Call<ResponseBean> getStoresCity(@Header(AuthConstants.HEADER_STRING) String token);

  /**
   * 门店下添加条目
   * 
   * @param token
   * @param storeid
   * @param classifyid
   * @return
   */
  @PUT(value = "/store/{storeid}/classify/{classifyid}")
  public Call<ResponseBean> addStoreClassify(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("storeid") String storeid, @Path("classifyid") String classifyid);
}

package cn.aijiamuyingfang.client.rest.api;

import cn.aijiamuyingfang.client.rest.annotation.HttpApi;
import cn.aijiamuyingfang.commons.constants.AuthConstants;
import cn.aijiamuyingfang.commons.domain.address.RecieveAddress;
import cn.aijiamuyingfang.commons.domain.response.ResponseBean;
import cn.aijiamuyingfang.commons.domain.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * [描述]:
 * <p>
 * UserController客户端调用接口
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-10 23:01:18
 */
@HttpApi(baseurl = "weapp.host_name")
@SuppressWarnings("rawtypes")
public interface UserControllerApi {

  /**
   * 获取用户
   * 
   * @param token
   * @param userid
   * @return
   */
  @GET(value = "/user/{userid}")
  public Call<ResponseBean> getUser(@Header(AuthConstants.HEADER_STRING) String token, @Path("userid") String userid);

  /**
   * 获取用户手机号
   * 
   * @param token
   * @param userid
   * @return
   */
  @GET(value = "/user/{userid}/phone")
  public Call<ResponseBean> getUserPhone(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid);

  /**
   * 更新用户信息
   * 
   * @param token
   * @param userid
   * @param user
   * @return
   */
  @PUT(value = "/user/{userid}")
  public Call<ResponseBean> updateUser(@Header(AuthConstants.HEADER_STRING) String token, @Path("userid") String userid,
      @Body User user);

  /**
   * 获取用户收件地址
   * 
   * @param token
   * @param userid
   * @return
   */
  @GET(value = "/user/{userid}/recieveaddress")
  public Call<ResponseBean> getUserRecieveAddressList(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid);

  /**
   * 给用户添加收件地址
   * 
   * @param token
   * @param userid
   * @param request
   * @return
   */
  @POST(value = "/user/{userid}/recieveaddress")
  public Call<ResponseBean> addUserRecieveAddress(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid, @Body RecieveAddress request);

  /**
   * 获取收件地址
   * 
   * @param token
   * @param userid
   * @param addressid
   * @return
   */
  @GET(value = "/user/{userid}/recieveaddress/{addressid}")
  public Call<ResponseBean> getRecieveAddress(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid, @Path("addressid") String addressid);

  /**
   * 更新收件地址信息
   * 
   * @param token
   * @param userid
   * @param addressid
   * @param request
   * @return
   */
  @PUT(value = "/user/{userid}/recieveaddress/{addressid}")
  public Call<ResponseBean> updateRecieveAddress(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid, @Path("addressid") String addressid, @Body RecieveAddress request);

  /**
   * 废弃收件地址
   * 
   * @param token
   * @param userid
   * @param addressid
   * @return
   */
  @DELETE(value = "/user/{userid}/recieveaddress/{addressid}")
  public Call<ResponseBean> deprecateRecieveAddress(@Header(AuthConstants.HEADER_STRING) String token,
      @Path("userid") String userid, @Path("addressid") String addressid);
}

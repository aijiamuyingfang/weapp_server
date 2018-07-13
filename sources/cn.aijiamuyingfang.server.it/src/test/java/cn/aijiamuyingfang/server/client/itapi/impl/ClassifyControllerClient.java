package cn.aijiamuyingfang.server.client.itapi.impl;

import cn.aijiamuyingfang.server.client.annotation.HttpService;
import cn.aijiamuyingfang.server.client.itapi.ClassifyControllerApi;
import cn.aijiamuyingfang.server.commons.controller.bean.ResponseBean;
import cn.aijiamuyingfang.server.commons.utils.JsonUtils;
import cn.aijiamuyingfang.server.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.domain.exception.GoodsException;
import cn.aijiamuyingfang.server.domain.goods.Classify;
import cn.aijiamuyingfang.server.domain.goods.ClassifyRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

/**
 * [描述]:
 * <p>
 * 客户端调用ClassifyController的服务
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-12 05:59:49
 */
@Service
@SuppressWarnings("rawtypes")
public class ClassifyControllerClient {
  private static final Logger LOGGER = LogManager.getLogger(ClassifyControllerClient.class);

  private static final Callback<ResponseBean> Empty_Callback = new Callback<ResponseBean>() {

    @Override
    public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
      LOGGER.info("onResponse:" + response.message());
    }

    @Override
    public void onFailure(Call<ResponseBean> call, Throwable t) {
      LOGGER.error(t.getMessage(), t);
    }
  };

  @HttpService
  private ClassifyControllerApi classifyControllerApi;

  /**
   * 获取门店下所有的顶层条目
   * 
   * @param token
   * @param storeid
   * @return
   * @throws IOException
   */
  public List<Classify> getStoreTopClassifyList(String token, @Path(value = "storeid") String storeid)
      throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getStoreTopClassifyList(token, storeid).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      List<Classify> classifyList = JsonUtils.json2List(JsonUtils.list2Json((List<?>) returnData), Classify.class);
      if (null == classifyList) {
        throw new RuntimeException("get store top classify list return code is '200',but return data is null");
      }
      return classifyList;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 获取某个条目
   * 
   * @param token
   * @param classifyid
   * @return
   * @throws IOException
   */
  public Classify getClassify(String token, String classifyid) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getClassify(token, classifyid).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      Classify classify = JsonUtils.json2Bean(JsonUtils.map2Json((Map<?, ?>) returnData), Classify.class);
      if (null == classify) {
        throw new RuntimeException("get classify  return code is '200',but return data is null");
      }
      return classify;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 废弃条目
   * 
   * @param token
   * @param classifyid
   * @param async
   * @throws IOException
   */
  public void deleteClassify(String token, String classifyid, boolean async) throws IOException {
    if (async) {
      classifyControllerApi.deleteClassify(token, classifyid).enqueue(Empty_Callback);
      return;
    }
    Response<ResponseBean> response = classifyControllerApi.deleteClassify(token, classifyid).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    if ("200".equals(returnCode)) {
      return;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 创建顶层条目
   * 
   * @param token
   * @param request
   * @return
   * @throws IOException
   */
  public Classify createTopClassify(String token, ClassifyRequest request) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.createTopClassify(token, request).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      Classify classify = JsonUtils.json2Bean(JsonUtils.map2Json((Map<?, ?>) returnData), Classify.class);
      if (null == classify) {
        throw new RuntimeException("create top classify  return code is '200',but return data is null");
      }
      return classify;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 异步创建顶层条目
   * 
   * @param token
   * @param request
   * @param callback
   */
  public void createTopClassifyAsync(String token, ClassifyRequest request, Callback<ResponseBean> callback) {
    classifyControllerApi.createTopClassify(token, request).enqueue(callback);
  }

  /**
   * 获得条目下的所有子条目
   * 
   * @param token
   * @param classifyid
   * @return
   * @throws IOException
   */
  public List<Classify> getSubClassifyList(String token, String classifyid) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getSubClassifyList(token, classifyid).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      List<Classify> classifyList = JsonUtils.json2List(JsonUtils.list2Json((List<?>) returnData), Classify.class);
      if (null == classifyList) {
        throw new RuntimeException("get sub classify list return code is '200',but return data is null");
      }
      return classifyList;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 创建子条目
   * 
   * @param token
   * @param classifyid
   * @param coverImageFile
   * @param classifyRequest
   * @return
   * @throws IOException
   */
  public Classify createSubClassify(String token, String classifyid, File coverImageFile,
      ClassifyRequest classifyRequest) throws IOException {
    Response<ResponseBean> response = null;
    if (null == coverImageFile) {
      response = classifyControllerApi.createSubClassify(token, classifyid, convert(null, classifyRequest)).execute();
    } else {
      response = classifyControllerApi.createSubClassify(token, classifyid, convert(coverImageFile, classifyRequest))
          .execute();
    }
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      Classify classify = JsonUtils.json2Bean(JsonUtils.map2Json((Map<?, ?>) returnData), Classify.class);
      if (null == classify) {
        throw new RuntimeException("create sub classify  return code is '200',but return data is null");
      }
      return classify;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 异步创建子条目
   * 
   * @param token
   * @param classifyid
   * @param coverImageFile
   * @param classifyRequest
   * @param callback
   * @throws IOException
   */
  public void createSubClassifyAsync(String token, String classifyid, File coverImageFile,
      ClassifyRequest classifyRequest, Callback<ResponseBean> callback) throws IOException {
    if (null == coverImageFile) {
      classifyControllerApi.createSubClassify(token, classifyid, convert(null, classifyRequest)).enqueue(callback);
    } else {
      classifyControllerApi.createSubClassify(token, classifyid, convert(coverImageFile, classifyRequest))
          .enqueue(callback);
    }
  }

  private MultipartBody convert(File coverImageFile, ClassifyRequest classifyRequest) {
    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    if (coverImageFile != null) {
      RequestBody requestCoverImg = RequestBody.create(MediaType.parse("multipart/form-data"), coverImageFile);
      requestBodyBuilder.addFormDataPart("coverImage", coverImageFile.getName(), requestCoverImg);
    }
    if (null == classifyRequest) {
      return requestBodyBuilder.build();
    }
    if (StringUtils.hasContent(classifyRequest.getName())) {
      requestBodyBuilder.addFormDataPart("name", classifyRequest.getName());
    }
    return requestBodyBuilder.build();
  }

  /**
   * 条目下添加商品
   * 
   * 
   * @param token
   * @param classifyid
   * @param goodid
   * @param async
   * @throws IOException
   */
  public void addClassifyGood(String token, String classifyid, String goodid, boolean async) throws IOException {
    if (async) {
      classifyControllerApi.addClassifyGood(token, classifyid, goodid).enqueue(Empty_Callback);
      return;
    }
    Response<ResponseBean> response = classifyControllerApi.addClassifyGood(token, classifyid, goodid).execute();
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      throw new RuntimeException("response body is null");
    }
    String returnCode = responseBean.getCode();
    if ("200".equals(returnCode)) {
      return;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }
}

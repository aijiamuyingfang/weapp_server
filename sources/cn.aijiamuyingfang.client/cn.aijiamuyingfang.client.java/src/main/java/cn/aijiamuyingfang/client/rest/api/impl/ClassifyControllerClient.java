package cn.aijiamuyingfang.client.rest.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.aijiamuyingfang.client.commons.constant.ClientRestConstants;
import cn.aijiamuyingfang.client.rest.annotation.HttpService;
import cn.aijiamuyingfang.client.rest.api.ClassifyControllerApi;
import cn.aijiamuyingfang.client.rest.utils.ResponseUtils;
import cn.aijiamuyingfang.vo.classify.Classify;
import cn.aijiamuyingfang.vo.exception.GoodsException;
import cn.aijiamuyingfang.vo.response.ResponseBean;
import cn.aijiamuyingfang.vo.response.ResponseCode;
import cn.aijiamuyingfang.vo.utils.JsonUtils;
import cn.aijiamuyingfang.vo.utils.StringUtils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
      LOGGER.info("onResponse:{}", response.message());
    }

    @Override
    public void onFailure(Call<ResponseBean> call, Throwable t) {
      LOGGER.error(t.getMessage(), t);
    }
  };

  @HttpService
  private ClassifyControllerApi classifyControllerApi;

  /**
   * 获取所有顶层条目
   * 
   * @return
   * @throws IOException
   */
  public List<Classify> getTopClassifyList() throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getTopClassifyList().execute();
    return getClassifyListFromResponse(response, "get top classify list return code is '200',but return data is null");
  }

  /**
   * 从response中获取List<Classify>
   * 
   * @param response
   * @param errormsg
   * @return
   * @throws IOException
   */
  private List<Classify> getClassifyListFromResponse(Response<ResponseBean> response, String errormsg)
      throws IOException {
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      if (response.errorBody() != null) {
        LOGGER.error(new String(response.errorBody().bytes()));
      }
      throw new GoodsException(ResponseCode.RESPONSE_BODY_IS_NULL);
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      List<Classify> classifyList = JsonUtils.json2List(JsonUtils.list2Json((List<?>) returnData), Classify.class);
      if (null == classifyList) {
        throw new GoodsException("500", errormsg);
      }
      return classifyList;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 获取某个条目
   * 
   * @param classifyId
   * @return
   * @throws IOException
   */
  public Classify getClassify(String classifyId) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getClassify(classifyId).execute();
    return getClassifyFromResponse(response, "get classify  return code is '200',but return data is null");
  }

  /**
   * 从response中获取Classify
   * 
   * @param response
   * @param errormsg
   * @return
   * @throws IOException
   */
  private Classify getClassifyFromResponse(Response<ResponseBean> response, String errormsg) throws IOException {
    ResponseBean responseBean = response.body();
    if (null == responseBean) {
      if (response.errorBody() != null) {
        LOGGER.error(new String(response.errorBody().bytes()));
      }
      throw new GoodsException(ResponseCode.RESPONSE_BODY_IS_NULL);
    }
    String returnCode = responseBean.getCode();
    Object returnData = responseBean.getData();
    if ("200".equals(returnCode)) {
      Classify classify = JsonUtils.json2Bean(JsonUtils.map2Json((Map<?, ?>) returnData), Classify.class);
      if (null == classify) {
        throw new GoodsException("500", errormsg);
      }
      return classify;
    }
    LOGGER.error(responseBean.getMsg());
    throw new GoodsException(returnCode, responseBean.getMsg());
  }

  /**
   * 废弃条目
   * 
   * @param classifyId
   * @param accessToken
   * @param async
   * @throws IOException
   */
  public void deleteClassify(String classifyId, String accessToken, boolean async) throws IOException {
    if (async) {
      classifyControllerApi.deleteClassify(classifyId, accessToken).enqueue(Empty_Callback);
      return;
    }
    ResponseUtils.handleGoodsVOIDResponse(classifyControllerApi.deleteClassify(classifyId, accessToken).execute(),
        LOGGER);
  }

  /**
   * 创建顶层条目
   * 
   * @param request
   * @param accessToken
   * @return
   * @throws IOException
   */
  public Classify createTopClassify(Classify request, String accessToken) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.createTopClassify(request, accessToken).execute();
    return getClassifyFromResponse(response, "create top classify  return code is '200',but return data is null");
  }

  /**
   * 异步创建顶层条目
   * 
   * @param request
   * @param accessToken
   * @param callback
   */
  public void createTopClassifyAsync(Classify request, String accessToken, Callback<ResponseBean> callback) {
    classifyControllerApi.createTopClassify(request, accessToken).enqueue(callback);
  }

  /**
   * 获得条目下的所有子条目
   * 
   * @param classifyId
   * @return
   * @throws IOException
   */
  public List<Classify> getSubClassifyList(String classifyId) throws IOException {
    Response<ResponseBean> response = classifyControllerApi.getSubClassifyList(classifyId).execute();
    return getClassifyListFromResponse(response, "get sub classify list return code is '200',but return data is null");
  }

  /**
   * 创建子条目
   * 
   * @param classifyId
   * @param coverImageFile
   * @param classifyRequest
   * @param accessToken
   * @return
   * @throws IOException
   */
  public Classify createSubClassify(String classifyId, File coverImageFile, Classify classifyRequest,
      String accessToken) throws IOException {
    Response<ResponseBean> response = null;
    if (null == coverImageFile) {
      response = classifyControllerApi.createSubClassify(classifyId, convert(null, classifyRequest), accessToken)
          .execute();
    } else {
      response = classifyControllerApi
          .createSubClassify(classifyId, convert(coverImageFile, classifyRequest), accessToken).execute();
    }
    return getClassifyFromResponse(response, "create sub classify  return code is '200',but return data is null");
  }

  /**
   * 异步创建子条目
   * 
   * @param classifyId
   * @param coverImageFile
   * @param classifyRequest
   * @param accessToken
   * @param callback
   */
  public void createSubClassifyAsync(String classifyId, File coverImageFile, Classify classifyRequest,
      String accessToken, Callback<ResponseBean> callback) {
    if (null == coverImageFile) {
      classifyControllerApi.createSubClassify(classifyId, convert(null, classifyRequest), accessToken)
          .enqueue(callback);
    } else {
      classifyControllerApi.createSubClassify(classifyId, convert(coverImageFile, classifyRequest), accessToken)
          .enqueue(callback);
    }
  }

  private MultipartBody convert(File coverImageFile, Classify classifyRequest) {
    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    if (coverImageFile != null) {
      RequestBody requestCoverImg = RequestBody.create(ClientRestConstants.MEDIA_TYPE_MULTIPART, coverImageFile);
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
   * @param classifyId
   * @param goodId
   * @param accessToken
   * @param async
   * @throws IOException
   */
  public void addClassifyGood(String classifyId, String goodId, String accessToken, boolean async) throws IOException {
    if (async) {
      classifyControllerApi.addClassifyGood(classifyId, goodId, accessToken).enqueue(Empty_Callback);
      return;
    }
    ResponseUtils.handleGoodsVOIDResponse(
        classifyControllerApi.addClassifyGood(classifyId, goodId, accessToken).execute(), LOGGER);
  }
}

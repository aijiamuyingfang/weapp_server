package cn.aijiamuyingfang.commons.domain.response;

/**
 * [描述]:
 * <p>
 * 返回码
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-26 05:04:23
 */
public enum ResponseCode {
  /**
   * 门店不存在
   */
  STORE_NOT_EXIST("404", "Store[%s] not exist"),
  /**
   * 条目不存在
   */
  CLASSIFY_NOT_EXIST("404", "Classify[%s] not exist"),
  /**
   * 商品不存在
   */
  GOOD_NOT_EXIST("404", "Good[%s] not exist"),

  /**
   * 商品详细信息不存在
   */
  GOODDETAIL_NOT_EXIST("404", "GoodDetail[%s] not exist"),
  /**
   * 用户不存在
   */
  USER_NOT_EXIST("404", "User[%s] not exist"),

  /**
   * 收件地址不存在
   */
  RECIEVEADDRESS_NOT_EXIST("404", "RecieveAddress[%s] not exist"),
  /**
   * 预览不存在
   */
  PREVIEWORDER_NOT_EXIST("404", "user[%s] preview order not exist"),
  /**
   * 预览项不存在
   */
  PREVIEWORDERITEM_NOT_EXIST("404", "preview order item[%s] not exist"),
  /**
   * 订单不存在
   */
  SHOPORDER_NOT_EXIST("404", "shoporder[%s] not exist"),
  /**
   * 订单项不存在
   */
  SHOPORDERITEM_NOT_EXIST("404", "shoporder item[%s] not exist"),

  /**
   * 订单不属于用户
   */
  SHOPORDER_NOT_BELONGTO_USER("500", "shoporder[%s] not belong to user[%]"),

  /**
   * 调用'GET /wxservice/wxsession'返回null
   */
  GET_WXSESSION_NULL("500", "get null response from 'GET /wxservice/wxsession'"),

  /**
   * 调用'GET /wxservice/wxsession'返回错误信息
   */
  GET_WXSESSION_ERR("500", "get error from 'GET /wxservice/wxsession':%s"),

  /**
   * 返回的openid为空(null或者空字符串)
   */
  GET_OPENID_NULL("500", "openid is empty"),

  /**
   * 连接微信服务器失败
   */
  USER_SESSION_WEIXIN_NET_ERR("006", "连接微信服务器失败"),

  /**
   * CODE无效
   */
  USER_SESSION_WEIXIN_CODE_ERR("007", "jscode无效"),

  /**
   * 微信返回值错误
   */
  USER_SESSION_RETURN_ERR("008", " 微信返回值错误"),
  /**
   * 运行时异常
   */
  RUNTIME_EXCEPTION("500", "%s"),
  /**
   * 请求返回体是null
   */
  RESPONSE_BODY_IS_NULL("500", "response body is null"),

  /**
   * 请求正常
   */
  OK("200", "SUCCESS");

  private String code;

  private String message;

  ResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage(Object... args) {
    return String.format(this.message, args);
  }
}
package cn.aijiamuyingfang.server.client;

import cn.aijiamuyingfang.client.rest.api.impl.AuthControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.ClassifyControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.CouponControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.GoodControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.PreviewOrderControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.ShopCartControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.ShopOrderControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.StoreControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.UserControllerClient;
import cn.aijiamuyingfang.client.rest.api.impl.UserMessageControllerClient;
import cn.aijiamuyingfang.commons.constants.AuthConstants;
import cn.aijiamuyingfang.commons.domain.address.City;
import cn.aijiamuyingfang.commons.domain.address.County;
import cn.aijiamuyingfang.commons.domain.address.Province;
import cn.aijiamuyingfang.commons.domain.address.RecieveAddress;
import cn.aijiamuyingfang.commons.domain.address.Town;
import cn.aijiamuyingfang.commons.domain.goods.Classify;
import cn.aijiamuyingfang.commons.domain.goods.Good;
import cn.aijiamuyingfang.commons.domain.shopcart.AddShopCartItemRequest;
import cn.aijiamuyingfang.commons.domain.shopcart.ShopCartItem;
import cn.aijiamuyingfang.commons.domain.shoporder.PreviewOrder;
import cn.aijiamuyingfang.commons.domain.shoporder.SendType;
import cn.aijiamuyingfang.commons.domain.shoporder.ShopOrder;
import cn.aijiamuyingfang.commons.domain.shoporder.ShopOrderStatus;
import cn.aijiamuyingfang.commons.domain.shoporder.request.CreateUserShoprderRequest;
import cn.aijiamuyingfang.commons.domain.shoporder.request.UpdateShopOrderStatusRequest;
import cn.aijiamuyingfang.commons.domain.shoporder.response.ConfirmUserShopOrderFinishedResponse;
import cn.aijiamuyingfang.commons.domain.user.User;
import cn.aijiamuyingfang.commons.domain.user.response.TokenResponse;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.domain.address.db.RecieveAddressRepository;
import cn.aijiamuyingfang.server.domain.coupon.db.GoodVoucherRepository;
import cn.aijiamuyingfang.server.domain.coupon.db.UserVoucherRepository;
import cn.aijiamuyingfang.server.domain.coupon.db.VoucherItemRepository;
import cn.aijiamuyingfang.server.domain.goods.db.ClassifyRepository;
import cn.aijiamuyingfang.server.domain.goods.db.GoodDetailRepository;
import cn.aijiamuyingfang.server.domain.goods.db.GoodRepository;
import cn.aijiamuyingfang.server.domain.goods.db.StoreRepository;
import cn.aijiamuyingfang.server.domain.shopcart.db.ShopCartItemRepository;
import cn.aijiamuyingfang.server.domain.shoporder.db.PreviewOrderItemRepository;
import cn.aijiamuyingfang.server.domain.shoporder.db.PreviewOrderRepository;
import cn.aijiamuyingfang.server.domain.shoporder.db.ShopOrderRepository;
import cn.aijiamuyingfang.server.domain.user.db.UserMessageRepository;
import cn.aijiamuyingfang.server.domain.user.db.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * [描述]:
 * <p>
 * 测试动作集
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-19 18:13:01
 */
public abstract class AbstractTestAction {
  /**
   * Admin用户的Id
   */
  public static final String ADMIN_USER_ID = "5c6a132a-829f-11e8-96fc-00cfe0430e2a";

  /**
   * Admin用户的JWT
   */
  public static final String ADMIN_USER_TOKEN;

  static {
    Map<String, Object> claims = new HashMap<>();
    claims.put(AuthConstants.CLAIM_KEY_USERNAME, ADMIN_USER_ID);
    claims.put(AuthConstants.CLAIM_KEY_CREATED, new Date());
    ADMIN_USER_TOKEN = AuthConstants.TOKEN_PREFIX + Jwts.builder().setClaims(claims)
        .setExpiration(new Date(System.currentTimeMillis() + AuthConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, System.getenv("weapp_jwt_secret")).compact();
  }

  @Autowired
  public UserRepository userRepository;

  @Autowired
  public UserMessageRepository usermessageRepository;

  @Autowired
  public RecieveAddressRepository recieveaddressRepository;

  @Autowired
  public StoreRepository storeRepository;

  @Autowired
  public ClassifyRepository classifyRepository;

  @Autowired
  public GoodRepository goodRepository;

  @Autowired
  public GoodDetailRepository gooddetailRepository;

  @Autowired
  public ShopCartItemRepository shopcartitemRepository;

  @Autowired
  public PreviewOrderRepository previeworderRepository;

  @Autowired
  public PreviewOrderItemRepository preivewitemRepository;

  @Autowired
  public GoodVoucherRepository goodvoucherRepository;

  @Autowired
  public UserVoucherRepository uservoucherRepository;

  @Autowired
  public VoucherItemRepository voucheritemRepository;

  @Autowired
  public ShopOrderRepository shoporderRepository;

  @Autowired
  public UserControllerClient userControllerClient;

  @Autowired
  public UserMessageControllerClient usermessageControllerClient;

  @Autowired
  public AuthControllerClient authControllerClient;

  @Autowired
  public StoreControllerClient storeControllerClient;

  @Autowired
  public ClassifyControllerClient classifyControllerClient;

  @Autowired
  public GoodControllerClient goodControllerClient;

  @Autowired
  public CouponControllerClient couponControllerClient;

  @Autowired
  public ShopCartControllerClient shopcartControllerClient;

  @Autowired
  public PreviewOrderControllerClient previewOrderControllerClient;

  @Autowired
  public ShopOrderControllerClient shoporderControllerClient;

  public String senderoneId;

  public String senderoneToken;

  public User createSenderOneUser() throws IOException {
    User createSenderRequest = new User();
    createSenderRequest.setJscode("senderonejscode");
    createSenderRequest.setPhone("11111111111");
    createSenderRequest.setNickname("SenderOne User NickName");
    User user = authControllerClient.registerUser(ADMIN_USER_TOKEN, createSenderRequest);
    if (user != null) {
      senderoneId = user.getId();
    }
    return user;
  }

  public String getSenderOneToken() throws IOException {
    TokenResponse tokenResponse = authControllerClient.getToken("senderonejscode", null, null);
    if (tokenResponse != null) {
      senderoneToken = AuthConstants.TOKEN_PREFIX + tokenResponse.getToken();
      return senderoneToken;
    }
    return null;
  }

  public String sendertwoId;

  public String sendertwoToken;

  public User createSenderTwoUser() throws IOException {
    User createSenderRequest = new User();
    createSenderRequest.setJscode("sendertwojscode");
    createSenderRequest.setPhone("22222222");
    createSenderRequest.setNickname("SenderTwo User NickName");
    User user = authControllerClient.registerUser(ADMIN_USER_TOKEN, createSenderRequest);
    if (user != null) {
      sendertwoId = user.getId();
    }
    return user;
  }

  public String getSenderTwoToken() throws IOException {
    TokenResponse tokenResponse = authControllerClient.getToken("sendertwojscode", null, null);
    if (tokenResponse != null) {
      sendertwoToken = AuthConstants.TOKEN_PREFIX + tokenResponse.getToken();
      return sendertwoToken;
    }
    return null;
  }

  public String senderoneRecieveAddressOneId;

  public RecieveAddress addSenderOneRecieveOne() throws IOException {
    RecieveAddress recieveaddressRequest = new RecieveAddress();
    recieveaddressRequest.setProvince(new Province("jiangsu", "210000"));
    recieveaddressRequest.setCity(new City("nanjing", "210100"));
    recieveaddressRequest.setCounty(new County("yuhuaqiao", "210101"));
    recieveaddressRequest.setTown(new Town("tiexinqiao", "000001"));
    recieveaddressRequest.setDef(true);
    recieveaddressRequest.setPhone("11111111111");
    RecieveAddress address = userControllerClient.addUserRecieveAddress(senderoneToken, senderoneId,
        recieveaddressRequest);
    if (address != null) {
      senderoneRecieveAddressOneId = address.getId();
    }
    return address;

  }

  public String sendertwoRecieveAddressOneId;

  public RecieveAddress addSenderTwoRecieveOne() throws IOException {
    RecieveAddress recieveaddressRequest = new RecieveAddress();
    recieveaddressRequest.setProvince(new Province("jiangsu", "210000"));
    recieveaddressRequest.setCity(new City("nanjing", "210100"));
    recieveaddressRequest.setCounty(new County("yuhuaqiao", "210101"));
    recieveaddressRequest.setTown(new Town("tiexinqiao", "000001"));
    recieveaddressRequest.setDef(true);
    recieveaddressRequest.setPhone("11111111111");
    RecieveAddress address = userControllerClient.addUserRecieveAddress(senderoneToken, senderoneId,
        recieveaddressRequest);
    if (address != null) {
      sendertwoRecieveAddressOneId = address.getId();
    }
    return address;

  }

  public ShopCartItem senderoneAdd5GoodOne() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodoneId);
    addShopcartItemRequest.setGoodNum(5);
    return shopcartControllerClient.addShopCartItem(senderoneToken, senderoneId, addShopcartItemRequest);
  }

  public ShopCartItem senderoneAdd5GoodTwo() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodtwoId);
    addShopcartItemRequest.setGoodNum(5);
    return shopcartControllerClient.addShopCartItem(senderoneToken, senderoneId, addShopcartItemRequest);
  }

  public ShopCartItem senderoneAdd10GoodOne() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodoneId);
    addShopcartItemRequest.setGoodNum(10);
    return shopcartControllerClient.addShopCartItem(senderoneToken, senderoneId, addShopcartItemRequest);
  }

  public ShopCartItem senderoneAdd10GoodTwo() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodtwoId);
    addShopcartItemRequest.setGoodNum(10);
    return shopcartControllerClient.addShopCartItem(senderoneToken, senderoneId, addShopcartItemRequest);
  }

  public ShopCartItem sendertwoAdd10GoodOne() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodoneId);
    addShopcartItemRequest.setGoodNum(10);
    return shopcartControllerClient.addShopCartItem(sendertwoToken, sendertwoId, addShopcartItemRequest);
  }

  public ShopCartItem sendertwoAdd10GoodTwo() throws IOException {
    AddShopCartItemRequest addShopcartItemRequest = new AddShopCartItemRequest();
    addShopcartItemRequest.setGoodid(goodtwoId);
    addShopcartItemRequest.setGoodNum(10);
    return shopcartControllerClient.addShopCartItem(sendertwoToken, sendertwoId, addShopcartItemRequest);
  }

  public String senderoneShoporderId;

  public ShopOrder senderoneBuy() throws IOException {
    CreateUserShoprderRequest shoporderRequest = new CreateUserShoprderRequest();
    shoporderRequest.setSendtype(SendType.THIRDSEND);
    shoporderRequest.setAddressid(senderoneRecieveAddressOneId);
    ShopOrder shoporder = shoporderControllerClient.createUserShopOrder(senderoneToken, senderoneId, shoporderRequest);
    if (shoporder != null) {
      senderoneShoporderId = shoporder.getId();
    }
    return shoporder;
  }

  public String sendertwoShoporderId;

  public ShopOrder sendertwoBuy() throws IOException {
    CreateUserShoprderRequest shoporderRequest = new CreateUserShoprderRequest();
    shoporderRequest.setSendtype(SendType.THIRDSEND);
    shoporderRequest.setAddressid(sendertwoRecieveAddressOneId);
    ShopOrder shoporder = shoporderControllerClient.createUserShopOrder(sendertwoToken, sendertwoId, shoporderRequest);
    if (shoporder != null) {
      sendertwoShoporderId = shoporder.getId();
    }
    return shoporder;
  }

  public void sendSenderOneShopOrder() throws IOException {
    UpdateShopOrderStatusRequest updateShoporderStatusRequest = new UpdateShopOrderStatusRequest();
    updateShoporderStatusRequest.setOperator("Sender");
    updateShoporderStatusRequest.setStatus(ShopOrderStatus.DOING);
    updateShoporderStatusRequest.setThirdsendCompany("send company");
    updateShoporderStatusRequest.setThirdsendno("111111111111");
    shoporderControllerClient.updateShopOrderStatus(senderoneToken, senderoneShoporderId, updateShoporderStatusRequest,
        false);
  }

  public ConfirmUserShopOrderFinishedResponse senderoneConfirmOrder() throws IOException {
    return shoporderControllerClient.confirmUserShopOrderFinished(senderoneToken, senderoneId, senderoneShoporderId);
  }

  public String goodoneId;

  public Good createGoodOne() throws IOException {
    Good goodRequest = new Good();
    goodRequest.setName("good 1");
    goodRequest.setCount(100);
    goodRequest.setPrice(100);
    goodRequest.setBarcode("111111111111");
    Good good = goodControllerClient.createGood(ADMIN_USER_TOKEN, null, null, goodRequest);
    if (good != null) {
      goodoneId = good.getId();
    }
    return good;
  }

  public String goodtwoId;

  public Good createGoodTwo() throws IOException {
    Good goodRequest = new Good();
    goodRequest.setName("good 2");
    goodRequest.setCount(100);
    goodRequest.setPrice(100);
    goodRequest.setBarcode("2222222222");
    Good good = goodControllerClient.createGood(ADMIN_USER_TOKEN, null, null, goodRequest);
    if (good != null) {
      goodtwoId = good.getId();
    }
    return good;
  }

  public PreviewOrder senderonePreviewGoodOne() throws IOException {
    List<String> goodids = new ArrayList<>();
    goodids.add(goodoneId);
    return previewOrderControllerClient.generatePreviewOrder(senderoneToken, senderoneId, goodids);
  }

  public PreviewOrder senderonePreviewAllGood() throws IOException {
    List<String> goodids = new ArrayList<>();
    goodids.add(goodoneId);
    goodids.add(goodtwoId);
    return previewOrderControllerClient.generatePreviewOrder(senderoneToken, senderoneId, goodids);
  }

  public PreviewOrder sendertwoPreviewAllGood() throws IOException {
    List<String> goodids = new ArrayList<>();
    goodids.add(goodoneId);
    goodids.add(goodtwoId);
    return previewOrderControllerClient.generatePreviewOrder(sendertwoToken, sendertwoId, goodids);
  }

  public void clearData() {
    storeRepository.deleteAll();
    shoporderRepository.deleteAll();
    previeworderRepository.deleteAll();
    preivewitemRepository.deleteAll();
    recieveaddressRepository.deleteAll();
    shopcartitemRepository.deleteAll();
    usermessageRepository.deleteAll();
    if (StringUtils.hasContent(senderoneId)) {
      userRepository.delete(senderoneId);
    }
    if (StringUtils.hasContent(sendertwoId)) {
      userRepository.delete(sendertwoId);
    }
    List<Classify> classifyList = classifyRepository.findByLevel(1);
    classifyRepository.delete(classifyList);
    gooddetailRepository.deleteAll();
    goodRepository.deleteAll();
    uservoucherRepository.deleteAll();
    goodvoucherRepository.deleteAll();
    voucheritemRepository.deleteAll();

    senderoneId = null;
    sendertwoId = null;
    senderoneToken = null;
    sendertwoToken = null;

    senderoneShoporderId = null;
    sendertwoShoporderId = null;

    senderoneRecieveAddressOneId = null;
    sendertwoRecieveAddressOneId = null;

    goodoneId = null;
    goodtwoId = null;

  }
}
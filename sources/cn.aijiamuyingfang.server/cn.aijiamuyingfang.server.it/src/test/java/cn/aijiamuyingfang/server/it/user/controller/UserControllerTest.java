package cn.aijiamuyingfang.server.it.user.controller;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.aijiamuyingfang.client.rest.api.impl.UserControllerClient;
import cn.aijiamuyingfang.server.it.AbstractTestAction;
import cn.aijiamuyingfang.server.it.ITApplication;
import cn.aijiamuyingfang.server.it.UseCaseDescription;
import cn.aijiamuyingfang.vo.exception.UserException;
import cn.aijiamuyingfang.vo.user.RecieveAddress;
import cn.aijiamuyingfang.vo.user.User;
import cn.aijiamuyingfang.vo.user.UserAuthority;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = ITApplication.class)
public class UserControllerTest {

  @Autowired
  private UserTestActions testActions;

  @Autowired
  private UserControllerClient userControllerClient;

  @Before
  public void before() throws IOException {
    testActions.clearUserMessage();
    testActions.clearUser();
    testActions.clearRecieveAddress();
  }

  @After
  public void after() {
    testActions.clearUserMessage();
    testActions.clearUser();
    testActions.clearRecieveAddress();
  }

  @Test(expected = UserException.class)
  @UseCaseDescription(description = "获取不存在的用户")
  public void test_GetUser_001() throws IOException {
    userControllerClient.getUser("not_exist_user", testActions.getAdminAccessToken());
    Assert.fail();
  }

  @Test
  @UseCaseDescription(description = "获取存在的用户")
  public void test_GetUser_002() throws IOException {
    User user = userControllerClient.getUser(UserTestActions.ADMIN_USER_NAME, testActions.getAdminAccessToken());
    Assert.assertNotNull(user);
    Assert.assertEquals(UserTestActions.ADMIN_USER_NAME, user.getUsername());
  }

  @Test
  @UseCaseDescription(description = "更新用户")
  public void test_UpdateUser_001() throws IOException {
    User senderOne = testActions.getSenderOne();
    User user = userControllerClient.getUser(senderOne.getUsername(), testActions.getSenderOneAccessToken());
    Assert.assertNotNull(user);
    Assert.assertEquals("11111111111", user.getPhone());

    User updateUserRequest = new User();
    updateUserRequest.setPhone("88888888");
    user = userControllerClient.updateUser(senderOne.getUsername(), updateUserRequest,
        testActions.getSenderOneAccessToken());
    Assert.assertEquals("88888888", user.getPhone());

  }

  @Test
  @UseCaseDescription(description = "用户没有收件地址")
  public void test_GetUserRecieveAddressList_001() throws IOException {
    User senderOne = testActions.getSenderOne();
    List<RecieveAddress> addressList = userControllerClient.getUserRecieveAddressList(senderOne.getUsername(),
        testActions.getSenderOneAccessToken());
    Assert.assertNotNull(addressList);
    Assert.assertEquals(0, addressList.size());
  }

  @Test
  @UseCaseDescription(description = "用户有收件地址")
  public void test_GetUserRecieveAddressList_002() throws IOException {
    User senderOne = testActions.getSenderOne();
    RecieveAddress recieveAddressOne = testActions.getSenderOneRecieveOne();
    Assert.assertNotNull(recieveAddressOne);

    List<RecieveAddress> addressList = userControllerClient.getUserRecieveAddressList(senderOne.getUsername(),
        testActions.getSenderOneAccessToken());
    Assert.assertNotNull(addressList);
    Assert.assertEquals(1, addressList.size());
    Assert.assertEquals(recieveAddressOne.getId(), addressList.get(0).getId());

    RecieveAddress recieveaddress = userControllerClient.getRecieveAddress(senderOne.getUsername(),
        recieveAddressOne.getId(), testActions.getSenderOneAccessToken());
    Assert.assertNotNull(recieveaddress);
    Assert.assertEquals(recieveAddressOne.getId(), recieveaddress.getId());

    RecieveAddress updateRecieveAddressRequest = new RecieveAddress();
    updateRecieveAddressRequest.setReciever("SSSSSSSS");
    userControllerClient.updateRecieveAddress(senderOne.getUsername(), recieveAddressOne.getId(),
        updateRecieveAddressRequest, testActions.getSenderOneAccessToken());

    recieveaddress = userControllerClient.getRecieveAddress(senderOne.getUsername(), recieveAddressOne.getId(),
        testActions.getSenderOneAccessToken());
    Assert.assertEquals("SSSSSSSS", recieveaddress.getReciever());

    userControllerClient.deprecateRecieveAddress(senderOne.getUsername(), recieveAddressOne.getId(),
        testActions.getSenderOneAccessToken(), false);

    addressList = userControllerClient.getUserRecieveAddressList(senderOne.getUsername(),
        testActions.getSenderOneAccessToken());
    Assert.assertEquals(0, addressList.size());
  }

  @Test
  @UseCaseDescription(description = "注册普通用户")
  public void test_Register_User_001() throws IOException {
    User user = new User();
    user.setUsername("userone");
    user.setPassword("passwordone");
    user.addAuthority(UserAuthority.BUYER_PERMISSION);
    User userone = userControllerClient.registerUser(user, testActions.getAdminAccessToken());
    User actualUser = userControllerClient.getUser(userone.getUsername(), testActions.getAdminAccessToken());
    Assert.assertEquals(userone.getUsername(), actualUser.getUsername());
    Assert.assertEquals(userone.getPassword(), actualUser.getPassword());
    Assert.assertEquals(UserAuthority.BUYER_PERMISSION, actualUser.getAuthorityList().get(0));
  }

  @Test
  @UseCaseDescription(description = "注冊管理員")
  public void test_Register_User_002() throws IOException {
    User user = new User();
    user.setUsername("managerone");
    user.setPassword("passwordone");
    user.addAuthority(UserAuthority.MANAGER_PERMISSION);
    User managerone = userControllerClient.registerUser(user, testActions.getAdminAccessToken());
    User actualUser = userControllerClient.getUser(managerone.getUsername(), testActions.getAdminAccessToken());
    Assert.assertEquals(managerone.getUsername(), actualUser.getUsername());
    Assert.assertEquals(managerone.getPassword(), actualUser.getPassword());
    Assert.assertEquals(UserAuthority.MANAGER_PERMISSION, actualUser.getAuthorityList().get(0));
  }

  @Test
  @UseCaseDescription(description = "注冊送貨員")
  public void test_Register_User_003() throws IOException {
    User user = new User();
    user.setUsername("senderone");
    user.setPassword("passwordone");
    user.addAuthority(UserAuthority.SENDER_PERMISSION);
    User senderone = userControllerClient.registerUser(user, testActions.getAdminAccessToken());
    User actualUser = userControllerClient.getUser(senderone.getUsername(), testActions.getAdminAccessToken());
    Assert.assertEquals(senderone.getUsername(), actualUser.getUsername());
    Assert.assertEquals(senderone.getPassword(), actualUser.getPassword());
    Assert.assertEquals(UserAuthority.SENDER_PERMISSION, actualUser.getAuthorityList().get(0));
  }

  @Test
  @UseCaseDescription(description = "用户未提供电话")
  public void test_getUserPhone_001() throws IOException {
    String phone = userControllerClient.getUserPhone(AbstractTestAction.ADMIN_USER_NAME,
        testActions.getAdminAccessToken());
    Assert.assertNull(phone);
  }

  @Test
  @UseCaseDescription(description = "用户提供了电话")
  public void test_getUserPhone_002() throws IOException {
    User user = new User();
    user.setPhone("11111111111");
    userControllerClient.updateUser(AbstractTestAction.ADMIN_USER_NAME, user, testActions.getAdminAccessToken());
    String phone = userControllerClient.getUserPhone(AbstractTestAction.ADMIN_USER_NAME,
        testActions.getAdminAccessToken());
    Assert.assertEquals("11111111111", phone);
    testActions.clearUserPhone(AbstractTestAction.ADMIN_USER_NAME);
  }
}

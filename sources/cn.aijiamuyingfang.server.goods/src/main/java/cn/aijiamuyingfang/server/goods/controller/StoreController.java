package cn.aijiamuyingfang.server.goods.controller;

import cn.aijiamuyingfang.commons.domain.exception.GoodsException;
import cn.aijiamuyingfang.commons.domain.goods.Store;
import cn.aijiamuyingfang.commons.domain.goods.response.GetDefaultStoreIdResponse;
import cn.aijiamuyingfang.commons.domain.goods.response.GetInUseStoreListResponse;
import cn.aijiamuyingfang.commons.domain.response.ResponseCode;
import cn.aijiamuyingfang.commons.utils.CollectionUtils;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.goods.service.ImageService;
import cn.aijiamuyingfang.server.goods.service.StoreClassifyService;
import cn.aijiamuyingfang.server.goods.service.StoreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * [描述]:
 * <p>
 * 门店服务-控制层
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-26 23:43:33
 */
@RestController
public class StoreController {
  @Autowired
  private StoreService storeService;

  @Autowired
  private StoreClassifyService storeclassifyService;

  @Autowired
  private ImageService imageService;

  /**
   * 分页获取在使用中的Store
   * 
   * @param currentpage
   *          当前页 默认值:1 (currentpage必须&ge;1,否则重置为1)
   * @param pagesize
   *          每页大小 默认值:10(pagesize必&gt;0,否则重置为1)
   * @return
   */
  @PreAuthorize(value = "isAuthenticated()")
  @GetMapping(value = "/store")
  public GetInUseStoreListResponse getInUseStoreList(@RequestParam(value = "currentpage") int currentpage,
      @RequestParam(value = "pagesize") int pagesize) {
    return storeService.getInUseStoreList(currentpage, pagesize);
  }

  /**
   * 创建门店
   * 
   * @param storeRequest
   * @param request
   * @return
   */
  @PreAuthorize(value = "hasAuthority('admin')")
  @PostMapping(value = "/store")
  public Store createStore(@RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
      @RequestParam(value = "detailImages", required = false) List<MultipartFile> detailImages, Store storeRequest,
      HttpServletRequest request) {
    if (null == storeRequest) {
      throw new GoodsException("400", "store request body is null");
    }
    if (StringUtils.isEmpty(storeRequest.getName())) {
      throw new GoodsException("400", "store name is empty");
    }
    storeService.createStore(storeRequest);
    String coverImgUrl = imageService.saveStoreLogo(storeRequest.getId(), coverImage);
    if (StringUtils.hasContent(coverImgUrl)) {
      coverImgUrl = String.format("http://%s:%s/%s", request.getServerName(), request.getServerPort(), coverImgUrl);
      storeRequest.setCoverImg(coverImgUrl);
    }

    imageService.clearStoreDetailImgs(storeRequest.getId());
    List<String> detailImgList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(detailImages)) {
      for (MultipartFile img : detailImages) {
        String detailImgUrl = imageService.saveStoreDetailImg(storeRequest.getId(), img);
        if (StringUtils.hasContent(detailImgUrl)) {
          detailImgUrl = String.format("http://%s:%s/%s", request.getServerName(), request.getServerPort(),
              detailImgUrl);
          detailImgList.add(detailImgUrl);
        }
      }
    }
    storeRequest.setDetailImgList(detailImgList);

    storeService.updateStore(storeRequest.getId(), storeRequest);
    return storeRequest;

  }

  /**
   * 获取门店信息
   * 
   * @param storeid
   */
  @PreAuthorize(value = "isAuthenticated()")
  @GetMapping(value = "/store/{storeid}")
  public Store getStore(@PathVariable("storeid") String storeid) {
    Store store = storeService.getStore(storeid);
    if (null == store) {
      throw new GoodsException(ResponseCode.STORE_NOT_EXIST, storeid);
    }
    return store;
  }

  /**
   * 更新门店信息
   * 
   * @param storeid
   * @param storeRequest
   * @return
   */
  @PreAuthorize(value = "hasAuthority('admin')")
  @PutMapping(value = "/store/{storeid}")
  public Store updateStore(@PathVariable("storeid") String storeid, @RequestBody Store storeRequest) {
    if (null == storeRequest) {
      throw new GoodsException("400", "update store request body is null");
    }
    return storeService.updateStore(storeid, storeRequest);
  }

  /**
   * 废弃门店
   * 
   * @param storeid
   */
  @PreAuthorize(value = "hasAuthority('admin')")
  @DeleteMapping(value = "/store/{storeid}")
  public void deprecateStore(@PathVariable("storeid") String storeid) {
    storeService.deprecateStore(storeid);
    imageService.deleteStoreImg(storeid);
  }

  /**
   * 获取默认门店Id
   * 
   * @return
   */
  @PreAuthorize(value = "isAuthenticated()")
  @GetMapping(value = "/store/defaultid")
  public GetDefaultStoreIdResponse getDefaultStoreId() {
    GetInUseStoreListResponse response = storeService.getInUseStoreList(1, 1);
    List<Store> storeList = response.getDataList();
    GetDefaultStoreIdResponse getDefaultStoreIdResponse = new GetDefaultStoreIdResponse();
    if (storeList != null && storeList.size() == 1) {
      getDefaultStoreIdResponse.setDefaultId(storeList.get(0).getId());
    }
    return getDefaultStoreIdResponse;
  }

  /**
   * 获取在哪些城市有门店分布
   * 
   * @return
   */
  @PreAuthorize(value = "isAuthenticated()")
  @GetMapping(value = "/store/city")
  public Set<String> getStoresCity() {
    return storeService.getStoresCity();
  }

  /**
   * 门店下添加条目
   * 
   * @param storeid
   * @param classifyId
   */
  @PreAuthorize(value = "hasAuthority('admin')")
  @PutMapping(value = "/store/{storeid}/classify/{classifyid}")
  public void addStoreClassify(@PathVariable("storeid") String storeid, @PathVariable("classifyid") String classifyId) {
    storeclassifyService.addClassify(storeid, classifyId);
  }
}

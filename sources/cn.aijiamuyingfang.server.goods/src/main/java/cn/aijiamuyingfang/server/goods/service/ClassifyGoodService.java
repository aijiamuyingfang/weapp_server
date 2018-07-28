package cn.aijiamuyingfang.server.goods.service;

import cn.aijiamuyingfang.commons.domain.exception.GoodsException;
import cn.aijiamuyingfang.commons.domain.goods.Classify;
import cn.aijiamuyingfang.commons.domain.goods.Good;
import cn.aijiamuyingfang.commons.domain.goods.response.GetClassifyGoodListResponse;
import cn.aijiamuyingfang.commons.domain.response.ResponseCode;
import cn.aijiamuyingfang.commons.utils.CollectionUtils;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.domain.goods.db.ClassifyRepository;
import cn.aijiamuyingfang.server.domain.goods.db.GoodRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * [描述]:
 * <p>
 * 条目服务Service
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-26 23:51:11
 */
@Service
public class ClassifyGoodService {
  @Autowired
  private ClassifyRepository classifyRepository;

  @Autowired
  private GoodRepository goodRepository;

  /**
   * 分页查询条目下的商品
   * 
   * @param classifyid
   *          条目id
   * @param packFilter
   *          商品过滤条件:包装类型
   * @param levelFilter
   *          商品过滤条件:商品等级
   * @param orderType
   *          商品排序条件:排序字段
   * @param orderValue
   *          商品排序条件:DESC:降序;ASC:升序.
   * @param currentpage
   *          请求页
   * @param pagesize
   *          每页商品数
   * @return
   */
  public GetClassifyGoodListResponse getClassifyGoodList(String classifyid, List<String> packFilter,
      List<String> levelFilter, String orderType, String orderValue, int currentpage, int pagesize) {
    Classify classify = classifyRepository.findOne(classifyid);
    if (null == classify) {
      throw new GoodsException(ResponseCode.CLASSIFY_NOT_EXIST, classifyid);
    }
    // PageRequest的Page参数是基于0的,但是currentPage是基于1的,所有将currentPage作为参数传递给PgeRequest时需要'-1'
    PageRequest pageRequest;
    if (StringUtils.hasContent(orderType)) {
      pageRequest = new PageRequest(currentpage - 1, pagesize, Sort.Direction.fromString(orderValue), orderType);
    } else {
      pageRequest = new PageRequest(currentpage - 1, pagesize);
    }
    Page<Good> goodPage;
    if (CollectionUtils.isEmpty(packFilter) && CollectionUtils.isEmpty(levelFilter)) {
      goodPage = goodRepository.findClassifyGood(classifyid, pageRequest);
    } else if (CollectionUtils.isEmpty(packFilter)) {
      goodPage = goodRepository.findClassifyGoodByLevelIn(classifyid, levelFilter, pageRequest);
    } else if (CollectionUtils.isEmpty(levelFilter)) {
      goodPage = goodRepository.findClassifyGoodByPackIn(classifyid, packFilter, pageRequest);
    } else {
      goodPage = goodRepository.findClassifyGoodByPackInAndLevelIn(classifyid, packFilter, levelFilter, pageRequest);
    }
    GetClassifyGoodListResponse response = new GetClassifyGoodListResponse();
    response.setCurrentpage(goodPage.getNumber() + 1);
    response.setDataList(goodPage.getContent());
    response.setTotalpage(goodPage.getTotalPages());
    return response;
  }

  /**
   * 移除条目下的商品
   * 
   * @param goodid
   */
  public void removeClassifyGood(String goodid) {
    classifyRepository.removeClassifyGood(goodid);
  }
}

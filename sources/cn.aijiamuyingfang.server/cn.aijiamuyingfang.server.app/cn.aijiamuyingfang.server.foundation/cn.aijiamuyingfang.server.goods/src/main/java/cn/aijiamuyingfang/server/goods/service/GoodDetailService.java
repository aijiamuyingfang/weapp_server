package cn.aijiamuyingfang.server.goods.service;

import cn.aijiamuyingfang.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.goods.db.GoodDetailRepository;
import cn.aijiamuyingfang.server.goods.dto.GoodDetailDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [描述]:
 * <p>
 * 商品详情Service
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-01 02:43:34
 */
@Service
public class GoodDetailService {
  @Autowired
  private GoodDetailRepository goodDetailRepository;

  /**
   * 创建商品详细信息
   * 
   * @param gooddetail
   */
  public GoodDetailDTO createORUpdateGoodDetail(GoodDetailDTO gooddetail) {
    if (null == gooddetail) {
      return null;
    }
    if (StringUtils.hasContent(gooddetail.getId())) {
      GoodDetailDTO oriGoodDetail = goodDetailRepository.findOne(gooddetail.getId());
      if (oriGoodDetail != null) {
        oriGoodDetail.update(gooddetail);
        return goodDetailRepository.saveAndFlush(gooddetail);
      }
    }
    return goodDetailRepository.saveAndFlush(gooddetail);
  }

  /**
   * 获得商品详细信息
   * 
   * @param gooddetailId
   * @return
   */
  public GoodDetailDTO getGoodDetail(String gooddetailId) {
    return goodDetailRepository.findOne(gooddetailId);
  }
}

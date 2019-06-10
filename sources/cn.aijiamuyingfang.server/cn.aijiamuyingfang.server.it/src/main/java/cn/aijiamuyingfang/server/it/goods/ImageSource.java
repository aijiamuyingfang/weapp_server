package cn.aijiamuyingfang.server.it.goods;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.aijiamuyingfang.commons.utils.StringUtils;
import lombok.Data;

/**
 * [描述]:
 * <p>
 * 图片资源
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2019-05-17 18:28:31
 */
@Entity
@Data
public class ImageSource {
  @Id
  private String id;

  private String url;

  public void update(ImageSource coverImg) {
    if (null == coverImg || StringUtils.isEmpty(coverImg.id) || StringUtils.isEmpty(coverImg.url)) {
      return;
    }
    this.url = coverImg.url;
    this.id = coverImg.id;
  }

  public ImageSource() {
  }

  public ImageSource(String id, String url) {
    this.id = id;
    this.url = url;
  }

}
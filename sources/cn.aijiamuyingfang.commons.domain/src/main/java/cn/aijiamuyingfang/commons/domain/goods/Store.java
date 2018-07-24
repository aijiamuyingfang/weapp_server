package cn.aijiamuyingfang.commons.domain.goods;

import cn.aijiamuyingfang.commons.domain.address.StoreAddress;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 * [描述]:
 * <p>
 * 店铺
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-27 00:12:21
 */
@Entity
public class Store {

  /**
   * 门店Id
   */
  @Id
  @GeneratedValue(generator = "strategy_uuid")
  @GenericGenerator(name = "strategy_uuid", strategy = "uuid")
  private String id;

  /**
   * 门店是否废弃(该字段用于删除门店:当需要删除门店时,设置该字段为true)
   */
  private boolean deprecated;

  /**
   * 门店名
   */
  private String name;

  /**
   * 营业时间
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "start", column = @Column(name = "start_worktime")),
      @AttributeOverride(name = "end", column = @Column(name = "end_worktime")) })
  private WorkTime workTime;

  /**
   * 封面图片地址
   */
  private String coverImg;

  /**
   * 详细图片地址
   */
  @ElementCollection
  private List<String> detailImgList = new ArrayList<>();

  /**
   * 门店地址
   */
  @OneToOne(cascade = CascadeType.ALL)
  private StoreAddress storeAddress;

  @ManyToMany
  @JsonIgnore
  private List<Classify> classifyList = new ArrayList<>();

  /**
   * 根据提供的Store更新本门店的信息
   * 
   * @param updateStore
   */
  public void update(Store updateStore) {
    if (null == updateStore) {
      return;
    }
    if (StringUtils.hasContent(updateStore.name)) {
      this.name = updateStore.name;
    }
    if (updateStore.workTime != null) {
      this.workTime.update(updateStore.workTime);
    }
    if (StringUtils.hasContent(updateStore.coverImg)) {
      this.coverImg = updateStore.coverImg;
    }
    if (updateStore.detailImgList != null && !updateStore.detailImgList.isEmpty()) {
      this.detailImgList = updateStore.detailImgList;
    }
    if (updateStore.storeAddress != null) {
      this.storeAddress = updateStore.storeAddress;
    }
    if (updateStore.classifyList != null && !updateStore.classifyList.isEmpty()) {
      this.classifyList = updateStore.classifyList;
    }
  }

  /**
   * 为门店添加顶层条目
   * 
   * @param classify
   */
  public void addClassify(Classify classify) {
    if (null == classify) {
      return;
    }
    synchronized (this) {
      if (null == this.classifyList) {
        this.classifyList = new ArrayList<>();
      }
    }
    this.classifyList.add(classify);
  }

  public boolean isDeprecated() {
    return deprecated;
  }

  public void setDeprecated(boolean deprecated) {
    this.deprecated = deprecated;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WorkTime getWorkTime() {
    return workTime;
  }

  public void setWorkTime(WorkTime workTime) {
    this.workTime = workTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCoverImg() {
    return coverImg;
  }

  public void setCoverImg(String coverImg) {
    this.coverImg = coverImg;
  }

  public List<String> getDetailImgList() {
    return detailImgList;
  }

  public void setDetailImgList(List<String> detailImgList) {
    this.detailImgList = detailImgList;
  }

  public StoreAddress getStoreAddress() {
    return storeAddress;
  }

  public void setStoreAddress(StoreAddress storeAddress) {
    this.storeAddress = storeAddress;
  }

  public List<Classify> getClassifyList() {
    return classifyList;
  }

  public void setClassifyList(List<Classify> classifyList) {
    this.classifyList = classifyList;
  }

}
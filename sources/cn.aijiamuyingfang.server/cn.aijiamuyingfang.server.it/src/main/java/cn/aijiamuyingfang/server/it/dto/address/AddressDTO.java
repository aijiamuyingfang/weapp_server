package cn.aijiamuyingfang.server.it.dto.address;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * [描述]:
 * <p>
 * 地址相关请求的请求体
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-04 23:00:45
 */
@MappedSuperclass
@Data
public abstract class AddressDTO {
  /**
   * 地址-Id
   */
  @Id
  @GeneratedValue(generator = "strategy_uuid")
  @GenericGenerator(name = "strategy_uuid", strategy = "uuid")
  protected String id;

  /**
   * 地址是否废弃
   */
  protected boolean deprecated;

  /**
   * 地址-省
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "province_name")),
      @AttributeOverride(name = "code", column = @Column(name = "province_code")) })
  protected ProvinceDTO province;

  /**
   * 地址-市
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "city_name")),
      @AttributeOverride(name = "code", column = @Column(name = "city_code")) })
  protected CityDTO city;

  /**
   * 地址-县/区
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "county_name")),
      @AttributeOverride(name = "code", column = @Column(name = "county_code")) })
  protected CountyDTO county;

  /**
   * 地址-镇/社区
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "town_name")),
      @AttributeOverride(name = "code", column = @Column(name = "town_code")) })
  protected TownDTO town;

  /**
   * 地址-详细地址
   */
  protected String detail;

  /**
   * 地址-坐标
   */
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "latitude", column = @Column(name = "coordinate_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "coordinate_longitude")) })
  protected CoordinateDTO coordinate;
}

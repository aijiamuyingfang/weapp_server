package cn.aijiamuyingfang.server.it.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import cn.aijiamuyingfang.commons.utils.CollectionUtils;
import cn.aijiamuyingfang.commons.utils.NumberUtils;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import cn.aijiamuyingfang.server.it.domain.Gender;
import cn.aijiamuyingfang.server.it.domain.UserAuthority;
import lombok.Data;

/**
 * [描述]:
 * <p>
 * 用户
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-25 16:38:56
 */
@Entity
@Data
public class User {
  /**
   * 用户的Id
   */
  @Id
  @GeneratedValue(generator = "strategy_uuid")
  @GenericGenerator(name = "strategy_uuid", strategy = "uuid")
  private String id;

  /**
   * 该小程序中用户的唯一Id
   */
  private String openid;

  /**
   * 密码
   */
  private String password;

  /**
   * 昵称
   */
  private String nickname;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 联系电话
   */
  private String phone;

  /**
   * 小程序调用wx.login() 获取的临时登录凭证code
   */
  private String jscode;

  /**
   * 用户角色
   */
  @JsonDeserialize(contentUsing = AuthorityListDeserializer.class)
  @ElementCollection(fetch = FetchType.EAGER, targetClass = UserAuthority.class)
  @Enumerated(EnumType.ORDINAL)
  private List<UserAuthority> authorityList = new ArrayList<>();

  private static class AuthorityListDeserializer extends JsonDeserializer<GrantedAuthority> {

    @Override
    public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      JsonToken currentToken = p.currentToken();
      if (currentToken == JsonToken.VALUE_NUMBER_INT) {
        return UserAuthority.fromValue(p.getIntValue());
      } else if (currentToken == JsonToken.VALUE_STRING) {
        return UserAuthority.fromValue(NumberUtils.toInt(p.getValueAsString(), 0));
      }
      return UserAuthority.UNKNOW;
    }

  }

  /**
   * 用户所在的APP ID
   */
  @JsonIgnore
  private String appid;

  /**
   * 性别
   */
  @JsonDeserialize(using = GenderDeserializer.class)
  private Gender gender;

  private static class GenderDeserializer extends JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      JsonToken currentToken = p.currentToken();
      if (currentToken == JsonToken.VALUE_NUMBER_INT) {
        return Gender.fromValue(p.getIntValue());
      } else if (currentToken == JsonToken.VALUE_STRING) {
        return Gender.fromValue(NumberUtils.toInt(p.getValueAsString(), 0));
      }
      return Gender.UNKNOW;
    }

  }

  /**
   * 最后一次消息读取时间
   */
  @JsonIgnore
  private Date lastReadMsgTime = new Date(0);

  /**
   * 用户通用积分
   */
  private int genericScore = 0;

  /**
   * 使用提供的updateUser更新用户信息
   * 
   * @param updateUser
   *          要更新的用户信息
   */
  public void update(User updateUser) {
    if (null == updateUser) {
      return;
    }
    if (StringUtils.hasContent(updateUser.nickname)) {
      this.nickname = updateUser.nickname;
    }
    if (updateUser.gender != null) {
      this.gender = updateUser.gender;
    }
    if (StringUtils.hasContent(updateUser.avatar)) {
      this.avatar = updateUser.avatar;
    }
    if (StringUtils.hasContent(updateUser.phone)) {
      this.phone = updateUser.phone;
    }
    if (updateUser.lastReadMsgTime != null) {
      this.lastReadMsgTime = updateUser.lastReadMsgTime;
    }
    if (updateUser.genericScore != 0) {
      this.genericScore = updateUser.genericScore;
    }
    if (!CollectionUtils.isEmpty(updateUser.authorityList)) {
      this.authorityList = updateUser.authorityList;
    }
  }

  /**
   * 增加用户通用积分
   * 
   * @param score
   *          要添加的用户通用积分
   */
  public void increaseGenericScore(int score) {
    this.genericScore += score;
  }

  /**
   * 减少用户通用积分
   * 
   * @param score
   *          要减少的用户通用积分
   */
  public void decreaseGenericScore(int score) {
    this.genericScore -= score;
  }

  public void addAuthority(UserAuthority authority) {
    synchronized (this) {
      if (null == this.authorityList) {
        this.authorityList = new ArrayList<>();
      }
      if (authority != null && !this.authorityList.contains(authority)) {
        this.authorityList.add(authority);
      }
    }
  }

  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityList;
  }

  @JsonIgnore
  public String getUsername() {
    return this.id;
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }

}

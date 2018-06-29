package cn.aijiamuyingfang.server.domain.user;

import cn.aijiamuyingfang.server.domain.BaseEnum;

/**
 * [描述]:
 * <p>
 * 用户消息类型
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-27 17:10:30
 */
public enum UserMessageType implements BaseEnum {

	/**
	 * 通知
	 */
	NOTICE(0),

	/**
	 * 链接
	 */
	LINK(1),
	/**
	 * 位置类型
	 */
	UNKNOW(-1);
	private int value;

	private UserMessageType(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
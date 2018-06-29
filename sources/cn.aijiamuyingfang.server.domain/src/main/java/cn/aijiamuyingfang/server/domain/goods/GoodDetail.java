package cn.aijiamuyingfang.server.domain.goods;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * [描述]:
 * <p>
 * 商品详情
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-27 00:13:06
 */
@Entity
public class GoodDetail {
	/**
	 * 商品详情Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * 保质期
	 */
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "start", column = @Column(name = "lifetime_start")),
			@AttributeOverride(name = "end", column = @Column(name = "lifetime_end")) })
	private ShelfLife lifetime;

	/**
	 * 商品详细图片
	 */
	@ElementCollection
	private List<String> detailImgList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ShelfLife getLifetime() {
		return lifetime;
	}

	public void setLifetime(ShelfLife lifetime) {
		this.lifetime = lifetime;
	}

	public List<String> getDetailImgList() {
		return detailImgList;
	}

	public void setDetailImgList(List<String> detailImgList) {
		this.detailImgList = detailImgList;
	}

	/**
	 * 保质期
	 */
	public static class ShelfLife {
		/**
		 * 保质期-开始时间
		 */
		@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
		private Date start;

		/**
		 * 保质期-结束时间
		 */
		@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
		private Date end;

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}

	}
}

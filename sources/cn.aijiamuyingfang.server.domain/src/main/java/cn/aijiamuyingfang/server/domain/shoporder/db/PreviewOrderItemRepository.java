package cn.aijiamuyingfang.server.domain.shoporder.db;

import cn.aijiamuyingfang.server.domain.shoporder.PreviewOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * [描述]:
 * <p>
 * 预览项的数据仓库
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-25 21:15:03
 */
@Repository
public interface PreviewOrderItemRepository extends JpaRepository<PreviewOrderItem, String> {

}

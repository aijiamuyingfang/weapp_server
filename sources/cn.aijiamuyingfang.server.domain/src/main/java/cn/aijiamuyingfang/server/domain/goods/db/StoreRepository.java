package cn.aijiamuyingfang.server.domain.goods.db;

import cn.aijiamuyingfang.server.domain.goods.Store;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * [描述]:
 * <p>
 * 门店的数据仓库
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-25 21:15:03
 */

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
  // TODO:要设置条件:deprecated=false

  @Override
  @Query(value = "select s from Store s where s.id=:id and s.deprecated=false")
  Store findOne(@Param("id") String id);

  /**
   * 分页查找所有在使用中的门店
   * 
   * @param pageable
   * @return
   */
  @Query(value = "select  * from store where deprecated=false  order by ?#{#pageable}",
      countQuery = "select count(*) from store where deprecated=false  order by ?#{#pageable}", nativeQuery = true)
  Page<Store> findInUseStores(Pageable pageable);

  /**
   * 查找所有在使用中的门店(非分页)
   * 
   * @return
   */
  @Query(value = "select  * from store where deprecated=false", nativeQuery = true)
  List<Store> findInUseStores();

  /**
   * 移除门店下的条目
   * 
   * @param classifyid
   */
  @Modifying
  @Transactional
  @Query(value = "delete from store_classify_list where classify_list_id=:classifyid", nativeQuery = true)
  public void removeStoreClassify(@Param("classifyid") String classifyid);

}

package cn.aijiamuyingfang.server.goods.db;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.aijiamuyingfang.server.goods.dto.GoodDTO;

/**
 * [描述]:
 * <p>
 * 商品的数据仓库
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-25 21:15:03
 */
@Repository
public interface GoodRepository extends JpaRepository<GoodDTO, String> {

  @Override
  @Query(value = "select * from good where id=:good_id and deprecated=false", nativeQuery = true)
  GoodDTO findOne(@Param("good_id") String goodId);

  /**
   * 按照打包方式和阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param levelList
   * @param pageable
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and g.pack in :pack and g.level in :level order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and g.pack in :pack and g.level in :level order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackInAndLevelIn(@Param("classify_id") String classifyId,
      @Param("pack") List<String> packList, @Param("level") List<String> levelList, Pageable pageable);
  
  /**
   * 按照打包方式和阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param levelList
   * @param pageable
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) and g.level in :level order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) and g.level in :level order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackNULLORInAndLevelIn(@Param("classify_id") String classifyId,
      @Param("pack") List<String> packList, @Param("level") List<String> levelList, Pageable pageable);
  
  
  /**
   * 按照打包方式和阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param levelList
   * @param pageable
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and g.pack in :pack and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and g.pack in :pack and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackInAndLevelNULLORIn(@Param("classify_id") String classifyId,
      @Param("pack") List<String> packList, @Param("level") List<String> levelList, Pageable pageable);
  
  
  /**
   * 按照打包方式和阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param levelList
   * @param pageable
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackNULLORInAndLevelNULLORIn(@Param("classify_id") String classifyId,
      @Param("pack") List<String> packList, @Param("level") List<String> levelList, Pageable pageable);

  /**
   * 按照打包方式分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param pageable
   *          分页信息
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and g.pack in :pack order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and g.pack in :pack order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackIn(@Param("classify_id") String classifyId, @Param("pack") List<String> packList,
      Pageable pageable);
  
  /**
   * 按照打包方式分页获取条目下的商品
   * 
   * @param classifyId
   * @param packList
   * @param pageable
   *          分页信息
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id where "
          + "g.deprecated=false and c.classify_id=:classify_id and (g.pack IS NULL or g.pack in :pack) order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByPackNULLORIn(@Param("classify_id") String classifyId, @Param("pack") List<String> packList,
      Pageable pageable);

  /**
   * 按照阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param levelList
   * @param pageable
   *          分页信息
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and g.level in :level order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id "
          + "where g.deprecated=false and c.classify_id=:classify_id and g.level in :level order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByLevelIn(@Param("classify_id") String classifyId,
      @Param("level") List<String> levelList, Pageable pageable);

  /**
   * 按照阶段分页获取条目下的商品
   * 
   * @param classifyId
   * @param levelList
   * @param pageable
   *          分页信息
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated=false "
          + "and c.classify_id=:classify_id and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id "
          + "where g.deprecated=false and c.classify_id=:classify_id and (g.level IS NULL or g.level in :level) order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGoodByLevelNULLORIn(@Param("classify_id") String classifyId,
      @Param("level") List<String> levelList, Pageable pageable);
  
  /**
   * 分页获取条目下的商品
   * 
   * @param classifyId
   * @param pageable
   *          分页信息
   * @return
   */
  @Query(
      value = "select * from good g inner join classify_good_list c on g.id=c.good_list_id where g.deprecated="
          + "false and c.classify_id=:classify_id order by ?#{#pageable}",
      countQuery = "select count(*) from good g inner join classify_good_list c on g.id=c.good_list_id "
          + "where g.deprecated=false and c.classify_id=:classify_id order by ?#{#pageable}",
      nativeQuery = true)
  Page<GoodDTO> findClassifyGood(@Param("classify_id") String classifyId, Pageable pageable);

  /**
   * 将Good中对应goodvoucherId的GoodVoucher废弃
   * 
   * @param goodvoucherId
   */
  @Modifying
  @Transactional
  @Query(value = "update good set voucher_id=null where voucher_id=:good_voucher_id", nativeQuery = true)
  void deprecateGoodVoucher(@Param("good_voucher_id") String goodvoucherId);
}

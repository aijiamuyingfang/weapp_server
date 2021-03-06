package cn.aijiamuyingfang.server.it.db.logcenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.aijiamuyingfang.server.it.dto.logcenter.LogDTO;

/**
 * [描述]:
 * <p>
 * 日志的数据仓库
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2019-04-10 18:06:35
 */
@Repository
public interface LogRepository extends JpaRepository<LogDTO, String> {
  /**
   * 分页查询日志
   * 
   * @param where
   * @param pageable
   * @return
   */
  @Query(value = "select * from log :where order by ?#{#pageable}",
      countQuery = "select count(*) from log where :where order by ?#{#pageable}", nativeQuery = true)
  Page<LogDTO> findLog(@Param("where") String where, Pageable pageable);
}

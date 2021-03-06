package cn.aijiamuyingfang.server.logcenter.service;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.aijiamuyingfang.server.logcenter.db.LogRepository;
import cn.aijiamuyingfang.server.logcenter.dto.LogDTO;
import cn.aijiamuyingfang.server.logcenter.utils.ConvertUtils;
import cn.aijiamuyingfang.vo.logcenter.Log;
import cn.aijiamuyingfang.vo.logcenter.PagableLogList;

/**
 * [描述]:
 * <p>
 * 日志Service层
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2019-04-10 16:04:03
 */
@Service
public class LogService {
  @Autowired
  private LogRepository logRepository;

  /**
   * 保存日志记录到数据库
   * 
   * @param log
   */
  @Async
  public void save(Log log) {
    if (null == log) {
      return;
    }
    if (null == log.getCreateTime()) {
      log.setCreateTime(new Date());
    }
    logRepository.saveAndFlush(ConvertUtils.convertLog(log));
  }

  /**
   * 分页查询日志信息
   * 
   * @param params
   * @return
   */
  public PagableLogList getLogList(Map<String, String> params) {
    int currentPage = NumberUtils.toInt(params.remove("current_page"), 1);
    int pageSize = NumberUtils.toInt(params.remove("page_size"), 10);
    PageRequest pagable = new PageRequest(currentPage - 1, pageSize);
    StringBuilder whereSqlBuilder = new StringBuilder();
    whereSqlBuilder.append(params.size() > 0 ? "where " : "");
    for (Entry<String, String> entry : params.entrySet()) {
      whereSqlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(" and ");
    }
    String whereSql = whereSqlBuilder.toString();
    whereSql = whereSql.substring(0, whereSql.length() - 5);
    Page<LogDTO> logDTOPage = logRepository.findLog(whereSql, pagable);
    PagableLogList response = new PagableLogList();
    response.setCurrentPage(logDTOPage.getNumber() + 1);
    response.setDataList(ConvertUtils.convertLogDTOList(logDTOPage.getContent()));
    response.setTotalpage(logDTOPage.getTotalPages());
    return response;
  }

}

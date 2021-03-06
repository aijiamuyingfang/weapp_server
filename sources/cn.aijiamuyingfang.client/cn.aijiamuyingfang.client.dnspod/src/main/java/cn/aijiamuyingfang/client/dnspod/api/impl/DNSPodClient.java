package cn.aijiamuyingfang.client.dnspod.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.aijiamuyingfang.client.dnspod.api.DNSPodApi;
import cn.aijiamuyingfang.client.dnspod.domain.CreateRecordRequest;
import cn.aijiamuyingfang.client.dnspod.domain.DomainRequest;
import cn.aijiamuyingfang.client.dnspod.domain.SubDomainRecordListResponse;
import cn.aijiamuyingfang.client.dnspod.domain.UpdateRecordRequest;
import cn.aijiamuyingfang.client.rest.annotation.HttpService;
import cn.aijiamuyingfang.vo.utils.StringUtils;

/**
 * [描述]:
 * <p>
 * dns pod操作客戶端
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2019-06-09 06:44:50
 */
@Service
public class DNSPodClient {

  /**
   * 环境变量中,用来存放“请求公网IP的URL”的Key
   */
  private static final String NETIP_URL_KEY = "WEAPP_DNSPOD_NETIP";

  /**
   * 默认请求公网IP的URL
   */
  private static final String DEFAULT_NET_IP_URL = "http://ipinfo.io/ip";

  private static final String GET_NET_IP_URL;

  static {
    String url = System.getenv(NETIP_URL_KEY);
    if (StringUtils.hasContent(url)) {
      GET_NET_IP_URL = url;
    } else {
      GET_NET_IP_URL = DEFAULT_NET_IP_URL;
    }
  }

  @HttpService
  private DNSPodApi dnspodApi;

  public void updateDNSPod(String domain, String tokenId, String tokenValue) throws IOException {
    String[] domainArrary = domain.split("\\.");
    String rootDomain = domainArrary[domainArrary.length - 2] + '.' + domainArrary[domainArrary.length - 1];
    String domainPrefix = domain.replace(rootDomain, "");
    if (domainPrefix.endsWith(".")) {
      domainPrefix = domainPrefix.substring(0, domainPrefix.length() - 1);
    }

    DomainRequest request = new DomainRequest(tokenId, tokenValue, rootDomain);
    SubDomainRecordListResponse subDomainListResponse = dnspodApi.getSubDomainRecod(request.toPartMap()).execute()
        .body();
    SubDomainRecordListResponse.Record equalRecord = null;
    SubDomainRecordListResponse.Record wildRecord = null;
    if (subDomainListResponse != null && CollectionUtils.isNotEmpty(subDomainListResponse.getRecords())) {
      for (SubDomainRecordListResponse.Record record : subDomainListResponse.getRecords()) {
        if (domainPrefix.equals(record.getSubDomain())) {
          equalRecord = record;
        }
        if ("*".equals(record.getSubDomain())) {
          wildRecord = record;
        }
      }
    }
    
    syncRealIP(tokenId, tokenValue, rootDomain, domainPrefix, equalRecord, wildRecord);
  }


  /**
   * 域名同步真實IP
   * @param tokenId
   * @param tokenValue
   * @param rootDomain
   * @param domainPrefix
   * @param equalRecord
   * @param wildRecord
   * @throws IOException
   */
  private void syncRealIP(String tokenId, String tokenValue, String rootDomain, String domainPrefix,
      SubDomainRecordListResponse.Record equalRecord, SubDomainRecordListResponse.Record wildRecord)
      throws IOException {
    String actualIp = getPublicIP();
    String recordIp = null;
    String recordId = null;
    String recordLine = null;
    if (equalRecord != null) {
      recordIp = equalRecord.getValue();
      recordId = equalRecord.getId();
      recordLine = equalRecord.getArea();
    } else if (wildRecord != null) {
      recordIp = wildRecord.getValue();
      recordId = wildRecord.getId();
      recordLine = wildRecord.getArea();
    } else {
      CreateRecordRequest createRecordRequest = new CreateRecordRequest(tokenId, tokenValue, rootDomain, actualIp);
      createRecordRequest.setSubDomain(StringUtils.hasContent(domainPrefix) ? domainPrefix : "@");
      dnspodApi.createRecord(createRecordRequest.toPartMap()).execute();
      return;
    }
    if (!actualIp.equals(recordIp)) {
      UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest(tokenId, tokenValue, rootDomain, domainPrefix,
          recordId, recordLine);
      dnspodApi.updateDynamicDNS(updateRecordRequest.toPartMap()).execute();
    }
  }
  

  public String getPublicIP() throws IOException {
    StringBuilder content = new StringBuilder();
    URL url = new URL(GET_NET_IP_URL);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    String read = "";
    while ((read = in.readLine()) != null) {
      content.append(read);
    }
    return content.toString();
  }
}

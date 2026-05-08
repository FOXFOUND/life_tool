import org.xbill.DNS.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;


/**
 *         <dependency>
 *             <groupId>dnsjava</groupId>
 *             <artifactId>dnsjava</artifactId>
 *             <version>3.5.2</version>
 *         </dependency>
 * */
public class DnsTester {

    // 整合了 IPv4 与 IPv6 的公共 DNS 服务器列表（完整版）
    private static final List<InetAddress> DNS_SERVERS = buildDnsServerList();

    // 线程池大小
    private static final int THREAD_POOL_SIZE = 100;

    private static List<InetAddress> buildDnsServerList() {
        String[] ips = {
                // 中国电信 IPv4 (65个)
                "218.2.2.2", "218.4.4.4", "219.141.136.10",
                "219.141.140.10", "219.150.32.132", "222.222.222.222",
                "222.222.202.202", "219.149.135.188", "59.49.49.49",
                "222.74.1.200", "219.148.204.66", "219.149.194.55",
                "219.149.194.56", "112.100.100.100", "202.96.209.133",
                "116.228.111.118", "202.96.209.5", "180.168.255.118",
                "61.147.37.1", "218.2.135.1", "61.153.177.196",
                "61.153.177.200", "61.132.163.68", "202.102.213.68",
                "202.102.192.68", "218.85.152.99", "218.85.157.99",
                "202.101.224.69", "202.101.226.68", "219.146.1.66",
                "219.147.1.66", "222.88.88.88", "222.85.85.85",
                "202.103.24.68", "202.103.44.150", "222.246.129.80",
                "59.51.78.211", "59.51.78.210", "222.246.129.81",
                "202.96.128.86", "202.96.128.166", "202.96.134.133",
                "202.103.225.68", "202.103.224.68", "202.100.192.68",
                "202.100.199.8", "61.128.128.68", "61.128.192.68",
                "61.139.2.69", "218.6.200.139", "202.98.192.67",
                "202.98.198.167", "222.172.200.68", "61.166.150.123",
                "202.98.224.70", "218.30.19.40", "61.134.1.4",
                "202.100.64.68", "61.178.0.93", "61.178.0.94",
                "202.100.192.68", "202.100.199.8",

                // 中国联通 IPv4 (28个)
                "123.123.123.123", "123.123.123.124", "202.106.196.115",
                "202.102.128.68", "202.102.152.3", "202.102.154.3",
                "221.7.1.21", "202.96.69.38", "202.96.86.18",
                "202.96.134.133", "202.98.0.68", "202.98.5.68",
                "221.12.1.227", "221.12.33.227", "221.12.65.227",
                "221.12.97.227", "221.12.129.227", "221.12.161.227",
                "221.12.193.227", "221.12.225.227", "221.12.1.228",
                "221.12.33.228", "221.12.65.228", "221.12.97.228",
                "221.12.129.228", "221.12.161.228", "221.12.193.228",
                "221.12.225.228",

                // 中国移动 IPv4 (34个)
                "211.138.180.2", "211.138.180.3", "211.136.17.107",
                "221.179.38.7", "218.200.1.3", "218.200.1.9",
                "211.137.130.3", "211.137.130.164", "211.137.191.26",
                "211.137.191.27", "112.4.0.55", "112.4.0.66",
                "211.138.151.161", "211.138.151.162", "211.138.244.2",
                "211.138.244.3", "211.139.163.114", "211.139.163.130",
                "218.201.4.3", "218.201.4.195", "211.139.248.2",
                "211.139.248.3", "211.139.7.2", "211.139.7.226",
                "218.202.4.195", "218.202.4.3", "211.139.124.2",
                "211.139.124.3", "218.204.4.8", "218.204.4.9",
                "211.136.18.211", "211.136.18.203", "211.139.29.98",
                "211.139.29.99",

                // 阿里云、腾讯云和其他 IPv4 (22个)
                "223.5.5.5", "223.6.6.6", "183.60.83.19",
                "183.60.82.98", "61.235.70.252", "211.98.4.1",
                "103.27.24.1", "8.8.8.8", "8.8.4.4",
                "1.1.1.1", "1.0.0.1", "9.9.9.9",
                "149.112.112.112", "208.67.222.222", "208.67.220.220",
                "84.200.69.80", "84.200.70.40", "94.140.14.14",
                "94.140.15.15", "76.76.19.19", "198.153.192.1",
                "198.153.194.1",

                // 中国电信 IPv6 (约50个)
                "2400:da00:6666::6666", "240e:40:8000::10",
                "240e:40:8000::11", "240e:45::6666",
                "240e:45:5000::8888", "240e:d:0:100::6",
                "240e:d:1000:100::6", "240e:3:800:ff00::8",
                "240e:3:3800:ff00::8", "240e:41:c000:ffff::",
                "240e:41:c900:ffff::", "240e:211::6666",
                "240e:212::6666", "240e:41:4100::1",
                "240e:41:4200::1", "240e:0058:c000:1000:116:228:111:118",
                "240e:0058:c000:1600:180:168:255:118", "240e:5b::6666",
                "240e:5a::6666", "240e:1c:200::1",
                "240e:1c:200::2", "240e:46:4088::4088",
                "240e:46:4888::4888", "240e:14:6000::1",
                "240e:14:e000::1", "240e:13:0000:0100::1",
                "240e:13:1800:0100::1", "240e:4e::66",
                "240e:4e:800::66", "240e:4b::88",
                "240e:4b:1000::88", "240e:50:5000::80",
                "240e:50:c800::210", "240e:1f:1::1",
                "240e:1f:1::33", "240e:9:0:100:202:103:224:68",
                "240e:9:2000:100:202:103:225:68", "240e:42:c000::1",
                "240e:42:c000::2", "240e:47:0:702::1",
                "240e:47:0:701::1", "240e:56:4000:8000::69",
                "240e:56:4000::218", "240e:4a:4300:3::67",
                "240e:4a:4400:3::167", "240e:52:4800::8888",
                "240e:52:4000::8888", "240e:45:d000:8:202:98:224:200",
                "240e:45:d000:8:202:98:224:216", "240e:f:a::6",
                "240e:f:a00b::6", "240e:44:4001:1::1",
                "240e:44:4001:8001::1", "240e:43:c000::1",

                // 中国联通 IPv6 (约23个)
                "2408:8899::8", "2408:8000:1010:1::8",
                "2408:8000:1010:2::8", "2408:8888::8",
                "2408:8899::8", "2408:8000:6001:7000::8888",
                "2408:8000:6001:7000::9999", "2408:8000:c000::8888",
                "2408:8000:c004::8888", "2408:8000:eeee::e",
                "2408:8000:eeee::f", "2408:8001:2000:3::ffff",
                "2408:8001:2000:3::eeee", "2408:8001:4000:9000:221:7:128:68",
                "2408:8001:4010:9000:221:7:136:68", "2408:8662::1",
                "2408:8662::2", "2408:8001:7000::",
                "2408:876c::8888", "2408:8001:a001::252",
                "2408:8001:a001::254", "2408:8001:d000:101d::58",
                "2408:8001:d000:101d::68",

                // 中国移动 IPv6 (约27个)
                "2409:8088::a", "2409:8002:2000:0::1",
                "2409:8010:2000::1", "2409:8010:2000::2",
                "2409:8018:2001:1::8888", "2409:8018:2001:1::9999",
                "2409:801a:2000::7", "2409:801a:2000::6",
                "2409:801e:2000::1", "2409:801e:2000::2",
                "2409:8028:2000::3333", "2409:8028:2000::4444",
                "2409:8034:2000::1", "2409:8034:2000::2",
                "2409:803c:2000:0001::26", "2409:803c:2000:0003::130",
                "2409:804c:2000:0001::1", "2409:804c:2000:0002::1",
                "2409:8057:2000:4::8", "2409:8060:20ea:200::1",
                "2409:8060:20ea:201::1", "2409:8062:2000:2::1",
                "2409:8062:2000:2::2", "2409:8070:2000:f110::1",
                "2409:8070:2000:f100::1", "2409:807c:2000:1001::0",
                "2409:807c:2000:1000::0",

                // 服务商 IPv6 (约14个)
                "2400:3200::1", "2400:3200:baba::1",
                "2402:4e00::", "2400:da00::6666",
                "2620:1ec:4::1", "2402:4e90:1::1",
                "2402:4e80:1::1", "240c::6666",
                "2001:4860:4860::8888", "2001:4860:4860::8844",
                "2606:4700:4700::1111", "2606:4700:4700::1001",
                "2620:fe::fe", "2620:0:ccc::2"
        };

        List<InetAddress> list = new ArrayList<>();
        for (String ip : ips) {
            try {
                list.add(InetAddress.getByName(ip));
            } catch (UnknownHostException e) {
                System.err.println("无效 IP 地址，已跳过: " + ip);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        final int totalCount = DNS_SERVERS.size();
        System.out.println("开始测试 " + totalCount + " 个 DNS 服务器（IPv4 + IPv6）...");
        System.out.println("测试域名：www.baidu.com (AAAA 记录)");
        System.out.println("目标：筛选同时支持 UDP 和 TCP 查询的服务器\n");

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<DnsResult>> futures = new ArrayList<>(totalCount);

        // 提交任务
        for (InetAddress dns : DNS_SERVERS) {
            Future<DnsResult> future = executor.submit(() -> {
                boolean udpOk = testDns(dns, false);
                boolean tcpOk = testDns(dns, true);
                return new DnsResult(dns.getHostAddress(), udpOk, tcpOk);
            });
            futures.add(future);
        }

        // 关闭线程池并等待任务完成
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
                System.err.println("部分测试任务超时未完成！");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("主线程被中断");
        }

        // 收集结果并筛选出 UDP 和 TCP 都可用的服务器
        List<DnsResult> availableServers = new ArrayList<>();
        int udpSuccessCount = 0;
        int tcpSuccessCount = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                DnsResult result = futures.get(i).get();
                if (result.udpOk) udpSuccessCount++;
                if (result.tcpOk) tcpSuccessCount++;
                if (result.udpOk && result.tcpOk) {
                    availableServers.add(result);
                }
            } catch (InterruptedException | ExecutionException e) {
                // 某个任务异常时，视为不可用，不统计
            }
        }

        // 输出统计信息
        System.out.println("==================== 测试完成 ====================");
        System.out.println("总测试服务器数: " + totalCount);
        System.out.println("UDP 成功: " + udpSuccessCount);
        System.out.println("TCP 成功: " + tcpSuccessCount);
        System.out.println("同时支持 UDP 和 TCP: " + availableServers.size());

        // 输出可用 DNS 列表
        if (availableServers.isEmpty()) {
            System.out.println("\n没有找到同时支持 UDP 和 TCP 的 DNS 服务器。");
        } else {
            System.out.println("\n以下 DNS 服务器同时支持 UDP 和 TCP 查询：");
            System.out.println("------------------------------------------------");
            for (DnsResult r : availableServers) {
                System.out.printf("%-48s%n", r.ip);
            }
            System.out.println("------------------------------------------------");
        }

        System.out.println("\n按 Enter 键退出...");
        new Scanner(System.in).nextLine();
    }

    /**
     * 测试指定 DNS 服务器的连通性
     *
     * @param dnsIp  DNS 服务器地址
     * @param useTcp true 使用 TCP，false 使用 UDP
     * @return 查询成功且获得至少一条应答记录则返回 true
     */
    private static boolean testDns(InetAddress dnsIp, boolean useTcp) {
        try {
            SimpleResolver resolver = new SimpleResolver(dnsIp.getHostAddress());
            resolver.setPort(53);
            resolver.setTimeout(3); // 3 秒超时
            resolver.setTCP(useTcp);

            Lookup lookup = new Lookup("www.baidu.com.", Type.AAAA);
            lookup.setResolver(resolver);
            lookup.setCache(null); // 禁用缓存

            Record[] records = lookup.run();
            return records != null && records.length > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 内部类：存储单个 DNS 测试结果
    private static class DnsResult {
        final String ip;
        final boolean udpOk;
        final boolean tcpOk;

        DnsResult(String ip, boolean udpOk, boolean tcpOk) {
            this.ip = ip;
            this.udpOk = udpOk;
            this.tcpOk = tcpOk;
        }
    }
}
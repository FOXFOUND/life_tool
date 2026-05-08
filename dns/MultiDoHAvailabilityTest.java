import org.xbill.DNS.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 国内外 DoH 服务器批量可用性测试
 * 使用 dnsjava 库，支持标准 RFC 8484 DoH
 */
public class MultiDoHAvailabilityTest {

    // ---------- 预定义 DoH 服务器列表（国内 + 国外）----------
    private static final Map<String, String> DOH_SERVERS = new LinkedHashMap<>();

    static {
        // 国内主流 DoH
        DOH_SERVERS.put("阿里云 DoH", "https://dns.alidns.com/dns-query");
        DOH_SERVERS.put("DNSPod DoH", "https://doh.pub/dns-query");
        DOH_SERVERS.put("114DNS DoH", "https://doh.114dns.com/dns-query");
        DOH_SERVERS.put("360 DoH", "https://doh.360.cn/dns-query");

        // 国外主流 DoH
        DOH_SERVERS.put("Cloudflare DoH", "https://cloudflare-dns.com/dns-query");
        DOH_SERVERS.put("Google DoH", "https://dns.google/dns-query");
        DOH_SERVERS.put("Quad9 DoH", "https://dns.quad9.net/dns-query");
        DOH_SERVERS.put("OpenDNS DoH", "https://doh.opendns.com/dns-query");
    }

    // 默认测试参数
    private static final String DEFAULT_DOMAIN = "www.baidu.com";
    private static final int DEFAULT_TYPE = Type.AAAA;
    private static final int TIMEOUT_SECONDS = 10;
    private static final int CONCURRENCY = 4;   // 并行测试数量

    public static void main(String[] args) throws InterruptedException {
        // 可选的命令行参数 [domain] [record_type]
        String domain = args.length > 0 ? args[0] : DEFAULT_DOMAIN;
        int recordType = DEFAULT_TYPE;
        if (args.length > 1) {
            recordType = Type.value(args[1].toUpperCase());
        }

        System.out.printf("========== DoH 批量可用性测试 ==========%n");
        System.out.printf("查询域名: %s , 记录类型: %s%n", domain, Type.string(recordType));
        System.out.printf("超时设置: %d 秒, 并发数: %d%n%n", TIMEOUT_SECONDS, CONCURRENCY);

        // 执行批量测试
        List<DoHTestResult> results = runTests(domain, recordType);

        // 输出汇总报告
        printSummary(results);
    }

    /**
     * 并发测试所有 DoH 服务器
     */
    private static List<DoHTestResult> runTests(String domain, int recordType) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY);
        List<Future<DoHTestResult>> futures = new ArrayList<>();

        for (Map.Entry<String, String> entry : DOH_SERVERS.entrySet()) {
            String name = entry.getKey();
            String url = entry.getValue();
            futures.add(executor.submit(() -> testSingleDoH(name, url, domain, recordType)));
        }

        // 收集结果
        List<DoHTestResult> results = new ArrayList<>();
        for (Future<DoHTestResult> future : futures) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                System.err.println("内部错误: " + e.getCause().getMessage());
            }
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        return results;
    }

    /**
     * 测试单个 DoH 服务器
     */
    private static DoHTestResult testSingleDoH(String name, String url, String domain, int recordType) {
        long start = System.nanoTime();
        boolean available = false;
        List<String> answers = new ArrayList<>();
        String errorMsg = null;

        try {
            Resolver resolver = new DohResolver (url);
            resolver.setTimeout(Duration.ofSeconds(TIMEOUT_SECONDS));

            Lookup lookup = new Lookup(domain, recordType);
            lookup.setResolver(resolver);
            lookup.setCache(null);   // 避免缓存干扰
            Record[] records = lookup.run();

            if (lookup.getResult() == Lookup.SUCCESSFUL && records != null && records.length > 0) {
                available = true;
                for (Record rec : records) {
                    answers.add(rec.rdataToString());
                }
            } else {
                errorMsg = lookup.getErrorString();
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }

        long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        return new DoHTestResult(name, url, available, elapsedMs, answers, errorMsg);
    }

    /**
     * 打印汇总报告
     */
    private static void printSummary(List<DoHTestResult> results) {
        System.out.println("========== 测试结果汇总 ==========");
        List<DoHTestResult> succeeded = results.stream().filter(r -> r.available).collect(Collectors.toList());
        List<DoHTestResult> failed = results.stream().filter(r -> !r.available).collect(Collectors.toList());

        System.out.printf("成功率: %d / %d (%.1f%%)%n%n",
                succeeded.size(), results.size(), succeeded.size() * 100.0 / results.size());

        // 成功列表（按耗时排序）
        if (!succeeded.isEmpty()) {
            System.out.println("✅ 成功的服务器:");
            succeeded.stream()
                    .sorted(Comparator.comparingLong(DoHTestResult::elapsedMs))
                    .forEach(r -> System.out.printf("   %-20s | %6d ms | %s%n",
                            r.name, r.elapsedMs, r.url));
            System.out.println();
        }

        // 失败列表
        if (!failed.isEmpty()) {
            System.out.println("❌ 失败的服务器:");
            failed.forEach(r -> System.out.printf("   %-20s | %s%n   -> 错误: %s%n",
                    r.name, r.url, r.errorMsg));
            System.out.println();
        }

        // 输出一个示例响应（第一个成功服务器的答案）
        if (!succeeded.isEmpty()) {
            DoHTestResult first = succeeded.get(0);
            System.out.printf("📋 示例响应 (来自 %s):%n", first.name);
            if (first.answers.isEmpty()) {
                System.out.println("   无记录数据");
            } else {
                first.answers.forEach(ans -> System.out.println("   " + ans));
            }
        }
    }

    /**
     * 测试结果封装类
     */
    private static class DoHTestResult {
        final String name;
        final String url;
        final boolean available;
        final long elapsedMs;
        final List<String> answers;
        final String errorMsg;

        DoHTestResult(String name, String url, boolean available, long elapsedMs, List<String> answers, String errorMsg) {
            this.name = name;
            this.url = url;
            this.available = available;
            this.elapsedMs = elapsedMs;
            this.answers = answers;
            this.errorMsg = errorMsg;
        }

        long elapsedMs() { return elapsedMs; }
    }
}
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * DNS延迟测试工具（UDP + TCP）
 * 向指定的DNS服务器发送A记录查询，测量响应时间
 */
public class DnsLatencyTest {

    private static final int TYPE_A = 1;
    private static final int CLASS_IN = 1;

    /**
     * 通过UDP测量DNS延迟
     * @param dnsServer DNS服务器IP地址
     * @param domain 查询域名
     * @param timeoutMs 超时毫秒数
     * @return 延迟毫秒数，失败或超时返回-1
     */
    public static long measureUdpLatency(String dnsServer, String domain, int timeoutMs) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(timeoutMs);
            InetAddress serverAddr = InetAddress.getByName(dnsServer);
            int transactionId = new Random().nextInt(0xFFFF);

            byte[] query = buildDnsQuery(transactionId, domain);
            DatagramPacket packet = new DatagramPacket(query, query.length, serverAddr, 53);

            long start = System.nanoTime();
            socket.send(packet);

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            long end = System.nanoTime();

            if (isValidDnsResponse(buffer, transactionId)) {
                return TimeUnit.NANOSECONDS.toMillis(end - start);
            } else {
                return -1;
            }
        } catch (SocketTimeoutException e) {
            return -1; // 超时
        } catch (IOException e) {
            System.err.println("UDP通信异常 [" + dnsServer + "]: " + e.getMessage());
            return -1;
        }
    }

    /**
     * 通过TCP测量DNS延迟
     * @param dnsServer DNS服务器IP地址
     * @param domain 查询域名
     * @param timeoutMs 超时毫秒数（连接+读取）
     * @return 延迟毫秒数，失败或超时返回-1
     */
    public static long measureTcpLatency(String dnsServer, String domain, int timeoutMs) {
        int transactionId = new Random().nextInt(0xFFFF);
        byte[] query = buildDnsQuery(transactionId, domain);
        // TCP DNS 需要先发送两字节的长度（网络字节序）
        byte[] tcpQuery = new byte[2 + query.length];
        tcpQuery[0] = (byte) ((query.length >> 8) & 0xFF);
        tcpQuery[1] = (byte) (query.length & 0xFF);
        System.arraycopy(query, 0, tcpQuery, 2, query.length);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(dnsServer, 53), timeoutMs);
            socket.setSoTimeout(timeoutMs);

            long start = System.nanoTime();
            OutputStream out = socket.getOutputStream();
            out.write(tcpQuery);
            out.flush();

            InputStream in = socket.getInputStream();
            // 读取响应长度（2字节）
            byte[] lenBuf = new byte[2];
            readFully(in, lenBuf);
            int respLen = ((lenBuf[0] & 0xFF) << 8) | (lenBuf[1] & 0xFF);
            if (respLen > 4096) { // 防止恶意报文
                return -1;
            }
            byte[] respData = new byte[respLen];
            readFully(in, respData);
            long end = System.nanoTime();

            if (isValidDnsResponse(respData, transactionId)) {
                return TimeUnit.NANOSECONDS.toMillis(end - start);
            } else {
                return -1;
            }
        } catch (SocketTimeoutException | SocketException e) {
            return -1;
        } catch (IOException e) {
            System.err.println("TCP通信异常 [" + dnsServer + "]: " + e.getMessage());
            return -1;
        }
    }

    /**
     * 从输入流中读取指定长度的数据，保证完全读取
     */
    private static void readFully(InputStream in, byte[] buf) throws IOException {
        int offset = 0;
        int len = buf.length;
        while (offset < len) {
            int read = in.read(buf, offset, len - offset);
            if (read < 0) {
                throw new EOFException("流提前结束");
            }
            offset += read;
        }
    }

    /**
     * 构造DNS查询报文（与UDP/TCP共用）
     */
    private static byte[] buildDnsQuery(int id, String domain) {
        ByteBufferWriter writer = new ByteBufferWriter();
        // Header
        writer.writeShort(id);
        writer.writeShort(0x0100); // 标准查询，递归期望
        writer.writeShort(1);      // QDCOUNT
        writer.writeShort(0);      // ANCOUNT
        writer.writeShort(0);      // NSCOUNT
        writer.writeShort(0);      // ARCOUNT

        // QNAME
        String[] labels = domain.split("\\.");
        for (String label : labels) {
            writer.writeByte(label.length());
            for (char ch : label.toCharArray()) {
                writer.writeByte((byte) ch);
            }
        }
        writer.writeByte(0); // 域名结束

        // QTYPE & QCLASS
        writer.writeShort(TYPE_A);
        writer.writeShort(CLASS_IN);

        return writer.toBytes();
    }

    /**
     * 验证DNS响应的基本有效性（ID匹配、QR=1、RCODE=0）
     */
    private static boolean isValidDnsResponse(byte[] response, int expectedId) {
        if (response == null || response.length < 12) return false;
        int id = ((response[0] & 0xFF) << 8) | (response[1] & 0xFF);
        if (id != expectedId) return false;
        int flags = ((response[2] & 0xFF) << 8) | (response[3] & 0xFF);
        boolean isResponse = (flags & 0x8000) != 0;
        int rcode = flags & 0x000F;
        return isResponse && rcode == 0;
    }

    /**
     * 辅助字节缓冲区写入器
     */
    static class ByteBufferWriter {
        private byte[] buf = new byte[512];
        private int pos = 0;

        void writeByte(int v) {
            buf[pos++] = (byte) (v & 0xFF);
        }

        void writeShort(int v) {
            writeByte((v >> 8) & 0xFF);
            writeByte(v & 0xFF);
        }

        byte[] toBytes() {
            byte[] result = new byte[pos];
            System.arraycopy(buf, 0, result, 0, pos);
            return result;
        }
    }

    /**
     * 执行测试并输出结果
     */
    public static void main(String[] args) {
        String[] dnsServers = {
                "114.114.114.114",
                "223.5.5.5",
                "202.98.0.68",
                "123.123.123.123"
        };
        String testDomain = "www.baidu.com";
        int testTimes = 30;
        int timeoutMs = 3000;

        System.out.println("DNS 延迟测试（UDP / TCP）");
        System.out.println("测试域名: " + testDomain);
        System.out.println("每个服务器测试次数: " + testTimes);
        System.out.println("超时: " + timeoutMs + "ms\n");
        System.out.printf("%-18s %-15s %-15s%n", "DNS服务器", "UDP平均延迟(ms)", "TCP平均延迟(ms)");
        System.out.println("------------------------------------------------");

        for (String dns : dnsServers) {
            // UDP 测试
            long udpTotal = 0;
            int udpSuccess = 0;
            for (int i = 0; i < testTimes; i++) {
                long latency = measureUdpLatency(dns, testDomain, timeoutMs);
                if (latency >= 0) {
                    udpTotal += latency;
                    udpSuccess++;
                }
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
            double udpAvg = (udpSuccess == 0) ? -1.0 : (double) udpTotal / udpSuccess;

            // TCP 测试
            long tcpTotal = 0;
            int tcpSuccess = 0;
            for (int i = 0; i < testTimes; i++) {
                long latency = measureTcpLatency(dns, testDomain, timeoutMs);
                if (latency >= 0) {
                    tcpTotal += latency;
                    tcpSuccess++;
                }
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
            double tcpAvg = (tcpSuccess == 0) ? -1.0 : (double) tcpTotal / tcpSuccess;

            System.out.printf("%-18s ", dns);
            System.out.printf("%-15s ", (udpAvg < 0 ? "超时/失败" : String.format("%.2f (%d次)", udpAvg, udpSuccess)));
            System.out.printf("%-15s%n", (tcpAvg < 0 ? "超时/失败" : String.format("%.2f (%d次)", tcpAvg, tcpSuccess)));
        }
    }
}
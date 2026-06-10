
# 算法实验与工具集

本仓库包含多个独立的 Java 项目和工具模块，主要用于算法练习、数据结构实现、PPT 处理、加密通信、DNS 查询等场景。

## 目录结构

```
.
├── algorithm_test_project/   # 主要算法测试项目
├── bc_large_dc/              # 大数据/分布式计算相关
├── dns/                      # DNS 查询工具
├── pptx_generate_remarks/    # PPT 备注生成器
├── pptx_to_video_voice/      # PPT 转视频/语音
├── secret_chat/              # 加密聊天演示
├── struct_md2pptx/           # Markdown 转 PPTX
└── windows/                  # Windows 测试脚本
```

---

## 1. algorithm_test_project

**综合性 Java 算法项目**，包含数百道 LeetCode 风格题解、常见数据结构实现及单元测试。  
项目结构遵循 Maven/Gradle 约定（`src/main/java`），使用 IntelliJ IDEA 开发。

### 主要子模块（包）

#### 数组与字符串
- `arr` – 数组基本操作  
- `arraytomap` – 数组转 Map  
- `commonChars` – 查找多个字符串的公共字符  
- `lengthOfLongestSubstring` – 最长无重复子串  
- `longestPalindrome` – 最长回文子串  
- `reverseWords` – 反转字符串中的单词  
- `validPalindrome` – 验证回文串  
- `mergeAlternately` – 交替合并字符串  
- `gcdOfStrings` – 字符串的最大公因子  
- `removeOuterParentheses` – 删除最外层括号  

#### 链表
- `addTwoNumbers` – 两数相加（链表）  
- `mergeTwoLists` – 合并两个有序链表  
- `reverseList` – 反转链表  
- `reverseBetween` – 反转链表 II  
- `reverseKGroup` – K 个一组反转链表  
- `swapPairs` – 两两交换链表节点  
- `deleteDuplicates` – 删除排序链表重复元素  
- `deleteMiddle` – 删除链表的中间节点  
- `detectCycle` – 检测环形链表  
- `getIntersectionNode` – 两个链表的交点  
- `copyRandomList` – 复制带随机指针的链表  
- `LRUCache` / `lru` – LRU 缓存实现  
- `oddEvenList` – 奇偶链表  
- `reorderList` – 重排链表  

#### 树与二叉树
- `buildTree` – 根据遍历结果构建二叉树  
- `isSymmetric` – 对称二叉树  
- `isValidBST` – 验证二叉搜索树  
- `maxDepth` – 二叉树的最大深度  
- `levelOrder` – 二叉树的层序遍历  
- `lowestCommonAncestor` – 最近公共祖先  
- `mirrorTree` – 二叉树的镜像  
- `pruneTree` – 修剪二叉树  
- `rightSideView` – 二叉树的右视图  
- `treeToDoublyList` – 二叉树转双向链表  
- `widthOfBinaryTree` – 二叉树最大宽度  
- `goodNodes` – 好节点计数  

#### 动态规划
- `climbStairs`（未显式列出，但有 `minCostClimbingStairs`）– 最小代价爬楼梯  
- `maxProfit` – 买卖股票的最佳时机  
- `rob` – 打家劫舍  
- `longestCommonSubsequence` – 最长公共子序列  
- `longestConsecutive` – 最长连续序列  
- `lenLongestFibSubseq` – 最长斐波那契子序列  
- `numTilings` – 多米诺骨牌铺砖  
- `tribonacci` – 泰波那契数  
- `translateNum` – 数字翻译成字符串  
- `dicesProbability` – 掷骰子的概率  

#### 回溯与组合
- `combinations` / `combine` – 组合生成  
- `permute` – 全排列  
- `subsets` – 子集  
- `combinationSum2` – 组合总和 II  
- `n` – N 皇后问题  

#### 贪心与区间
- `eraseOverlapIntervals` – 移除重叠区间  
- `findMinArrowShots` – 引爆气球的最少箭数  
- `partitionLabels` – 划分字母区间  
- `leastInterval` – 任务调度器  
- `maxJump` – 最大跳跃  

#### 数学与位运算
- `countBits` – 二进制中 1 的个数  
- `singleNumber` / `singleNumbers` – 只出现一次的数字  
- `myPow` – 快速幂  
- `reversePairs` – 翻转对  
- `maximumSwap` – 最大交换  
- `nthUglyNumber` – 第 N 个丑数  

#### 栈与队列
- `dailyTemperatures` – 每日温度  
- `validateStackSequences` – 验证栈序列  
- `removeStars` – 移除星号  

#### 图与搜索
- `canVisitAllRooms` – 能否访问所有房间  
- `findSmallestSetOfVertices` – 找到最小顶点集合（有向图）  
- `orangesRotting` – 腐烂的橘子（BFS）  
- `nearestExit` – 迷宫最近出口  
- `updateMatrix` – 01 矩阵中的最近距离  

#### 其他实用模块
- `fastjson` / `json` – JSON 处理示例  
- `guaua` – Google Guava 测试  
- `thread` / `threadLocalDemo` – 多线程与 ThreadLocal  
- `VolatileTest` – volatile 关键字测试  
- `regex` – 正则表达式测试  
- `readfile` / `read` – 文件读取与配置读取  
- `uuid` / `url` – UUID 生成与 URL 测试  
- `puml` – PlantUML 图表生成  
- `memory` – 内存转储分析  

> 注：由于原始目录树编码问题，部分包名以英文单词为准，上述列表已覆盖主要功能。

---

## 2. bc_large_dc

**大数据/分布式计算相关模块**（Big Data / Large Scale Distributed Computing）。  
可能包含数据一致性验证、大文件处理或分布式任务调度示例。  
目录结构：
```
bc_large_dc/
├── .idea/          # IDEA 配置
└── src/            # 源代码（未详细展开）
```

---

## 3. dns

**DNS 查询工具**。  
实现域名解析、DNS 记录查询（A/AAAA/MX/TXT 等）或简单的 DNS 代理功能。  
（目录下未提供具体文件，需根据源码确认）

---

## 4. pptx_generate_remarks

**PPT 备注生成器**。  
用于自动向 PowerPoint 文件添加演讲者备注，可能基于模板或文本文件批量处理。

---

## 5. pptx_to_video_voice

**PPT 转视频/语音工具**。  
将演示文稿转换为视频文件，或结合 TTS 生成配音讲解。  
典型用途：自动化制作教学视频。

---

## 6. secret_chat

**加密聊天演示**。  
实现端到端加密的即时通讯示例，可能包含对称/非对称加密、密钥交换等功能。

---

## 7. struct_md2pptx

**Markdown 转 PPTX 转换器**。  
将 Markdown 格式的文档（支持标题、列表、代码块）生成为 PowerPoint 文件。  
适用于快速生成技术分享或报告幻灯片。

---

## 8. windows

**Windows 测试脚本**。  
包含批处理或 PowerShell 脚本，例如 `Test-RemoteMaxSessions` 用于测试远程桌面最大会话数。

---

## 环境要求

- **JDK**：1.8 或更高版本  
- **构建工具**：Maven / Gradle（部分模块可能需要）  
- **IDE**：推荐 IntelliJ IDEA  
- **其他依赖**：  
  - `pptx_to_video_voice` 可能依赖 FFmpeg  
  - `secret_chat` 可能依赖 Bouncy Castle 等加密库  

## 使用指南

1. 克隆仓库  
   ```bash
   git clone https://github.com/your-username/your-repo.git
   ```
2. 根据需要进入具体模块目录  
3. 使用 IDE 打开或直接运行 `main` 方法  
4. 各模块独立，可单独编译运行

## 注意事项

- 部分模块为实验性或演示代码，生产环境使用请谨慎评估。  
- 原始目录树中存在编码乱码，实际文件名以英文为准。  
- 算法模块大多包含单元测试，可参考 `src/test/java`（若有）验证正确性。

---

## 贡献

欢迎提交 Issue 或 Pull Request 来完善算法题解或工具功能。

## 许可证

[MIT](LICENSE)
```

此 README 已涵盖您提出的三点要求。如需进一步调整或补充具体文件列表，请提供更详细的目录内容。

# algorithm_test_project

## 项目概述

这是一个综合性的 Java 算法测试项目，包含了大量 LeetCode 风格算法题解、数据结构实现以及多个独立的功能模块（如 DNS 查询、PPT 生成、加密聊天等）。项目使用 IntelliJ IDEA 作为开发环境，遵循 Maven/Gradle 项目结构（`src/main/java`）。

## 目录结构

```text
algorithm_test_project/
├── .idea/                     # IntelliJ IDEA 配置目录
├── src/
│   └── main/
│       └── java/              # 算法和测试代码主目录
│           ├── add/           # 加法相关算法
│           ├── addTwoNumbers/ # 两数相加（链表）
│           ├── apaas/         # 应用平台即服务相关
│           ├── apaast/        # 未明确分类
│           ├── areSentencesSimilar/ # 句子相似性判断
│           ├── arr/           # 数组操作
│           ├── arraytomap/    # 数组转 Map
│           ├── arth/          # 算术运算
│           ├── bignumber/     # 大数处理
│           ├── bit/           # 位运算
│           ├── buildMatrix/   # 矩阵构建
│           ├── buildTree/     # 树结构构建
│           ├── calendar/      # 日历相关
│           ├── canCross/      # 青蛙过河问题
│           ├── canTransform/  # 字符串变换
│           ├── canVisitAllRooms/ # 房间遍历
│           ├── captureForts/  # 堡垒抓捕
│           ├── champagneTower/ # 香槟塔
│           ├── checkStraightLine/ # 共线检查
│           ├── circle/        # 圆相关字符串处理
│           ├── circular/      # 环形数组/循环
│           ├── closeStrings/  # 接近字符串
│           ├── colorTheGrid/  # 网格涂色
│           ├── combinations/  # 组合生成
│           ├── combinationSum2/ # 组合总和 II
│           ├── combine/       # 合并操作
│           ├── commonChars/   # 公共字符
│           ├── constructArr/  # 数组构建
│           ├── copyRandomList/ # 复制带随机指针的链表
│           ├── costMax/       # 最大代价
│           ├── countBits/     # 二进制中1的个数
│           ├── countDigitOne/ # 数字1的个数
│           ├── countSpecialNumbers/ # 特殊数字计数
│           ├── countSubstrings/ # 回文子串计数
│           ├── dailyTemperatures/ # 每日温度
│           ├── deleteDuplicates/ # 删除重复节点
│           ├── deleteMiddle/  # 删除中间节点
│           ├── deleteNode/    # 删除节点
│           ├── delNodes/      # 删除多个节点
│           ├── detectCycle/   # 检测环
│           ├── dicesProbability/ # 骰子概率
│           ├── digArtifacts/  # 挖掘工件
│           ├── direct/        # 直接映射
│           ├── divide/        # 除法（分玩家）
│           ├── eraseOverlapIntervals/ # 移除重叠区间
│           ├── exception/     # 异常测试
│           ├── exchange/      # 交换
│           ├── fastjson/      # FastJson 示例
│           ├── findClosestElements/ # 查找最接近的元素
│           ├── findDifference/ # 找不同
│           ├── findKthLargest/ # 第K大元素
│           ├── findMaxAverage/ # 最大平均值
│           ├── findMaxFish/   # 最大鱼群
│           ├── findMinArrowShots/ # 最少箭数引爆气球
│           ├── findNumberIn2DArray/ # 二维数组查找
│           ├── findPeakElement/ # 峰值元素
│           ├── findRotateSteps/ # 旋转转盘步数
│           ├── findSmallestSetOfVertices/ # 最小顶点集合
│           ├── furthestBuilding/ # 最远建筑
│           ├── garden/        # 花园问题
│           ├── gcdOfStrings/  # 字符串最大公因子
│           ├── generate/      # 生成 SQL
│           ├── getDirections/ # 获取方向
│           ├── getIntersectionNode/ # 链表交点
│           ├── getKthFromEnd/ # 倒数第K个节点
│           ├── getLeastNumbers/ # 最小的K个数
│           ├── getProbability/ # 概率计算
│           ├── goodNodes/     # 好节点
│           ├── guaua/         # Guava 测试
│           ├── guessNumber/   # 猜数字
│           ├── heap/          # 堆排序
│           ├── hextest/       # 十六进制测试
│           ├── hIndex/        # H指数
│           ├── house/         # 打家劫舍
│           ├── idea/          # IDEA 功能
│           ├── insert/        # 插入操作
│           ├── insertionSortList/ # 插入排序链表
│           ├── interleaving/  # 交错字符串
│           ├── isBalanced/    # 平衡树判断
│           ├── isItPossible/  # 可能性判断
│           ├── isPalindrome/  # 回文判断
│           ├── isStraight/    # 顺子判断
│           ├── isSubsequence/ # 子序列判断
│           ├── isSubStructure/ # 子树结构
│           ├── isSymmetric/   # 对称树
│           ├── isValidBST/    # 验证二叉搜索树
│           ├── jmdi/          # 未知模块
│           ├── json/          # JSON 转 Map
│           ├── jump/          # 跳跃游戏
│           ├── k/             # K 相关
│           ├── kidsWithCandies/ # 拥有最多糖果的孩子
│           ├── kSmallestPairs/ # 最小K对和
│           ├── kthSmallest/   # 第K小元素
│           ├── largestAltitude/ # 最高海拔
│           ├── largestValues/ # 每行最大值
│           ├── leastInterval/ # 任务调度
│           ├── lengthOfLongestSubstring/ # 最长无重复子串
│           ├── lenLongestFibSubseq/ # 最长斐波那契子序列
│           ├── levelOrder/    # 层序遍历
│           ├── list/          # 列表操作
│           ├── longestCommonSubsequence/ # 最长公共子序列
│           ├── longestConsecutive/ # 最长连续序列
│           ├── longestOnes/   # 最大连续1的个数
│           ├── longestPalindrome/ # 最长回文子串
│           ├── longestSubarray/ # 最长子数组
│           ├── longestZigZag/ # 最长锯齿路径
│           ├── lowestCommonAncestor/ # 最近公共祖先
│           ├── lru/           # LRU 缓存
│           ├── LRUCache/      # LRU 缓存实现
│           ├── makesquare/    # 火柴拼正方形
│           ├── map/           # Map 测试
│           ├── match/         # 匹配测试
│           ├── maxArea/       # 最大面积
│           ├── maxDepth/      # 最大深度
│           ├── maxDistance/   # 最大距离
│           ├── maximum/       # 最大乘积
│           ├── maximumSwap/   # 最大交换
│           ├── maxJump/       # 最大跳跃
│           ├── maxLevelSum/   # 最大层和
│           ├── maxProfit/     # 最大利润
│           ├── maxScore/      # 最大分数
│           ├── maxScoreIndices/ # 最大分数下标
│           ├── maxSumDivThree/ # 被3整除的最大和
│           ├── maxSumMinProduct/ # 最大子数组最小乘积
│           ├── maxValue/      # 最大值
│           ├── maxVowels/     # 最大元音字母数
│           ├── memory/        # 内存转储
│           ├── mergeAlternately/ # 交替合并字符串
│           ├── mergeTwoLists/ # 合并两个有序链表
│           ├── minCapability/ # 最小能力
│           ├── minCostClimbingStairs/ # 最小爬楼梯代价
│           ├── minEatingSpeed/ # 最小吃香蕉速度
│           ├── minFlips/      # 最小翻转
│           ├── minimumTime/   # 最少时间
│           ├── minSubarray/   # 最短子数组
│           ├── minSubArrayLen/ # 长度最小子数组
│           ├── mirrorReflection/ # 镜面反射
│           ├── mirrorTree/    # 镜像树
│           ├── moveZeroes/    # 移动零
│           ├── movingCount/   # 机器人运动范围
│           ├── myPow/         # 幂函数
│           ├── n/             # N皇后问题
│           ├── nearestExit/   # 最近出口
│           ├── nextLargerNodes/ # 下一个更大节点
│           ├── nthUglyNumber/ # 第N个丑数
│           ├── numSubarrayProductLessThanK/ # 乘积小于K的子数组
│           ├── numTilings/    # 多米诺骨牌铺砖
│           ├── oddEvenList/   # 奇偶链表
│           ├── operation/     # 操作测试
│           ├── optimal/       # 最优划分字符串
│           ├── orangesRotting/ # 腐烂的橘子
│           ├── pairSum/       # 配对和
│           ├── partitionLabels/ # 划分字母区间
│           ├── permutation/   # 排列
│           ├── permute/       # 全排列
│           ├── pivotIndex/    # 中心下标
│           ├── possibleToStamp/ # 邮票贴图
│           ├── printNumbers/  # 打印数字
│           ├── priorityQueue/ # 优先队列测试
│           ├── productExceptSelf/ # 除自身以外数组乘积
│           ├── pruneTree/     # 修剪二叉树
│           ├── puml/          # PlantUML 相关
│           ├── quick/         # 快速排序
│           ├── reachNumber/   # 到达终点数字
│           ├── read/          # 读配置
│           ├── readfile/      # 读文件
│           ├── rearrangeArray/ # 重排数组
│           ├── ref/           # 引用测试
│           ├── referenece/    # 引用测试
│           ├── regex/         # 正则测试
│           ├── removeOuterParentheses/ # 删除最外层括号
│           ├── removeStars/   # 移除星号
│           ├── removeZeroSumSublists/ # 删除和为0的子链表
│           ├── reorderList/   # 重排链表
│           ├── repeatedStringMatch/ # 重复字符串匹配
│           ├── reverseBetween/ # 反转链表 II
│           ├── reverseKGroup/ # K个一组反转链表
│           ├── reverseLeftWords/ # 左旋转字符串
│           ├── reverseList/   # 反转链表
│           ├── reverseOddLevels/ # 反转奇数层
│           ├── reversePairs/  # 翻转对
│           ├── reverseWords/  # 反转单词
│           ├── rightSideView/ # 二叉树的右视图
│           ├── rob/           # 打家劫舍
│           ├── rotateRight/   # 旋转链表
│           ├── search/        # 搜索
│           ├── searchBST/     # 二叉搜索树查找
│           ├── shortest/      # 最短路径（交替颜色）
│           ├── singleNonDuplicate/ # 只出现一次的数字
│           ├── singleNumber/  # 只出现一次的数字
│           ├── singleNumbers/ # 只出现一次的数字（进阶）
│           ├── smallestChair/ # 最小座位号
│           ├── smallestNumber/ # 最小数
│           ├── spiralOrder/   # 螺旋矩阵
│           ├── splitListToParts/ # 分隔链表
│           ├── string/        # 字符串数组测试
│           ├── subarrayLCM/   # 子数组 LCM
│           ├── subarraySum/   # 子数组和
│           ├── subsets/       # 子集
│           ├── successfulPairs/ # 成功配对
│           ├── suggestedProducts/ # 推荐产品
│           ├── sumSubarrayMins/ # 子数组最小值的和
│           ├── swapLinkedPair/ # 交换链表对
│           ├── swapPairs/     # 两两交换链表节点
│           ├── systemtest/    # 系统测试
│           ├── test/          # 通用测试
│           ├── thread/        # 线程测试
│           ├── threadLocalDemo/ # ThreadLocal 示例
│           ├── threeSum/      # 三数之和
│           ├── timetest/      # 时间测试
│           ├── totalCost/     # 总成本
│           ├── totalFruit/    # 最多水果
│           ├── translateNum/  # 数字翻译
│           ├── transportationHub/ # 交通枢纽
│           ├── treeToDoublyList/ # 树转双向链表
│           ├── tribonacci/    # 泰波那契数
│           ├── trie/          # 字典树
│           ├── twoEditWords/  # 两次编辑单词
│           ├── twoSum/        # 两数之和
│           ├── type/          # 类型测试
│           ├── unique/        # 唯一二叉搜索树/路径
│           ├── updateMatrix/  # 更新矩阵（01矩阵）
│           ├── url/           # URL 测试
│           ├── uuid/          # UUID 生成
│           ├── validateStackSequences/ # 验证栈序列
│           ├── validPalindrome/ # 验证回文串
│           ├── verifyPostorder/ # 验证二叉搜索树后序遍历
│           ├── VolatileTest/  # volatile 测试
│           ├── ways/          # 解码方法等
│           ├── widthOfBinaryTree/ # 二叉树最大宽度
│           ├── word/          # 单词矩形（程序员面试金典）
│           └── resources/     # 资源文件（如 readhttp）
├── bc_large_dc/               # 大数据量 DC 相关模块
├── dns/                       # DNS 查询或解析工具
├── pptx_generate_remarks/     # PPT 生成备注
├── pptx_to_video_voice/       # PPT 转视频/语音
├── secret_chat/               # 加密聊天功能
├── struct_md2pptx/            # Markdown 转 PPTX
└── windows/                   # Windows 测试脚本（如 Test-RemoteMaxSessions）
```

## 主要内容

- **算法练习**：`src/main/java` 下包含上百个经典算法题解，覆盖数组、链表、树、动态规划、贪心、回溯、位运算、字符串等常见类型。
- **独立工具**：
  - `pptx_generate_remarks`、`pptx_to_video_voice`、`struct_md2pptx`：Office 文档处理与转换。
  - `secret_chat`：安全通信示例。
  - `dns`：DNS 相关功能。
  - `bc_large_dc`：大数据场景下的分布式计算或数据一致性验证。
- **测试与资源**：包含多线程、JSON 处理、Guava、FastJson、PriorityQueue、内存转储等单元测试或示例代码。

## 开发环境

- **IDE**：IntelliJ IDEA（项目根目录包含 `.idea` 配置）
- **语言**：Java（源码位于 `src/main/java`）
- **构建工具**：未明确提供 `pom.xml` 或 `build.gradle`，但目录结构兼容 Maven/Gradle。

## 使用说明

1. 使用 IntelliJ IDEA 打开项目根目录。
2. 将 `src/main/java` 标记为 **Sources Root**。
3. 运行任意包含 `main` 方法的类进行算法测试或功能验证。
4. 各独立模块（如 `pptx_to_video_voice`）可能需要额外依赖，请根据具体代码补充。

## 注意事项

- 部分目录名称源自 Windows `tree` 命令输出，原始编码可能存在乱码，但不影响实际代码文件。
- 某些包为临时测试或未完善，使用时请注意检查代码完整性。

---

如需进一步了解具体算法实现或模块功能，请查阅相应包内的 Java 源码及注释。
```
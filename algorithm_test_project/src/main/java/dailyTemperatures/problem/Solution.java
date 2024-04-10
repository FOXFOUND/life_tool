package dailyTemperatures.problem;

import java.util.Stack;

public class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            //栈为空,则入栈
            if (stack.empty()) {
                stack.push(i);
                continue;
            }
            //如果栈顶的温度大于当前的温度,入栈
            if (temperatures[stack.peek().intValue()] >= temperatures[i]) {
                stack.push(i);
                continue;
            }
            //到这里说明,栈顶的温度小于当前的温度
            do {
                //栈顶出栈
                int popValue = stack.pop();
                //计算时间差
                res[popValue] = i - popValue;

            } while (
                    //栈不为空
                    !stack.empty()
                    &&
                    //判断栈顶是否还符合栈顶的温度小于当前的温度的场景
                    temperatures[stack.peek().intValue()] < temperatures[i]);
            //当前天入栈
            stack.push(i);
        }
        return res;
    }
}

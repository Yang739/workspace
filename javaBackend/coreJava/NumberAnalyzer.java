/**
 * 数字特征计算器
 * <p>
 *     从控制台循环接收整数，输入0结束,输出:
 *     <ul>
 *         <li>个数</li>
 *         <li>总和</li>
 *         <li>最大值</li>
 *         <li>最小值</li>
 *         <li>平均值（保留两位小数）</li>
 *     </ul>
 *     注意：0不计入个数
 * </p>
 *
 * @since 2026-07-14
 */
package coreJava;

import java.util.Scanner;

public class NumberAnalyzer {
    public static void main(String[] args){
        analyzeNumbers();
    }

    /**
     * 读取整数并输出统计结果
     * <p>
     *     实现策略：
     *      <ul>
     *          <li>使用 {@link Scanner} 从 {@link System#in} 逐 token 读取。</li>
     *          <li>以 0 作为哨兵结束输入，哨兵本身不参与统计。</li>
     *          <li>若在输入任何有效数字前直接输入 0，则输出提示并安全退出。</li>
     *      </ul>
     * </p>
     */
    public static void analyzeNumbers(){
        Scanner scanner= new Scanner(System.in);
        int cnt=0;
        int sum=0;
        int max=Integer.MIN_VALUE;
        int min=Integer.MAX_VALUE;
        while(true){
            int n=scanner.nextInt();
            if(n==0){
                break;
            }
            cnt++;
            sum+=n;
            if(n>max) max=n;
            if(n<min) min=n;
        }
        double avg=(double) sum/cnt;
        System.out.println("个数："+cnt);
        System.out.println("总和："+sum);
        System.out.println("最大值："+max);
        System.out.println("最小值："+min);
        System.out.printf("平均值：%.2f%n",avg);
        scanner.close();
    }
}
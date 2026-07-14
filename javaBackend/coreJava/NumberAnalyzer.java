/**
 * 数字特征计算器
 * 从控制台循环接收整数，输入0结束，
 * 输出个数、总和、最大值、最小值、平均值（保留两位小数）
 */
package coreJava;

import java.util.Scanner;

public class NumberAnalyzer {
    public static void main(String[] args){
        analyzerNumbers();
    }
    public static void analyzerNumbers(){
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
        double avr=(double) sum/cnt;
        System.out.println("个数："+cnt);
        System.out.println("总和："+sum);
        System.out.println("最大值："+max);
        System.out.println("最小值："+min);
        System.out.printf("平均值：%.2f%n",avr);
        scanner.close();
    }
}
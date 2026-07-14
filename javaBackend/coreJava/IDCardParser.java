/**
 * 身份证信息解析器
 * 从控制台接收一个18位身份证号码，
 * 验证格式，提取并输出出生日期和性别，
 * 日期格式化为“xxxx年xx月xx日”，性别判断规则：第17位奇数为男，偶数为女。
 * 随机生成一个合法的18位身份证号（校验码非强制精确计算）
 */
package coreJava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class IDCardParser {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入18位身份证号码：");
        String id=scanner.nextLine().trim();
        String res=paresIDCard(id);
        System.out.println(res);
        System.out.println("随机生成一个模拟身份证号码：");
        String fakeID=generateRandomID();
        System.out.println("模拟身份证号码："+fakeID);
        System.out.println("解析结果："+paresIDCard(fakeID));
        scanner.close();
    }
    public static String paresIDCard(String id){
        if(id==null||id.length()!=18){
            return "错误，请输入18位身份证号码";
        }
        for (int i = 0; i < 17; i++) {
            if(!Character.isDigit(id.charAt(i))){
                return "错误，请输入有效身份证号码";
            }
        }
        char lastCharacter=id.charAt(17);
        if(lastCharacter!='x'&&lastCharacter!='X'&&!Character.isDigit(lastCharacter)){
            return "错误，请输入有效身份证号码";
        }

        String birthStr=id.substring(6,14);
        LocalDate birthDate;
        try{
            birthDate=LocalDate.parse(birthStr,DateTimeFormatter.ofPattern("yyyyMMdd"));
        }catch (DateTimeParseException e){
            return "错误，包含无效出生日期信息";
        }
        DateTimeFormatter outputFormatter=DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String formattedBirth=birthDate.format(outputFormatter);

        char genderChar=id.charAt(16);
        int genderNum=genderChar-'0';
        String gender=(genderNum%2==1)? "男":"女";

        StringBuilder sb=new StringBuilder();
        sb.append("出生日期：").append(formattedBirth).append("性别：").append(gender);
        return sb.toString();
    }
    public static String generateRandomID(){
        Random random = new Random();
        String[] areas={"110101", "310101", "440103", "510104"};
        String area=areas[random.nextInt(areas.length)];
        int year=1900+random.nextInt(126);
        int mon=1+random.nextInt(12);
        int day;
        if(mon==2){
            day=1+random.nextInt(28);
        }else if (mon==4||mon==6||mon==9||mon==11){
            day=1+random.nextInt(30);
        }else{
            day=1+random.nextInt(31);
        }
        String birth=String.format("%04d%02d%02d",year,mon,day);
        int sequence=1+random.nextInt(999);
        String seqStr=String.format("%03d",sequence);
        char[] checkCodes={'1','0','X','9','8','7','6','5','4','3','2'};
        char check=checkCodes[random.nextInt(checkCodes.length)];
        return area+birth+seqStr+check;
    }
}

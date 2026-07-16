/**
 * 身份证信息解析器
 * <p>
 *     功能：
 *     <ul>
 *         <li>从控制台接收一个18位身份证号码，验证格式，提取并输出出生日期和性别</li>
 *         <li>随机生成一个模拟的18位身份证号码（校验码为随机值，不可用于真实场景）</li>
 *         <li>将解析结果格式化输出</li>
 *     </ul>
 * </p>
 *<p>
 *     规则说明：
 *     <ul>
 *         <li>身份证号码必须为18位，前17位为数字，末位为数字或'X'/'x'</li>
 *         <li>第7~14位为出生日期，格式yyyyMMdd，需为有效日期</li>
 *         <li>第17位（倒数第二位）判断性别：奇数男，偶数女</li>
 *         <li>随机生成的号码中，校验码未按GB 11643标准精确计算，仅作示例</li>
 *     </ul>
 *</p>
 *
 * @since 2026-07-14
 */
package coreJava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class IDCardParser {
    public static void main() {

        //交互部分
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入18位身份证号码：");
        String id=scanner.nextLine().trim();
        String res=parseIDCard(id);
        System.out.println(res);

        //随机生成演示
        System.out.println("随机生成一个模拟身份证号码：");
        String fakeID=generateRandomID();
        System.out.println("模拟身份证号码："+fakeID);
        System.out.println("解析结果："+parseIDCard(fakeID));
        scanner.close();
    }

    /**
     * 解析18位身份证号码，提取出生日期和性别
     *<p>
     *     验证规则：
     *     <ul>
     *         <li>非空且长度18位</li>
     *         <li>前17位必须全为数字</li>
     *         <li>第18位为数字或'X'/'x'</li>
     *         <li>第7~14位能解析为有效日期</li>
     *     </ul>
     *</p>
     *
     * @param id 身份证号码字符串
     * @return 解析结果字符串，包含出生日期（xxxx年xx月xx日）和性别；若格式错误则返回错误提示
     */
    public static String parseIDCard(String id){

        //格式检查
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

        //提取出生日期字符串并解析为LocalDate
        String birthStr=id.substring(6,14);
        LocalDate birthDate;
        try{
            birthDate=LocalDate.parse(birthStr,DateTimeFormatter.ofPattern("yyyyMMdd"));
        }catch (DateTimeParseException e){
            return "错误，包含无效出生日期信息";
        }

        //格式化出生日期为中文格式
        DateTimeFormatter outputFormatter=DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String formattedBirth=birthDate.format(outputFormatter);

        //判断性别，第17位数字（索引16）
        char genderChar=id.charAt(16);
        int genderNum=genderChar-'0';
        String gender=(genderNum%2==1)? "男":"女";

        //组装结果
        StringBuilder sb=new StringBuilder();
        sb.append("出生日期：").append(formattedBirth).append("性别：").append(gender);

        return sb.toString();
    }

    /**
     * 随机生成一个格式合法的模拟身份证号码
     * <p>
     *     注意：该校验码为随机生成，不符合国标校验规则，仅可用于测试
     * </p>
     *
     * @return 18位模拟身份证号码
     */
    public static String generateRandomID(){

        //常见地区码（6位），这里仅示例几个
        Random random = new Random();
        String[] areas={"110101", "310101", "440103", "510104"};
        String area=areas[random.nextInt(areas.length)];

        // 随机生成出生日期
        int year=1900+random.nextInt(126);
        int mon=1+random.nextInt(12);
        int day;

        // 根据月份确定天数上限，未考虑闰年
        if(mon==2){
            day=1+random.nextInt(28);
        }else if (mon==4||mon==6||mon==9||mon==11){
            day=1+random.nextInt(30);
        }else{
            day=1+random.nextInt(31);
        }
        String birth=String.format("%04d%02d%02d",year,mon,day);

        // 3位顺序码（001~999）
        int sequence=1+random.nextInt(999);
        String seqStr=String.format("%03d",sequence);

        // 随机生成校验码（非标准）
        char[] checkCodes={'1','0','X','9','8','7','6','5','4','3','2'};
        char check=checkCodes[random.nextInt(checkCodes.length)];

        return area+birth+seqStr+check;
    }
}

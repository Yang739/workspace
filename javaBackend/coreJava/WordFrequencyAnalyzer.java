package coreJava;

import java.util.*;

/**
 * 单词频率统计与索引
 * <p>
 *     <ul>
 *         <li>从硬编码英文段落中统计单词频次</li>
 *         <li>按出现次数降序、字母升序排序，输出前5个高频词</li>
 *         <li>收集所有出现字母去重打印，并通过{@link Iterator}删除只出现一次的单词</li>
 *     </ul>
 * </p>
 */
public class WordFrequencyAnalyzer {

    public static void main(String[] args) {

        //硬编码一段英文文本
        String text = "Hello world! Hello Java. Java is great, and Java is powerful. "
                + "The world of Java is amazing. Hello again, world of code. "
                + "Code in Java, code with style. Style matters, style is important.";

        //转小写，去除标点，保留字母和空格
        String cleanedText = text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "");

        //按空白字符拆分单词
        String[] words = cleanedText.split("\\s+");

        //统计词频
        Map<String, Integer> wordCountMap = new HashMap<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        }

        //转化为List并排序
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCountMap.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                //按次数降序
                int cmp = Integer.compare(o2.getValue(), o1.getValue());
                if (cmp != 0) return cmp;

                //次数相同按单词字母升序
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        //打印频率最高的前5个单词
        int limit = Math.min(5, entryList.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            System.out.printf("%s : %d次%n", entry.getKey(), entry.getValue());
        }

        //使用 Set 收集所有出现过的字母,去重，并按自然顺序打印
        Set<Character> letterSet = new TreeSet<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (Character.isLetter(c)) {
                    letterSet.add(c);
                }
            }
        }
        System.out.println(letterSet);

        //使用 Iterator 遍历 Map，删除出现次数为1的单词
        wordCountMap.entrySet().removeIf(entry -> entry.getValue() == 1);
        if (wordCountMap.isEmpty()) {
            System.out.println("所有单词都只出现一次，已全部删除。");
        } else {
            List<Map.Entry<String, Integer>> remainList = new ArrayList<>(wordCountMap.entrySet());
            remainList.sort((e1, e2) -> {
                int cmp = Integer.compare(e2.getValue(), e1.getValue());
                if (cmp != 0) return cmp;
                return e1.getKey().compareTo(e2.getKey());
            });
            for (Map.Entry<String, Integer> entry : remainList) {
                System.out.printf("%s : %d次%n", entry.getKey(), entry.getValue());
            }
        }
    }
}
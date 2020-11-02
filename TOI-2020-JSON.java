import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class JsonMain {
 
    private static final boolean DEBUG = false;
    private static final boolean JSON_RESULT = true;
 
 
    private static final Pattern OBJECT_PATTERN = Pattern.compile("^\\{(?<obj>.*)}$");
    private static final Pattern OBJECT_KEY_PAIR_PATTERN = Pattern.compile("^(?<key>\"\\w+\"):\\s*(?<value>.+)$");
 
    private static final Pattern ARRAY_PATTERN = Pattern.compile("^\\[(?<array>.*)]$");
    private static final List<Object> list = new LinkedList<>();
    private static final Map<String, Object> map = new LinkedHashMap<>();
 
 
    private static final Pattern INPUT_PATTERN = Pattern.compile("\\[(?<in>[^\\[\\]]*)]");
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        var json = scanner.nextLine(); 
        var objMatch = OBJECT_PATTERN.matcher(json); 
        var arrayMatch = ARRAY_PATTERN.matcher(json);
        var objFind = objMatch.find();
        var arrayFind = arrayMatch.find();
        if (!objFind && !arrayFind) {
            System.out.println(json);
            System.out.println("(not a valid json pattern.)");
        }
        if (objFind){
            debug("object find");
            var pairs = objMatch.group("obj");
            parseObject(map, pairs);
            debug("json object result: "+map.toString());
        }
 
        if (arrayFind){
            debug("array find");
            var arr = arrayMatch.group("array");
            parseArray(list, arr);
            debug("json list result: "+list.toString());
        }
        scanner = new Scanner(System.in);
        System.out.println("input your value");
        var input = scanner.nextLine();
        if (!input.startsWith("obj")) {
            System.out.println("must be obj(pattern)");
            return;
        }
 
        input = input.substring(3);
        var inputMatch = INPUT_PATTERN.matcher(input);
        if (objFind){
            var result = parseInput(inputMatch, map);
            if (JSON_RESULT) result = result.toString().replaceAll("=", ":");
            System.out.println("final result: "+result);
        }else if (arrayFind){
            var result = parseInput(inputMatch, list);
            if (JSON_RESULT) result = result.toString().replaceAll("=", ":");
            System.out.println("final result: "+result);
        }else {
            System.out.println(json);
        }
    }
 
    private static Object parseInput(Matcher matcher, Object object){
        if (matcher.find()){
            var parameter = matcher.group("in");
            var index = toInt(parameter);
            if (index != -1){
                if (!(object instanceof List)){
                    System.out.println("getting with ["+index+"] but the value is not array");
                    return object;
                }
                return parseInput(matcher, ((List<?>) object).get(index));
            }else{
                if (!(object instanceof Map)){
                    System.out.println("getting with ["+parameter+"] but the value is not map");
                    return object;
                }
                return parseInput(matcher, ((Map<?, ?>) object).get(parameter));
            }
        }else{
            return object;
        }
    }
 
    private static int toInt(String para){
        try{
            return Integer.parseInt(para);
        }catch (NumberFormatException e){
            return -1;
        }
    }
 
    private static void parseObject(Map<String, Object> map, String parse){
        debug("parsing object: "+parse);
        var arr = parse.split("(?<!\\[\\w\\+)(?<!\\{w\\+),\\s*(?!.+])(?!.+})");
        if (arr.length > 1){
            for (String s : arr) {
                parseObject(map, s);
            }
        }else{
            var keyPairMatcher = OBJECT_KEY_PAIR_PATTERN.matcher(parse);
            if (!keyPairMatcher.find()){
                System.out.println("not a valid json object pattern.");
                return;
            }
            var key = keyPairMatcher.group("key");
            var value = keyPairMatcher.group("value");
            map.put(key, parseValue(value));
        }
    }
 
    private static void parseArray(List<Object> list, String parse){
        debug("parsing array: "+parse);
        var arr = parse.split("(?<!\\[\\w\\+)(?<!\\{w\\+),\\s*(?!.+])(?!.+})");
        if (arr.length > 1){
            for (String s : arr) {
                parseArray(list, s);
            }
        }else{
            var value = arr[0];
            list.add(parseValue(value));
        }
    }
 
    private static Object parseValue(String value){
        var objMatch = OBJECT_PATTERN.matcher(value);
        var arrMatch = ARRAY_PATTERN.matcher(value);
        if (objMatch.find()){
            var pairs = objMatch.group("obj");
            var map = new LinkedHashMap<String, Object>();
            parseObject(map, pairs);
            return map;
        }else if (arrMatch.find()){
            var arr = arrMatch.group("array");
            var list = new LinkedList<>();
            parseArray(list, arr);
            return list;
        }else{
            return value;
        }
    }
 
    private static void debug(String msg){
        if (DEBUG) System.out.println("[DEBUG] "+msg);
    }
 
 
}

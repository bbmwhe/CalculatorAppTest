package calculation;

import java.util.HashMap;
import java.util.Map;
//策略工厂
public class CalculationFactory {
    private static Map<String,Computable> computableMap = new HashMap();
    static{
        computableMap.put("+",new CalculateAdd());
        computableMap.put("-",new CalculateMinus());
        computableMap.put("*",new CalculateMultiply());
        computableMap.put("/",new CalculateDivide());
    }

    public static Computable getComputable(String strategy){
        return computableMap.get(strategy);
    };

    public static boolean isContain(String strategy){
        return computableMap.containsKey(strategy);
    }
}

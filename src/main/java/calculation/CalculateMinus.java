package calculation;
//处理减法的策略类
public class CalculateMinus implements Computable {
    public double computa(double num1, double num2) {
        return num1-num2;
    }
}

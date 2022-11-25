package calculation;
//处理乘法的策略类
public class CalculateMultiply implements Computable {
    public double computa(double num1, double num2) {
        return num1*num2;
    }
}

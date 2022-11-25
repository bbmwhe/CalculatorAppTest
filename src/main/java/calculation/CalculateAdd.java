package calculation;
//处理加法的策略类
public class CalculateAdd implements Computable {
    public double computa(double num1, double num2) {
        return num1+num2;
    }
}

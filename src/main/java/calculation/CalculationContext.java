package calculation;

public class CalculationContext {
    private Computable computable ;

    public CalculationContext(Computable cp){
        this.computable = cp;
    };

    public double computa(double num1,double num2){
        return computable.computa(num1,num2);
    };
}

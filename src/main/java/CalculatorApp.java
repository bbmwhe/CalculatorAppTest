
import calculation.CalculationFactory;
import calculation.HandleUnit;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class CalculatorApp {

    private static Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
//    记录操作记录的栈
    private static Stack<HandleUnit> undoStack = new Stack<HandleUnit>();
//    记录重做记录的栈
    private static Stack<HandleUnit> redoStack = new Stack<HandleUnit>();
//    运算式位置
    static Integer firstInput =1;
    static Integer secondInput =2;
    static Integer thirdInput =3;
//    操作符
    static String operator ="";
//    运算式第N个值
    static Double firstNum = null;
    static Double secondNum = null;

    public static void main(String[] args) {
        String isContinue = "y";
        //写一个接收器
        Scanner scanner = new Scanner(System.in);
        while("y".equals(isContinue.toLowerCase())){
            if (firstNum == null ){
                System.out.println("请输入第一个数据或undo,redo");
                String num1 = scanner.next();
                handleInput(num1, firstInput);
                continue;
            }

            if ("".equals(operator)){
                System.out.println("请选择运算方法(默认为+) +,-,*,/,undo,redo");
                String op = scanner.next();
                handleInput(op, secondInput);
                continue;
            }

            if (secondNum == null ){
                System.out.println("请输入第二个数据或undo,redo");
                String num2 = scanner.next();
                handleInput(num2,thirdInput);
                continue;
            }

            switch (operator){
                case "+" :
                   double resultAdd = CalculationFactory.getComputable(operator).computa(firstNum, secondNum);
                    System.out.println(firstNum + " + " + secondNum + " = " + resultAdd);
                    break;
                case "-" :
                    double resultMinus = CalculationFactory.getComputable(operator).computa(firstNum, secondNum);
                    System.out.println(firstNum + " - " + secondNum + " = " + resultMinus);
                    break;
                case "*" :
                    double resultMultiply = CalculationFactory.getComputable(operator).computa(firstNum, secondNum);
                    System.out.println(firstNum + " * " + secondNum + " = " + resultMultiply);
                    break;
                case "/" :
                    double resultDivide = CalculationFactory.getComputable(operator).computa(firstNum, secondNum);
                    System.out.println(firstNum + " / " + secondNum + " = " + resultDivide);
                    break;
                default:break;
            }
            System.out.println(" 是否继续:y继续,其他输入退出");
            isContinue = scanner.next();
            resetAllValue();
        }
        System.out.println("----任务完成,bye----");
        scanner.close();
    }

//    重设所有算式的项
    private static void resetAllValue() {
        operator="";
        firstNum = null;
        secondNum = null;
    }

    /**
     * 校验输入的内容是不是数字
     * @param input
     * @return
     */
    public static boolean isNumber(String input){
        return numberPattern.matcher(input).matches();
    }

    /**
     * @param input 用户输入的内容
     * @param position 算式的第N个位置
     */
    public static void handleInput(String input,Integer position){
        switch (input){
            case "undo":
                if (!undoStack.empty()){
                    handleUndo();
                }else{
                    System.out.println("没有找到上一步的操作记录");
                }
                break;
            case "redo":
                if (!redoStack.empty()){
                    handleRedo();
                }else{
                    System.out.println("没有找到上一步的操作记录");
                }
                break;
            default:
                if (isNumber(input)){
                    switch (position){
                        case 1:
                            firstNum = Double.parseDouble(input);
                            undoStack.push(new HandleUnit(firstInput,input));
//                            重新输入以后就需要情况redo了
                            redoStack.clear();
                            break;
                        case 3:
                            secondNum = Double.parseDouble(input);
                            undoStack.push(new HandleUnit(thirdInput,input));
                            redoStack.clear();
                            break;
                    }
                }else if (secondInput.equals(position)&&verificationOperator(input)){
                        operator = input;
                        undoStack.push(new HandleUnit(secondInput,input));
                        redoStack.clear();
                }else{
                    System.out.println("输入异常,请重新输入");
                }
        }
    }

//    重做上一步的处理逻辑
    private static void handleRedo() {
        HandleUnit handleUnit =  redoStack.pop();
        //首先把回退步骤放回操作步骤栈
        undoStack.push(handleUnit);
        switch (handleUnit.getPosition()){
            case 3:
                //redo比较简单,直接给对应的位置设置值即可
                secondNum = Double.parseDouble(handleUnit.getValue());
                break;
            case 2:
                //如果回退的是第2个位置的值,就是要重设运算符
                operator = handleUnit.getValue();
                break;
            case 1:
                //如果回退的是第1个位置的值,就是要重设第一个值,直接把第一个值置空
                firstNum = Double.parseDouble(handleUnit.getValue());
                break;
        }
    }

    //    处理回退的命令
    private static void handleUndo() {
        HandleUnit handleUnit =  undoStack.pop();
        redoStack.push(handleUnit);
        switch (handleUnit.getPosition()){
            case 3:
                //如果回退的是第三个位置的值,相当于是已经有一个计算结果,然后重新做计算,这个是否需要把加数和运输符重设
                //这是时候算式的 所有位置应该都是空,直接重新设值即可
                //首先把当前元素放到redoStack,如果没有回退(undo),理论上也没有(redo)
                operator =undoStack.pop().getValue();
                firstNum = Double.parseDouble(undoStack.pop().getValue());
                break;
            case 2:
                //如果回退的是第2个位置的值,就是要重设运算符,把运算符置空
                operator="";
                secondNum = null;
                break;
            case 1:
                //如果回退的是第1个位置的值,就是要重设第一个值,直接把第一个值置空
                resetAllValue();
                break;
        }
    }

//    检查运算符是不是合法的
    private static boolean verificationOperator(String input) {
        return CalculationFactory.isContain(input);
    };
}

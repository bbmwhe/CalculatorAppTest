package calculation;
//用来记录重做的对象
public class HandleUnit {
//    算式值位置
    private Integer position;
//    算式值
    private String value;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HandleUnit(Integer position, String value) {
        this.position = position;
        this.value = value;
    }
}

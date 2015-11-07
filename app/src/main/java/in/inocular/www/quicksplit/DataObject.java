package in.inocular.www.quicksplit;

/**
 * Created by anil on 5/11/15.
 */
public class DataObject {
    private String name;
    private String message;
    private int owedValue;

    DataObject(String text1, String text2, int value){
        name = text1;
        message = text2;
        owedValue = value;
    }

    public String getmText1() {
        return name;
    }

    public void setmText1(String mText1) {
        name = mText1;
    }

    public String getmText2() {
        return message;
    }

    public void setmText2(String mText2) {
        message = mText2;
    }

    public int getmText3() {
        return owedValue;
    }

    public void setmText3(int mText2) {
        owedValue = mText2;
    }
}
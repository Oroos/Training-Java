package test;

public class TestClass
{
    public static void appendTest(StringBuffer test)
    {
        test.append("test");
    }

    public static void main(String[] args)
    {
        StringBuffer test = new StringBuffer();
        appendTest(test);

        // im Buch Clean code wird gesagt das man einfach so methoden aufrufen kann was ich noch nie gesehen habe und 
        // ich auch nicht weiﬂ wie das geht.

        test.appendTestAnders();
    }

    public void appendTestAnders(StringBuffer test)
    {

    }
}

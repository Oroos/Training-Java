package test;

public class TestClass
{
    public void appendTestAnders(final StringBuffer test)
    {

    }

    public static void appendTest(final StringBuffer test)
    {
        test.append("test");
    }

    public static void main(final String[] args)
    {
        final StringBuffer test = new StringBuffer();
        appendTest(test);

        // im Buch Clean code wird gesagt das man einfach so methoden aufrufen kann was ich noch nie gesehen habe und
        // ich auch nicht weiﬂ wie das geht.

        test.appendTestAnders();
    }
}

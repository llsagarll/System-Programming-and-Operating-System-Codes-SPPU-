public class Test
{
    public native int sum(int n1, int n2);
    public static void main(String[] args)
    {
        System.loadLibrary("test");
        Test sample = new Test();
        System.out.println("2 + 3 = : " + sample.sum(2,3));
    }
}

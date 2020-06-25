public class Test {

    public static void main(String[] args) {
        Lol lol = new Lol(32);
        Lol x = lol;
        x.b = 69;
        System.out.println(lol.b);
    }
}

class Lol {
    int b;
    Lol (int i) {
        b = i;
    }
}

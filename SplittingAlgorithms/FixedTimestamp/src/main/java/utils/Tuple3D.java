package utils;

public class Tuple3D<T1, T2, T3> {

    public T1 item1;
    public T2 item2;
    public T3 item3;

    public Tuple3D(
            final T1 item_1,
            final T2 item_2,
            final T3 item_3) {
        item1 = item_1;
        item2 = item_2;
        item3 = item_3;
    }

    public int size() {
        return 3;
    }
       
}
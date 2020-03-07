import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

class ImmutableList<T> {
    private final List<T> list;

    public ImmutableList(List<T> list) {
        this.list = new ArrayList<T>(list);
    }

    @SafeVarargs
    public ImmutableList(T... array) {
        this.list = new ArrayList<T>(Arrays.asList(array));
    }

    public ImmutableList<T> add(T item) {
        List<T> n = new ArrayList<T>(this.list);
        n.add(item);
        return new ImmutableList<T>(n);
    }

    public ImmutableList<T> replace(T t, T item) {
        List<T> n = new ArrayList<T>(this.list);
        Collections.replaceAll(n, t, item);
        return new ImmutableList<T>(n);
    }

    public ImmutableList<T> remove(T t) {
        ArrayList<T> n = new ArrayList<T>(this.list);
        n.remove(t);
        return new ImmutableList<T>(n);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
               
} 

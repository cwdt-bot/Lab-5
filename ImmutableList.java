import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.lang.IllegalArgumentException;
import java.lang.ClassCastException;
import java.lang.IllegalStateException;
import java.lang.NullPointerException;
import java.util.Comparator;

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

    public ImmutableList<T> filter(Predicate<? super T> pred) {
        List<T> newList = new ArrayList<>();
        for (T item : list) {
            if (pred.test(item)) {
                newList.add(item);
            }
        }
        return new ImmutableList<T>(newList);
    }

    public <R> ImmutableList<R> map(Function<? super T, ? extends R> func) {
        List<R> newList = new ArrayList<>();
        for (T item : list) {
            newList.add(func.apply(item));
        }
        return new ImmutableList<R>(newList);
    }

    public ImmutableList<T> limit(long limit) throws IllegalArgumentException {
        if (limit < 0) {
            throw new IllegalArgumentException("limit size < 0");
        } else {
            ArrayList<T> newList = new ArrayList<>(this.list.subList(0, Math.min((int)limit,this.list.size())));
            return new ImmutableList<T>(newList);
        }
    }

    public ImmutableList<T> sorted() {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[])this.list.toArray();
        try {
            Arrays.sort(newArray);
        } catch (ClassCastException e) {
            throw new IllegalStateException("List elements do not implement Comparable");
        }
        return new ImmutableList<T>(Arrays.asList(newArray));
    }

    public ImmutableList<T> sorted(Comparator<? super T> c) {
        if (c == null) {
            throw new NullPointerException("Comparator is null");
        }
        List<T> newList = new ArrayList<T>(this.list);
        newList.sort(c);
        return new ImmutableList<T>(newList);
    }

    public Object[] toArray() {
        return this.list.toArray();
    }

    public <U> U[] toArray(U[] array) {
        try {
            this.list.toArray(array); 
        } catch (ArrayStoreException e1) {
            throw new ArrayStoreException("Cannot add element to array as it is the wrong type");
        } catch (NullPointerException e) {
            throw new NullPointerException("Input array cannot be null");
        }
        return this.list.toArray(array);
    }
}

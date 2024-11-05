/**
 * This class just allows implementation of pairs. Code courtesy of Konrad's lecture slides bar the setSecond and
 * compareTo
 */

public class Pair<T,S> implements Comparable<Pair<T,S>>
{
    private T first;
    private S second;
    public Pair(T firstElement, S secondElement)
    {
        first = firstElement;
        second = secondElement;
    }
    public T getFirst()
    {
        return first;
    }
    public S getSecond()
    {
        return second;
    }
    public void setSecond(S s)
    {
        this.second = s;
    }
    
    @Override
    public int compareTo(Pair<T, S> o) {
        return first.toString().compareTo(o.first.toString());
    }
}

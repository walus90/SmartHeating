package module.control;

/**
 * Created by Wojtek on 2016-06-10.
 * Interface for data stored in
 */
public interface DataSample<E> {
    public E getValue();
    public long getTime();
}

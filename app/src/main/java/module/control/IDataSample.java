package module.control;

import java.util.Date;

/**
 * Created by Wojtek on 2016-06-10.
 * Interface for data stored in
 */
public interface IDataSample<E> {
    public E getValue();
    public Date getTime();
}

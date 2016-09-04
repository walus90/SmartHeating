package module.control;

/**
 * Created by Wojtek on 2016-04-28.
 * Interface for receiving data from sytem and storing them(?)
 */
public interface IDataHandler {
    public byte[] colectData();
    public void sendData(byte[] bytes);
}

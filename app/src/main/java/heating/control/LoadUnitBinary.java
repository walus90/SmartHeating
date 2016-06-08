package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity_;

import org.androidannotations.annotations.EBean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import module.control.BaseUnit;
import module.control.UnitLoader;
import module.control.UnitSaver;

/**
 * Created by Wojtek on 2016-06-01.
 */
@EBean
public class LoadUnitBinary implements UnitLoader{

    Context mContext;
    String mCurentFileName;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
    public void setCurentFileName(String curentFileName) {
        this.mCurentFileName = curentFileName;
    }

    public LoadUnitBinary() {
    }

    @Override
    public BaseUnit loadUnit() {
        if(mCurentFileName==null){
            Log.e(LoadUnitBinary.class.toString(), "Problem with file name!");
        }
        HeatingControlUnit readedUnit = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;
        try {
            fis = mContext.openFileInput(mCurentFileName);
            is = new ObjectInputStream(fis);
            readedUnit = (HeatingControlUnit)is.readObject();
            if(is!=null)
                is.close();
            if(fis!=null)
                fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readedUnit;
    }

    // sets all units in list
    public void readAllUnitsBinary(){
        MainActivity_.sUnitsList.clear();
        String[] names = mContext.fileList();
        for(String s : names){
            if(s.contains("HeatingUnit")) {
                this.setCurentFileName(s);
                MainActivity_.sUnitsList.add((HeatingControlUnit) loadUnit());
            }
        }
    }

}

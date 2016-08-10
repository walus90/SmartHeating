package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;

import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import module.control.BaseUnit;
import module.control.UnitLoader;

/**
 * Created by Wojtek on 2016-06-01.
 */
@EBean
public class LoadUnitBinary implements UnitLoader{

    Context mContext;
    String mCurrentFileName;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
    public void setCurrentFileName(String currentFileName) {
        this.mCurrentFileName = currentFileName;
    }

    public LoadUnitBinary() {
        mContext = MainActivity.getAppContext();
    }

    // An @EBean annotated class must have only one constructor!!!
//    public LoadUnitBinary(String pathToBins) {
//        mContext = MainActivity.getAppContext();
//        mCurrentFileName = pathToBins;
//    }

    @Override
    public BaseUnit loadUnit() {
        if(mCurrentFileName ==null){
            Log.e(LoadUnitBinary.class.toString(), "Problem with file name!");
        }
        HeatingControlUnit readedUnit = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;
        try {
            fis = mContext.openFileInput(mCurrentFileName);
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
    public ArrayList<BaseUnit> readAllUnitsBinary(){
        ArrayList<BaseUnit> unitsFormBin = new ArrayList<>();
        File dirWithBins = new File(this.mCurrentFileName);
        //String[] names = mContext.fileList();
        String[] names = dirWithBins.list();
        if(names!=null) {
            for (String s : names) {
                if (s.contains(HeatingControlUnit.TYPE)) {
                    this.setCurrentFileName(s);
                    unitsFormBin.add((HeatingControlUnit) loadUnit());
                }
            }
        }
        return unitsFormBin;
    }

}

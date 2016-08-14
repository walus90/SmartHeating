package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

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

    @RootContext
    Context mContext;
    String mCurrentFileName;
    String mPathToFiles;

    public String getPathToFiles() {
        return mPathToFiles;
    }

    public void setPathToFiles(String mPathToFiles) {
        this.mPathToFiles = mPathToFiles;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
    public void setCurrentFileName(String currentFileName) {
        this.mCurrentFileName = currentFileName;
    }

    public LoadUnitBinary() {

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
        HeatingControlUnit readUnit = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;
        try {
            fis = mContext.openFileInput(mCurrentFileName);
            is = new ObjectInputStream(fis);
            //set SUID?
            readUnit = (HeatingControlUnit)is.readObject();
            if(is!=null)
                is.close();
            if(fis!=null)
                fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readUnit;
    }

    // sets all units in list
    public ArrayList<BaseUnit> readAllUnitsBinary(){
        ArrayList<BaseUnit> unitsFormBin = new ArrayList<>();
        // TODO I scew up here!!!
        File dirWithBins = new File(getPathToFiles());
        //String[] names = mContext.fileList();
        String[] names = dirWithBins.list();
        if(names!=null) {
            for (String s : names) {
                if (s.contains(HeatingControlUnit.TYPE)) {
                    this.setCurrentFileName(s);
                    unitsFormBin.add((HeatingControlUnit)loadUnit());
                }
            }
        }
        return unitsFormBin;
    }

}

package heating.control;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.smartheting.smartheating.MainActivity_;
import com.smartheting.smartheating.UnitsList_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import module.control.BaseUnit;
import module.control.IUnitLoader;

/**
 * Created by Wojtek on 2016-06-01.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LoadUnitBinary implements IUnitLoader {

    @Pref
    HeatingPrefs_ heatingPrefs;

    @RootContext
    Context mContext;
    // should be private
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
        String pathBin = heatingPrefs.pathToBinUnits().get();
        setPathToFiles(pathBin);
    }

    public LoadUnitBinary() {

    }

    // An @EBean annotated class must have only one constructor!!!
//    public LoadUnitBinary(String pathToBins) {
//        mContext = MainActivity.getAppContext();
//        mCurrentFileName = pathToBins;
//    }

    @Override
    public BaseUnit loadUnit(String unitName) {

        // assigned just for testing
        mCurrentFileName = unitName;
        if(mCurrentFileName ==null){
            Log.e(LoadUnitBinary.class.toString(), "Problem with file name!");
        }
        HeatingControlUnit readUnit = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;
        try {
            File file = new File(getPathToFiles()+File.separator+unitName);
            fis = new FileInputStream(file);
            //fis = mContext.openFileInput(mCurrentFileName);
            is = new ObjectInputStream(fis);
            //set SUID?
            readUnit = (HeatingControlUnit)is.readObject();
            is.close();
            if(fis!=null)
                fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(LoadUnitBinary_.class.getName(), e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i(LoadUnitBinary_.class.getName(), e.getMessage());
        }
        return readUnit;
    }

    // sets all units in list
    public ArrayList<BaseUnit> readAllUnitsBinary(){
        ArrayList<BaseUnit> unitsFormBin = new ArrayList<>();
        File dirWithBins = new File(getPathToFiles());
        String[] names = dirWithBins.list();
        if(names!=null) {
            for (String s : names) {
                if (s.contains(HeatingControlUnit.TYPE)) {
                    // base class has no parameters, so need to set here
                    this.setCurrentFileName(s);
                    HeatingControlUnit hcuToAdd = (HeatingControlUnit)loadUnit(s);
                    //check if not null
                    if(hcuToAdd != null)
                        unitsFormBin.add(hcuToAdd);
                }
            }
        }
        return unitsFormBin;
    }

    public void deleteAllBinariesUnits(){
        File dirWithBins = new File(getPathToFiles());
        String[] names = dirWithBins.list();
        if(names!=null) {
            for (String s : names) {
                if (s.contains(HeatingControlUnit.TYPE)) {
                    String numToRemove = s.substring(s.length()-1);
                    UnitsList_.getUnitList().remove(Integer.valueOf(numToRemove));
                    File binToRemove = new File(dirWithBins+File.separator+s);
                    boolean stat = binToRemove.delete();
                    if(!stat) {
                        Log.i(LoadUnitBinary_.class.toString(), "Problem with removing file");
                        Toast.makeText(MainActivity_.getAppContext(), "Problem with removing file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}

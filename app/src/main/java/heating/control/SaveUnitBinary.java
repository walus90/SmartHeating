package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import module.control.BaseUnit;
import module.control.IUnitSaver;

/**
 * Created by Wojtek on 2016-05-30.
 */
@EBean
public class SaveUnitBinary implements IUnitSaver {

    @Pref
    HeatingPrefs_ prefs;

    private Context mContext;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    String path;

    public SaveUnitBinary(){
        mContext = MainActivity_.getAppContext();
    }
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void saveUnit(BaseUnit unit) {
        if(unit instanceof HeatingControlUnit) {
            HeatingControlUnit huToSave = (HeatingControlUnit)unit;
            String fileName = HeatingControlUnit.TYPE + huToSave.getId();
            FileOutputStream fos = null;
            ObjectOutputStream os = null;
            File saveDir = new File(this.getPath());
            if(!saveDir.exists())
                saveDir.mkdirs();
            try {
                String unitPath = saveDir+File.separator+fileName;
                File f = new File(unitPath);
                boolean status=false;
                if( !f.exists() )
                    status = f.createNewFile();
                status = f.canWrite();
                Log.i(this.getClass().getName(), "status result after check if can write = " + status + "\n");
                if(!f.isFile()){
                    Log.i(this.getClass().getName(), "this is not a file\n");
                    f.delete();
                    f.createNewFile();
                }
                if(!f.isDirectory()){
                    Log.i(this.getClass().getName(), "this is not a directory\n");
                }

                fos = new FileOutputStream(f);
                os = new ObjectOutputStream(fos);
                os.writeObject(huToSave);
                Log.i(this.getClass().getName(), "Heating unit " + huToSave.getId() + " saved\n");
                os.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(SaveUnitBinary_.class.getName(), e.getMessage());
            }
        }else{
            Log.i(this.getClass().getName(), "Unknown type of unit\n");
        }
    }

}

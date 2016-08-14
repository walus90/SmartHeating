package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import module.control.BaseUnit;
import module.control.UnitSaver;

/**
 * Created by Wojtek on 2016-05-30.
 */
@EBean
public class SaveUnitBinary implements UnitSaver {

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
        mContext = MainActivity.getAppContext();
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
            File saveDir = new File(this.getPath(),"");
            if(!saveDir.exists())
                saveDir.mkdirs();
            try {
                //fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                //fos = new FileOutputStream(new File(prefs.pathToBinUnits().get(),"")+File.separator+fileName);

                //throws file not found exception
                fos = new FileOutputStream(saveDir+File.separator+fileName);
                os = new ObjectOutputStream(fos);
                os.writeObject(huToSave);
                Log.i(this.getClass().getName(), "Heating unit " + huToSave.getId() + " saved\n");
                if (os != null)
                    os.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(this.getClass().getName(), "Unknown type of unit\n");
        }
    }

}

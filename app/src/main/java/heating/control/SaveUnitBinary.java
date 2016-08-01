package heating.control;

import android.content.Context;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;
import com.smartheting.smartheating.MainActivity_;

import org.androidannotations.annotations.EBean;

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

    private Context mContext;

    public SaveUnitBinary(){
        mContext = MainActivity.getAppContext();
    }
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void saveUnit(BaseUnit unit) {
        if(unit instanceof HeatingControlUnit) {
            String fileName = HeatingControlUnit.TYPE + ((HeatingControlUnit) unit).getId();
            FileOutputStream fos = null;
            ObjectOutputStream os = null;
            try {
                fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                os = new ObjectOutputStream(fos);
                os.writeObject(unit);
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

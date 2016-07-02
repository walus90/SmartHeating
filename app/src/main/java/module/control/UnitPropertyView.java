package module.control;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Wojtek on 2016-07-02.
 */
@EViewGroup
public class UnitPropertyView extends RelativeLayout{

    @ViewById
    TextView tvParameterName;
    @ViewById
    EditText etParameterValue;
    @ViewById
    Button bEdit;

    public UnitPropertyView(Context context) {
        super(context);
    }


}

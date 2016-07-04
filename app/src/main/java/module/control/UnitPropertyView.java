package module.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartheting.smartheating.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Wojtek on 2016-07-02.
 */
@EViewGroup(R.layout.unit_property_view)
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

    public UnitPropertyView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public UnitPropertyView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public UnitPropertyView(Context context, String name, String value, boolean editable){
        super(context);
        tvParameterName.setText(name);
        etParameterValue.setText(value);
        if(!editable) {
            etParameterValue.setClickable(false);
            etParameterValue.setFocusable(false);
            etParameterValue.setEnabled(false);
            etParameterValue.setKeyListener(null);
            bEdit.setVisibility(GONE);
        }
    }

    public void setView(String name, String value, boolean editable){
        tvParameterName.setText(name);
        etParameterValue.setText(value);
        if(!editable) {
            etParameterValue.setEnabled(false);
            etParameterValue.setKeyListener(null);
            bEdit.setVisibility(GONE);
        }
    }

    public String getPropertyValue(){
        return this.etParameterValue.getText().toString();
    }

}

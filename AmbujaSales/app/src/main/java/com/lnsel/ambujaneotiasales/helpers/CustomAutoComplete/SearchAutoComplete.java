package com.lnsel.ambujaneotiasales.helpers.CustomAutoComplete;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import java.util.HashMap;

/**
 * Created by apps2 on 5/12/2017.
 */
public class SearchAutoComplete extends AutoCompleteTextView {

    public SearchAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** Returns the place description corresponding to the selected item */
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        /** Each item in the autocompetetextview suggestion list is a hashmap object */
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        return hm.get("cusFirstName");
    }

}

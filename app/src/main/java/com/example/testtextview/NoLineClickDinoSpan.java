
package com.example.testtextview;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

public class NoLineClickDinoSpan extends ClickableSpan {
    private int colorDinoId;
    private boolean isShowDinoUnderLine = false;
    private Context dinoContext;
    private int dinoColor;
    private WeakReference<View.OnClickListener> listenerDinoRef;

    public NoLineClickDinoSpan(Context dinoContext, int colorDinoId, View.OnClickListener listener) {
        super();
        if(listener != null)
            listenerDinoRef = new WeakReference<>(listener);
        this.dinoContext = dinoContext.getApplicationContext();
        this.colorDinoId = colorDinoId;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (dinoContext == null) {
            ds.setColor(dinoColor);
        } else {
            try {
                ds.setColor(ContextCompat.getColor(dinoContext, colorDinoId));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }

        ds.setUnderlineText(isShowDinoUnderLine);

    }

    public NoLineClickDinoSpan(int dinoColor, View.OnClickListener listener) {
        super();
        if(listener != null)
            listenerDinoRef = new WeakReference<>(listener);
        this.dinoColor = dinoColor;
    }

    public void setShowDinoUnderLine(boolean isShowUnderLine) {
        this.isShowDinoUnderLine = isShowUnderLine;
    }

    @Override
    public void onClick(View widget) {
        if(listenerDinoRef != null) {
            View.OnClickListener listener = listenerDinoRef.get();
            if (listener != null)
                listener.onClick(widget);
        }
    }

}

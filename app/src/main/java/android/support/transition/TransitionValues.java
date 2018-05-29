package android.support.transition;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitionValues {
    final ArrayList<Transition> mTargetedTransitions;
    public final Map<String, Object> values;
    public View view;

    public TransitionValues() {
        this.values = new HashMap();
        this.mTargetedTransitions = new ArrayList();
    }

    public boolean equals(Object other) {
        return (other instanceof TransitionValues) && this.view == ((TransitionValues) other).view && this.values.equals(((TransitionValues) other).values);
    }

    public int hashCode() {
        return (this.view.hashCode() * 31) + this.values.hashCode();
    }

    public String toString() {
        String returnValue = (("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.view + "\n") + "    values:";
        for (String s : this.values.keySet()) {
            returnValue = returnValue + "    " + s + ": " + this.values.get(s) + "\n";
        }
        return returnValue;
    }
}

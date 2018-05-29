package com.daimajia.swipe.interfaces;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl.Mode;
import java.util.List;

public interface SwipeItemMangerInterface {
    void closeAllExcept(SwipeLayout swipeLayout);

    void closeItem(int i);

    Mode getMode();

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    boolean isOpen(int i);

    void openItem(int i);

    void removeShownLayouts(SwipeLayout swipeLayout);

    void setMode(Mode mode);
}

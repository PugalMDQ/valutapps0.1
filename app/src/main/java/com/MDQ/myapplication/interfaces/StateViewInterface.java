package com.MDQ.myapplication.interfaces;

import com.MDQ.myapplication.enums.MessageViewType;
import com.MDQ.myapplication.enums.ViewType;

public interface StateViewInterface {
        void ShowErrorMessage(MessageViewType messageViewType, String errorMessage);
        void ShowErrorMessage(MessageViewType messageViewType, ViewType viewType, String errorMessage);

    }


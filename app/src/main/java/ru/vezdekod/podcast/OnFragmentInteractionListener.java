package ru.vezdekod.podcast;

import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;

public interface OnFragmentInteractionListener {
    void onFragmentInteraction(NavDirections navDirections);

    void setBackDirection(@Nullable NavDirections navDirections);
}

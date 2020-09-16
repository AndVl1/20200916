package ru.vezdekod.podcast.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import ru.vezdekod.podcast.OnFragmentInteractionListener;
import ru.vezdekod.podcast.R;

public class PodcastPreviewFragment extends Fragment {

    private OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener = (OnFragmentInteractionListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_podcast_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fragment_podcast_preview___button___publish_podcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = PodcastPreviewFragmentDirections.actionNavPodcastPreviewToNavEnd();
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            }
        });
    }
}

package ru.vezdekod.podcast.fragments;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import ru.vezdekod.podcast.OnFragmentInteractionListener;
import ru.vezdekod.podcast.R;
import ru.vezdekod.podcast.databinding.FragmentMainPodcastDataBinding;
import ru.vezdekod.podcast.model.PodcastViewModel;

public class MainPodcastDataFragment extends Fragment {

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private FragmentMainPodcastDataBinding viewBinding;
    private PodcastViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        onFragmentInteractionListener.setBackDirection(MainPodcastDataFragmentDirections.actionNavMainPodcastDataToNavFirst());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = FragmentMainPodcastDataBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.screen_title_new_podcast);
            actionBar.show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PodcastViewModel.class);
        viewBinding.fragmentMainPodcastDataButtonNext.setEnabled(viewModel.getMediaPlayer() != null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding.fragmentMainPodcastDataButtonNext.setOnClickListener(v -> {
            NavDirections navDirections = MainPodcastDataFragmentDirections.actionNavMainPodcastDataToNavAudioEditing();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });

        viewBinding.loadAudioButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("audio/*");
            startActivityForResult(Intent.createChooser(intent, "Select audio"), 666);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 666 && data != null) {
            Uri fileUri = data.getData();
            if (fileUri == null) return;

            viewModel.setFileUri(fileUri);

            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(requireContext(), fileUri);
                viewModel.setMediaPlayer(player);
            } catch (IOException e) {
                e.printStackTrace();
                viewModel.setMediaPlayer(null);
            }
            viewBinding.fragmentMainPodcastDataButtonNext.setEnabled(viewModel.getMediaPlayer() != null);
        }
    }
}

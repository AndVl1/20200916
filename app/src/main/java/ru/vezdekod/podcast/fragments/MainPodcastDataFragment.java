package ru.vezdekod.podcast.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import java.io.IOException;

import ru.vezdekod.podcast.OnFragmentInteractionListener;
import ru.vezdekod.podcast.R;
import ru.vezdekod.podcast.databinding.FragmentMainPodcastDataBinding;
import ru.vezdekod.podcast.model.PodcastViewModel;

public class MainPodcastDataFragment extends Fragment {

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private FragmentMainPodcastDataBinding viewBinding;
    private PodcastViewModel viewModel;
    private static final int REQUEST_CODE_MUSIC = 666;
    private static final int REQUEST_CODE_IMAGE = 111;

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
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
            actionBar.setTitle(R.string.screen_title_new_podcast);
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
            String podcastName = viewBinding.fragmentMainPodcastDataEditTextPodcastName.getText().toString();
            String podcastDescription = viewBinding.fragmentMainPodcastDataEditTextPodcastDescription.getText().toString();
            if (podcastName.length() != 0 && podcastDescription.length() != 0) {
                viewModel.setPodcastName(podcastName);
                viewModel.setPodcastDescription(podcastDescription);
                NavDirections navDirections = MainPodcastDataFragmentDirections.actionNavMainPodcastDataToNavAudioEditing();
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            }
            else {
                Toast.makeText(requireActivity(), "Должны быть заполнены название и описание",  Toast.LENGTH_SHORT).show();
            }
        });

        viewBinding.loadAudioButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("audio/*");
            startActivityForResult(Intent.createChooser(intent, "Select audio"), REQUEST_CODE_MUSIC);
        });

        viewBinding.loadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            Uri fileUri = data.getData();
            if (fileUri == null) return;
            if (requestCode == REQUEST_CODE_MUSIC) {

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

            if (requestCode == REQUEST_CODE_IMAGE) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), fileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewBinding.loadImage.setImageBitmap(bitmap);
            }
        }
    }
}

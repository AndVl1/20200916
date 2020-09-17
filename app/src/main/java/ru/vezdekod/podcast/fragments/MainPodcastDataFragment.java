package ru.vezdekod.podcast.fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private static final int REQUEST_CODE_MUSIC = 666;
    private static final int REQUEST_CODE_IMAGE = 111;

    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 22;

    private static final int REQUEST_BROWSE_PICTURE = 2;

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

        viewBinding.adultContentCb.setChecked(viewModel.getAdultContent());
        viewBinding.excludeExportCb.setChecked(viewModel.getExcludeFromExport());
        viewBinding.trailerCb.setChecked(viewModel.getTrailer());

        viewBinding.adultContentCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setAdultContent(isChecked);
        });

        viewBinding.excludeExportCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setExcludeFromExport(isChecked);
        });

        viewBinding.trailerCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setTrailer(isChecked);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding.fragmentMainPodcastDataImageButtonLoadImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "permission not", Toast.LENGTH_SHORT).show();

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                createImageBrowsingRequest();
            }
        });

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

    private void createImageBrowsingRequest() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_BROWSE_PICTURE);
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

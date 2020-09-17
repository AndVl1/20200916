package ru.vezdekod.podcast.fragments;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentResolver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
            String podcastName = viewBinding.fragmentMainPodcastDataEditTextPodcastName.getText().toString();
            String podcastDescription = viewBinding.fragmentMainPodcastDataEditTextPodcastDescription.getText().toString();
            if (podcastName.length() != 0 && podcastDescription.length() != 0) {
                viewModel.setPodcastName(podcastName);
                viewModel.setPodcastDescription(podcastDescription);
                NavDirections navDirections = MainPodcastDataFragmentDirections.actionNavMainPodcastDataToNavPodcastPreview();
                onFragmentInteractionListener.onFragmentInteraction(navDirections);
            } else {
                Toast.makeText(requireActivity(), "Должны быть заполнены название и описание", Toast.LENGTH_SHORT).show();
            }
        });

        viewBinding.fragmentMainPodcastDataButtonAudioEditing.setOnClickListener(v -> {
            NavDirections navDirections = MainPodcastDataFragmentDirections.actionNavMainPodcastDataToNavAudioEditing();
            onFragmentInteractionListener.onFragmentInteraction(navDirections);
        });

        viewBinding.loadAudioButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("audio/*");
            startActivityForResult(Intent.createChooser(intent, "Select audio"), REQUEST_CODE_MUSIC);
        });

        viewBinding.fragmentMainPodcastDataImageButtonLoadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_IMAGE);
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
        switch (requestCode) {
            case 666:
                if (data != null) {
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
                    viewBinding.fragmentMainPodcastDataLinearLayoutLoadAudio.setVisibility(View.GONE);
                    viewBinding.fragmentMainPodcastDataLinearLayoutAudio.setVisibility(View.VISIBLE);
                    viewBinding.fragmentMainPodcastDataButtonNext.setEnabled(viewModel.getMediaPlayer() != null);
                }
                break;
            case REQUEST_CODE_IMAGE:
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    Bitmap podcastImage = getBitmap(requireActivity().getContentResolver(), selectedImage, 1920, 1080);
                    viewBinding.fragmentMainPodcastDataImageButtonLoadImage.setImageBitmap(null);
                    viewBinding.fragmentMainPodcastDataImageButtonLoadImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewBinding.fragmentMainPodcastDataImageButtonLoadImage.setImageBitmap(podcastImage);
                    viewModel.setPodcastImage(podcastImage);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            createImageBrowsingRequest();
        }
    }

    private static Bitmap getBitmap(ContentResolver contentResolver, Uri selectedImage,
                                    int targetWidth, int targetHeight) {
        InputStream is = null;
        try {
            is = contentResolver.openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        try {
            is = contentResolver.openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int bitmapWidth, int bitmapHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > bitmapHeight || width > bitmapWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > bitmapHeight
                    && (halfWidth / inSampleSize) > bitmapWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

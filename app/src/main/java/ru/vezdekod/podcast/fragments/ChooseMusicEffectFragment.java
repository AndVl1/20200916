package ru.vezdekod.podcast.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.vezdekod.podcast.OnFragmentInteractionListener;
import ru.vezdekod.podcast.R;

public class ChooseMusicEffectFragment extends Fragment {
    private EditText searchEditText;
    private RecyclerView listOfMusic;

    private OnFragmentInteractionListener onFragmentInteractionListener = null;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        onFragmentInteractionListener.setBackDirection(ChooseMusicEffectFragmentDirections.actionNavChooseMusicEffectToNavAudioEditing());
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.screen_title_choose_music);
            actionBar.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_music_effect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Выбрать музыку");
        setHasOptionsMenu(true);
    }

    private void initViews(View view) {
        searchEditText = view.findViewById(R.id.search_edit_text);
        listOfMusic = view.findViewById(R.id.list_of_music);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.choose_music_menu, menu);
        MenuItem itemAdd = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_add == item.getItemId()) {
            /** Добавить трек со своего телефона*/
            Toast.makeText(getContext(), "Add", Toast.LENGTH_LONG).show();
        }
        if (android.R.id.home == item.getItemId()) {
            /**Навигиция назад*/
            Toast.makeText(getContext(), "Back", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

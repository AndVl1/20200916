package ru.vezdekod.podcast.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.vezdekod.podcast.R;

public class ChooseMusicEffectFragment extends Fragment {
    private EditText searchEditText;
    private RecyclerView listOfMusic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_music_effect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setHasOptionsMenu(true);
    }

    private void initViews(View view) {
        searchEditText = view.findViewById(R.id.search_edit_text);
        listOfMusic = view.findViewById(R.id.list_of_music);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.choose_music_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.action_add==item.getItemId()){
            /** Добавить трек со своего телефона*/
            Toast.makeText(getContext(),"Add",Toast.LENGTH_LONG).show();
        }
        if(R.id.action_back==item.getItemId()){
            /** Назад, навигацию настроить*/
            Toast.makeText(getContext(),"Back",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

package com.example.news.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.news.R;
import com.example.news.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String searchType = null;
    private String sortType = null;
    private String category = null;
    private String country = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.news.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.bottomNavView);
        NavController navController = Navigation.findNavController(this, R.id.viewFragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public void dialogSearch(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_search, null);

        EditText searchQuery = v.findViewById(R.id.edSearchQuery);
        Button btnSearch = v.findViewById(R.id.btnSearch);
        RadioGroup radioSearch = v.findViewById(R.id.radioSearch);
        RadioButton radioTitle = v.findViewById(R.id.radioSearchTitle);
        RadioButton radioDesc = v.findViewById(R.id.radioSearchDesc);

        builder.setView(v);

        AlertDialog dialogSearch = builder.create();

        if(dialogSearch.getWindow() != null){
            dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            radioSearch.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioSearchTitle){
                    searchType = radioTitle.getText().toString().toLowerCase();
                } else if (checkedId == R.id.radioSearchDesc) {
                    searchType = radioDesc.getText().toString().toLowerCase();
                } else {
                    searchType = null;
                }
            });
            btnSearch.setOnClickListener(view-> doSearch(searchQuery.getText().toString(), searchType));

            searchQuery.setOnEditorActionListener((v1, actionId, event) -> {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    doSearch(searchQuery.getText().toString(), searchType);
                }
                return false;
            });
            dialogSearch.show();
        }
    }

    private void doSearch(String query, String searchIn){
        if(query.isEmpty()){
            Toast.makeText(getApplicationContext(), "No Search Query !!!", Toast.LENGTH_SHORT).show();
        }

        if(searchIn == null){
            Toast.makeText(getApplicationContext(), "No Search Type !!!", Toast.LENGTH_SHORT).show();
        } else if(searchIn.equalsIgnoreCase("Title")){
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            i.putExtra("searchIn", searchIn);
            i.putExtra("searchQuery", query);
            startActivity(i);
        } else if(searchIn.equalsIgnoreCase("Description")){
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            i.putExtra("searchIn", searchIn);
            i.putExtra("searchQuery", query);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Search Type !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogSort(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sort, null);

        Button btnSort = v.findViewById(R.id.btnSort);
        RadioGroup radioSort = v.findViewById(R.id.radioSort);

        builder.setView(v);

        AlertDialog dialogSort = builder.create();

        if(dialogSort.getWindow() != null){
            dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            radioSort.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioSortPopularity){
                    sortType = "popularity";
                } else if (checkedId == R.id.radioSortDate) {
                    sortType = "publishedAt";
                } else {
                    sortType = null;
                }
            });
            btnSort.setOnClickListener(view-> doSort(sortType));
            dialogSort.show();
        }
    }

    private void doSort(String sortBy){
        if(sortBy == null){
            Toast.makeText(getApplicationContext(), "No Sort Type !!!", Toast.LENGTH_SHORT).show();
        } else if(sortBy.equals("popularity")){
            Intent i = new Intent(MainActivity.this, SortActivity.class);
            i.putExtra("sortBy", sortBy);
            startActivity(i);
        } else if (sortBy.equals("publishedAt")){
            Intent i = new Intent(MainActivity.this, SortActivity.class);
            i.putExtra("sortBy", sortBy);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Sort Type !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void dialogFilter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_filter, null);

        builder.setView(v);
        AlertDialog dialogFilter = builder.create();

        if(dialogFilter.getWindow() != null){
            dialogFilter.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            Spinner spinnerFilterCategory = v.findViewById(R.id.spinnerFilterCategory);
            Spinner spinnerFilterCountry = v.findViewById(R.id.spinnerFilterCountry);
            Button btnFilter = v.findViewById(R.id.btnFilter);

            ArrayAdapter<String> filterNewsCategory = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    getResources().getStringArray(R.array.newsCategoryList));

            ArrayAdapter<String> filterNewsCountry = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    getResources().getStringArray(R.array.newsCountryList));

            filterNewsCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            filterNewsCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerFilterCategory.setAdapter(filterNewsCategory);
            spinnerFilterCountry.setAdapter(filterNewsCountry);

            spinnerFilterCategory.setOnItemSelectedListener(this);
            spinnerFilterCountry.setOnItemSelectedListener(this);

            btnFilter.setOnClickListener(view -> doFilter(category, country));
            dialogFilter.show();
        }
    }

    private void doFilter(String category, String country){
        Intent i = new Intent(MainActivity.this, FilterActivity.class);

        i.putExtra("category", category);
        i.putExtra("country", country);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.searchMenu){
            dialogSearch();
        } else if (item.getItemId() == R.id.sortMenu){
            dialogSort();
        } else if (item.getItemId() == R.id.filterMenu){
            dialogFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categorySelected = parent.getItemAtPosition(position).toString().toLowerCase();
        String countrySelected = parent.getItemAtPosition(position).toString().toLowerCase();

        switch(categorySelected){
            case "business" :
                category = "business";
                break;

            case "entertainment" :
                category = "entertainment";
                break;

            case "general" :
                category = "general";
                break;

            case "health" :
                category = "health";
                break;

            case "science" :
                category = "science";
                break;

            case "sports" :
                category = "sports";
                break;

            case "technology" :
                category = "technology";
                break;

            case "choose category" :
                category = null;
                break;
        }

        switch(countrySelected){
            case "australia" :
                country = "au";
                break;

            case "chinese" :
                country = "cn";
                break;

            case "france" :
                country = "fr";
                break;

            case "indonesia" :
                country = "id";
                break;

            case "japan" :
                country = "jp";
                break;

            case "south korea" :
                country = "kr";
                break;

            case "russia" :
                country = "ru";
                break;

            case "thailand" :
                country = "th";
                break;

            case "united kingdom" :
                country = "uk";
                break;

            case "united states" :
                country = "us";
                break;

            case "choose country" :
                country = null;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
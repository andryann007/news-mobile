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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MainActivity extends AppCompatActivity{

    private String searchType = null;
    private String sortType = null;
    private String filterCategory = null;
    private String filterCountry = null;

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

            searchQuery.setOnEditorActionListener((v1, actionid, event) -> {
                if(actionid == EditorInfo.IME_ACTION_GO){
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

        Button btnFilter = v.findViewById(R.id.btnFilter);
        RadioGroup radioFilterCategory = v.findViewById(R.id.radioFilter);
        RadioGroup radioFilterCountry = v.findViewById(R.id.radioFilter2);

        builder.setView(v);

        AlertDialog dialogFilter = builder.create();
        if(dialogFilter.getWindow() != null){
            dialogFilter.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            radioFilterCountry.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioFilterAmerica){
                    filterCountry = "us";
                } else if (checkedId == R.id.radioFilterChinese) {
                    filterCountry = "cn";
                } else if (checkedId == R.id.radioFilterJapan) {
                    filterCountry = "jp";
                } else if (checkedId == R.id.radioFilterIndonesian) {
                    filterCountry = "id";
                } else if (checkedId == R.id.radioFilterKorean) {
                    filterCountry = "kr";
                } else if (checkedId == R.id.radioFilterRusian) {
                    filterCountry = "ru";
                } else if (checkedId == R.id.radioFilterThailand) {
                    filterCountry = "th";
                } else {
                    filterCountry = null;
                }
            });

            radioFilterCategory.setOnCheckedChangeListener((group, checkedId) -> {
                if(checkedId == R.id.radioFilterBusiness){
                    filterCategory = "business";
                } else if (checkedId == R.id.radioFilterEntertainment){
                    filterCategory = "entertainment";
                } else if (checkedId == R.id.radioFilterGeneral){
                    filterCategory = "general";
                } else if (checkedId == R.id.radioFilterHealth){
                    filterCategory = "health";
                } else if (checkedId == R.id.radioFilterScience){
                    filterCategory = "science";
                } else if (checkedId == R.id.radioFilterSports){
                    filterCategory = "sports";
                } else if (checkedId == R.id.radioFilterTechnology){
                    filterCategory = "technology";
                } else {
                    filterCategory = null;
                }
            });
            btnFilter.setOnClickListener(view-> doFilter(filterCategory, filterCountry));
            dialogFilter.show();
        }
    }

    private void doFilter(String category, String country){
        if(category == null && country == null){
            Toast.makeText(getApplicationContext(), "No Filter Type !!!", Toast.LENGTH_SHORT).show();
        } else if (category == null) {
            switch (country) {
                case "us": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "us");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "cn": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "cn");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "jp": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "jp");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "id": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "id");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "kr": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "kr");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "ru": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "ru");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
                case "th": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "th");
                    i.putExtra("category", "");
                    startActivity(i);
                    break;
                }
            }
        } else if (country == null) {
            switch (category) {
                case "business": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "business");
                    startActivity(i);
                    break;
                }
                case "entertainment": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "entertainment");
                    startActivity(i);
                    break;
                }
                case "general": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "general");
                    startActivity(i);
                    break;
                }
                case "health": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "health");
                    startActivity(i);
                    break;
                }
                case "science": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "science");
                    startActivity(i);
                    break;
                }
                case "sports": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "sports");
                    startActivity(i);
                    break;
                }
                case "technology": {
                    Intent i = new Intent(MainActivity.this, FilterActivity.class);
                    i.putExtra("country", "");
                    i.putExtra("category", "technology");
                    startActivity(i);
                    break;
                }
            }
        } else {
            switch (category) {
                case "business":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "business");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "entertainment":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "entertainment");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "general":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "general");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "health":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "health");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "science":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "science");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "sports":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "sports");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                case "technology":
                    switch (country) {
                        case "us": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "us");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "cn": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "cn");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "jp": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "jp");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "id": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "id");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "kr": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "kr");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "ru": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "ru");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                        case "th": {
                            Intent i = new Intent(MainActivity.this, FilterActivity.class);
                            i.putExtra("country", "th");
                            i.putExtra("category", "technology");
                            startActivity(i);
                            break;
                        }
                    }
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Invalid Filter Type !!!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
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
}
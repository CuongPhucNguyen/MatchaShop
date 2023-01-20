package com.example.matchashop.MainFragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.adapters.ProductAdapter;
import com.example.matchashop.databinding.FragmentHomeBinding;
import com.example.matchashop.models.ProductModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView productRV;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> productModelArrayList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);//Make sure you have this line of code.
        View root = binding.getRoot();
        

        // this.getActivity().setContentView(R.layout.fragment_home);

        // initializing our variables.
        productRV = binding.getRoot().findViewById(R.id.idRVProducts);

        TextView ascendFunc = (TextView) binding.getRoot().findViewById(R.id.asFilter);
        ascendFunc.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                         Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                                         buildRecyclerViewForAscendingOrder();
                                          Toast.makeText(getContext(),"Hello Javatpoint 2",Toast.LENGTH_SHORT).show();

                                      }
        });

        /*
        TextView descendFunc = (TextView) binding.getRoot().findViewById(R.id.desFilter);
        ascendFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });*/


        // calling method to
        // build recycler view.
        buildRecyclerView();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,@NonNull MenuInflater inflater) {
        // below line is to get our inflater
        // MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });

    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<ProductModel> filteredlist = new ArrayList<ProductModel>();

        // running a for loop to compare elements.
        for (ProductModel item : productModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }



        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this.getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();

            //Below line is to clear the recycler view of the search result
            // adapter.nullList();

        } else {

            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }




    private void buildRecyclerView() {

        // below line we are creating a new array list
        productModelArrayList = new ArrayList<ProductModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference docRef = db.collection("products");
        ArrayList<String> idList = new ArrayList<String>();
        //iterate through collection
        docRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                //get data from document
                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                //add data to array list
                idList.add(documentSnapshot.getId());
                productModelArrayList.add(productModel);
            }
            // initializing our adapter class.
            adapter = new ProductAdapter(productModelArrayList, this.getContext(), idList);

            // setting layout manager for our recycler view.
            productRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

            // setting adapter to our recycler view.
            productRV.setAdapter(adapter);
        });

    }

    private void buildRecyclerViewForAscendingOrder() {

        // below line we are creating a new array list
        productModelArrayList = new ArrayList<ProductModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference docRef = db.collection("products");
        ArrayList<String> idList = new ArrayList<String>();
        //iterate through collection
        docRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                //get data from document
                ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                //add data to array list
                idList.add(documentSnapshot.getId());
                productModelArrayList.add(productModel);
            }

            for (int i = 0; i < productModelArrayList.size(); i++) {
                for (int j = i + 1; j < productModelArrayList.size(); j++) {
                    if (productModelArrayList.get(i).getProductPrice() > productModelArrayList.get(j).getProductPrice()) {
                        ProductModel temp = productModelArrayList.get(i);
                        productModelArrayList.set(i, productModelArrayList.get(j));
                        productModelArrayList.set(j, temp);
                    }
                }
            }



            // initializing our adapter class.
            adapter = new ProductAdapter(productModelArrayList, this.getContext(), idList);

            // setting layout manager for our recycler view.
            productRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

            // setting adapter to our recycler view.
            productRV.setAdapter(adapter);
        });

    }


}

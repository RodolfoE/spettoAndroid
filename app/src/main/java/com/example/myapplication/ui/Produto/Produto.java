package com.example.myapplication.ui.Produto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adaptador.ListaProduto;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.servico.Chamadas.getProdutos;

public class Produto extends Fragment {
    private View mRoot;
    private RecyclerView mRecyclerView;
    private DemoCollectionPagerAdapter demoCollectionPagerAdapter;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_produto, container, false);
        mRoot = root;
        initComponents();

        return root;
    }

    private void initComponents() {
        //initList();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(demoCollectionPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // Instances of this class are fragments representing a single
    // object in our collection.
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";
        private View mRoot;
        private RecyclerView mRecyclerView;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.content_produtos, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mRoot = view;
            Bundle args = getArguments();
            initList();

            //((TextView) view.findViewById(android.R.id.text1))
              //      .setText(Integer.toString(args.getInt(ARG_OBJECT)));
        }


        private void initList() {
            mRecyclerView = mRoot.findViewById(R.id.lista_produtos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRoot.getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRoot.getContext()));
            getProdutos(null, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.body() != null) {
                        ListaProduto adapter = new ListaProduto((com.example.myapplication.modelos.Produto[]) response.body());
                        mRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

        }
    }
}

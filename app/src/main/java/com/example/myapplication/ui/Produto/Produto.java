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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adaptador.ListaProduto;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Categoria;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.servico.Chamadas.getCategorias;
import static com.example.myapplication.servico.Chamadas.getProdutos;

public class Produto extends Fragment {
    private View mRoot;
    private RecyclerView mRecyclerView;
    private DemoCollectionPagerAdapter demoCollectionPagerAdapter;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_produto, container, false);
        mRoot = root;
        return root;
    }

    @Override
    public void onViewCreated(final @NonNull View view, @Nullable Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getChildFragmentManager();
        getCategorias(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    Categoria[] cat = (Categoria[]) response.body();
                    Categoria[] cat2 = new Categoria[cat.length];
                    for (int i = 1; i < cat.length; i++) {
                        cat2[i-1] = cat[i];
                    }
                    cat2[cat.length - 1] = new Categoria(-1, "Outros");
                    demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(fragmentManager, cat2);
                    viewPager = view.findViewById(R.id.pager);
                    viewPager.setAdapter(demoCollectionPagerAdapter);
                    TabLayout tabLayout = view.findViewById(R.id.tab_layout);
                    tabLayout.setupWithViewPager(viewPager);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        private Categoria[] mCategorias;
        public DemoCollectionPagerAdapter(FragmentManager fm, Categoria[] categorias) {
            super(fm);
            mCategorias = categorias;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_OBJECT, mCategorias[i].getId_categoria());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mCategorias.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategorias[position].getNome();
        }
    }

    // Instances of this class are fragments representing a single
    // object in our collection.
    public static class DemoObjectFragment extends Fragment implements ListaProduto.listaProdutoClick {
        public static final String ARG_OBJECT = "object";
        private View mRoot;
        private RecyclerView mRecyclerView;
        private DemoObjectFragment este;
        private Bundle mSavedInstanceState;
        public static String ID_PRODUTO = "ID_PRODUTO";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            mSavedInstanceState = savedInstanceState;
            return inflater.inflate(R.layout.content_produtos, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mRoot = view;
            Bundle args = getArguments();
            initList(args.getInt(ARG_OBJECT));
            este = this;
        }

        private void initList(int id_categoria) {
            mRecyclerView = mRoot.findViewById(R.id.lista_produtos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRoot.getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRoot.getContext()));
            ArrayList<String[]> filtro = new ArrayList<>();
            filtro.add(new String[]{"id_categoria", id_categoria != -1 ? id_categoria + "" : "null"});
            getProdutos(filtro, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.body() != null) {
                        ListaProduto adapter = new ListaProduto((com.example.myapplication.modelos.Produto[]) response.body(), este);
                        mRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

        }

        @Override
        public void onClickProduto(String idProduto) {
            Bundle bundle = new Bundle();
            bundle.putString(ID_PRODUTO, idProduto);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_cadastro_produto, bundle);
        }
    }
}

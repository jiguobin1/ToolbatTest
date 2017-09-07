package jiguobin.bc.com.toolbattest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jiguobin.bc.com.toolbattest.R;

/**
 * Created by acer-PC on 2017/4/14.
 */

public class NewsFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate( R.layout.newsfragment, null);

        tabLayout= (TabLayout) inflate.findViewById(R.id.tabLayout);
        viewPager= (ViewPager) inflate.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            String[] itemName=new String[]{
                    "头条","NBA","汽车","笑话"
            };
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new Fragment1();
                    case 1:
                        return new Fragment2();
                    case 2:
                        return new Fragment3();
                    case 3:
                        return new Fragment4();
                }
                return new Fragment1();
            }

            @Override
            public int getCount() {
                return 4;
            }
            //将标题文字指定给tablayout
            @Override
            public CharSequence getPageTitle(int position) {
                return itemName[position];
            }
        });
        //将tablayout与viewpaget结合
        tabLayout.setupWithViewPager(viewPager);

        return inflate;
    }
}

package cn.ixuehu.smartpeking.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import cn.ixuehu.smartpeking.BaseFragment;
import cn.ixuehu.smartpeking.MainUI;
import cn.ixuehu.smartpeking.R;
import cn.ixuehu.smartpeking.bean.NewsCenterBean;

/**
 * @项目名: Zhbj56
 * @包名: org.itheima.zhbj56.fragment
 * @类名: MenuFragment
 * @创建者: 肖琦
 * @创建时间: 2015-4-22 下午3:17:57
 * @描述: TODO
 * 
 * @svn版本: $Rev: 15 $
 * @更新人: $Author: xq $
 * @更新时间: $Date: 2015-04-22 15:25:20 +0800 (Wed, 22 Apr 2015) $
 * @更新描述: TODO
 */
public class MenuFragment extends BaseFragment implements AdapterView.OnItemClickListener
{
	private ListView 	mListView;
	private List<NewsCenterBean.NewsCenterMenuBean>	mMenuDatas;	// 菜单对应的数据
	private int							mCurrentMenu;
	private MenuListAdapter					mAdapter;
	@Override
	protected View initView()
	{
		/*TextView tv = new TextView(mActivity);

		tv.setText("菜单页面");
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);
		
		return tv;*/
		mListView = new ListView(mActivity);
        mListView.setBackgroundColor(Color.BLACK);
        mListView.setPadding(0, 40, 0, 0);
        mListView.setCacheColorHint(android.R.color.transparent);// 设置为透明
        mListView.setSelector(android.R.color.transparent);
        return mListView;
	}
	/**
	 * 给菜单设置数据
	 *
	 * @param datas
	 */
	public void setData(List<NewsCenterBean.NewsCenterMenuBean> datas)
	{
		// 存储data
		this.mMenuDatas = datas;

		// 设置默认选中项
		mCurrentMenu = 0;

		// 给listView设置 adapter ---> List
		mAdapter = new MenuListAdapter();
		mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

	}
	class MenuListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (mMenuDatas != null)
                return mMenuDatas.size();
            return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null)
            {
                view = View.inflate(mActivity, R.layout.item_menu, null);
                holder = new ViewHolder();
                // 设置tag
                view.setTag(holder);

                // view的初始化
                holder.tv = (TextView) view;
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            NewsCenterBean.NewsCenterMenuBean bean = mMenuDatas.get(i);

            // 设置数据
            holder.tv.setText(bean.title);

            holder.tv.setEnabled(mCurrentMenu == i);

            return view;
		}

		@Override
		public long getItemId(int i) {

            return i;
		}

		@Override
		public Object getItem(int i) {
			if (mMenuDatas != null)
                return mMenuDatas.get(i);
            return null;
		}
	}
    class ViewHolder
    {
        TextView tv;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MainUI ui = (MainUI)mActivity;
        ContentFragment contentFragment = ui.getContentFragment();
        contentFragment.switchMenu(i);

        SlidingMenu menu = ui.getSlidingMenu();
        menu.toggle();

        mCurrentMenu = i;
        mAdapter.notifyDataSetChanged();
    }
}

package cn.ixuehu.smartpeking.fragment;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.ixuehu.smartpeking.BaseFragment;
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
public class MenuFragment extends BaseFragment
{
	private ListView 	mListView;
	private List<NewsCenterBean.NewsCenterMenuBean>	mMenuDatas;	// 菜单对应的数据
	private int							mCurrentMenu;
	private MenuListAdapter					mAdapter;
	@Override
	protected View initView()
	{
		TextView tv = new TextView(mActivity);

		tv.setText("菜单页面");
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
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
	}
	class MenuListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			return null;
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public Object getItem(int i) {
			return null;
		}
	}

}

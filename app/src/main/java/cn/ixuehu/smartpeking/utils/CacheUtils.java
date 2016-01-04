package cn.ixuehu.smartpeking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CacheUtils
{
	private final static String			SP_NAME	= "smartpeking";
	private static SharedPreferences	mPreferences;		// SharedPreferences的实例

	private static SharedPreferences getSp(Context context)
	{
		if (mPreferences == null)
		{
			mPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}

		return mPreferences;
	}

	/**
	 * 通过SP获得boolean类型的数据，没有默认为false
	 * 
	 * @param context
	 *            : 上下文
	 * @param key
	 *            : 存储的key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key)
	{
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, false);
	}

	/**
	 * 通过SP获得boolean类型的数据，没有默认为false
	 * 
	 * @param context
	 *            : 上下文
	 * @param key
	 *            : 存储的key
	 * @param defValue
	 *            : 默认值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 设置boolean的缓存数据
	 * 
	 * @param context
	 * @param key
	 *            :缓存对应的key
	 * @param value
	 *            :缓存对应的值
	 */
	public static void setBoolean(Context context, String key, boolean value)
	{
		SharedPreferences sp = getSp(context);
		Editor edit = sp.edit();// 获取编辑器
		edit.putBoolean(key, value);
		edit.commit();
	}
}

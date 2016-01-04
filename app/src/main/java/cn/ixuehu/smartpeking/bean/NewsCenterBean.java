package cn.ixuehu.smartpeking.bean;

import java.util.List;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.bean
 * Created by daimaren on 2016/1/4.
 */
public class NewsCenterBean {
    public List<NewsCenterMenuBean> data;
    public List<Long>				extend;
    public int						retcode;

    public class NewsCenterMenuBean
    {
        public List<NewsBean>	children;
        public long				id;
        public String			title;
        public int				type;

        public String			url;
        public String			url1;

        public String			dayurl;
        public String			excurl;
        public String			weekurl;
    }

    public class NewsBean
    {
        public long		id;
        public String	title;
        public int		type;
        public String	url;
    }
}

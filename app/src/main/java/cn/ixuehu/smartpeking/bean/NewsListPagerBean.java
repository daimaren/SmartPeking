package cn.ixuehu.smartpeking.bean;

import java.util.List;

/**
 * 项目名：SmartPeking
 * 包名：cn.ixuehu.smartpeking.bean
 * Created by daimaren on 2016/1/8.
 */
public class NewsListPagerBean {
    public NewsListData data;
    public int recdode;
    public class NewsListData{
        public String					countcommenturl;
        public String					more;
        public List<NewsItemBean> news;
        public String					title;
        public List<NewsTopicBean>		topic;
        public List<NewsTopNewsBean>	topnews;
    }
    public class NewsItemBean{
        public boolean	comment;
        public String	commentlist;
        public String	commenturl;
        public long		id;
        public String	listimage;
        public String	pubdate;
        public String	title;
        public String	type;
        public String	url;
    }
    public class NewsTopicBean
    {
        public String	description;
        public long		id;
        public String	listimage;
        public int		sort;
        public String	title;
        public String	url;
    }

    public class NewsTopNewsBean
    {
        public boolean	comment;
        public String	commentlist;
        public String	commenturl;
        public long		id;
        public String	pubdate;
        public String	title;
        public String	topimage;
        public String	type;
        public String	url;
    }
}

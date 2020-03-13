package com.lixiang.house.house.common.page;


import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 16:27
 */
public class PageData<T> {

    //结果列表,结果页
    private List<T> list;

    //分页总数，第几页，每一页的大小
    private Pagination pagination;

    public PageData(Pagination pagination,List<T> list){
        this.pagination=pagination;
        this.list=list;
    }

    public static <T> PageData<T> buildPage(List<T> list,long count,Integer pageSize,Integer pageNum){
        Pagination pagination = new Pagination(pageSize, pageNum, count);
        return new PageData<>(pagination,list);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

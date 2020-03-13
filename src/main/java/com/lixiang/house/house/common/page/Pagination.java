package com.lixiang.house.house.common.page;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-10 16:32
 */
public class Pagination {

    //当前页数
    private int pageNum;

    //每一页的大小
    private int pageSize;

    //分页总数
    private long totalCount;

    //该字段为了支持1，2 .....7,8
    private List<Integer> pages = Lists.newArrayList();

    public Pagination(Integer pageSize,Integer pageNum,Long totalCount){
        this.totalCount = totalCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        for (int i = 1; i <= pageNum;i++){
            pages.add(i);
        }
        //总页数
        Long pageCount = totalCount/pageSize + ((totalCount%pageSize==0) ? 0 : 1);
        if (pageCount > pageNum){
            for (int i = pageNum + 1;i < pageCount;i++){
                pages.add(i);
            }
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}

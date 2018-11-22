package com.staff.util.filtering;

import com.staff.model.Vacancy;

public class SortPagining {
    public Vacancy vacancy;
    public String sortColumnName;
    public String order;

    public Integer page;
    public Integer pagesize;

    public String getSortColumnName() {
        return sortColumnName;
    }

    public void setSortColumnName(String sortColumnName) {
        this.sortColumnName = sortColumnName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public SortPagining(Vacancy vacancy, String sortColumnName, String order, Integer page, Integer pagesize) {
        this.vacancy=vacancy;
        this.sortColumnName = sortColumnName;
        this.order = order;
        this.page = page;
        this.pagesize = pagesize;
    }
    public SortPagining() {

    }
}

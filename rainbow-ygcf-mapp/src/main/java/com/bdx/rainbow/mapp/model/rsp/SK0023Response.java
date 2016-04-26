package com.bdx.rainbow.mapp.model.rsp;



import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 15/11/5.
 */
public class SK0023Response extends BDXBody {
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    private List<Map<String, String>> lists;//结果集

    public List<Map<String, String>> getLists() {
        return lists;
    }

    public void setLists(List<Map<String, String>> lists) {
        this.lists = lists;
    }
}

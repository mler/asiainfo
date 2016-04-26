package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 15/11/3.
 * 台帐上传
 */
public class SK0022Request extends BDXBody {

    private byte[] ticket;

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }

    private List<Map<String,String>> map;


    public List<Map<String,String>> getMap() {
        return map;
    }

    public void setMap(List<Map<String,String>> map) {
        this.map = map;
    }
}

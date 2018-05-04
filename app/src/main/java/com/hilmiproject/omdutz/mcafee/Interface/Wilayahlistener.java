package com.hilmiproject.omdutz.mcafee.Interface;

import com.hilmiproject.omdutz.mcafee.Pojo.TokoEntity;
import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;

import java.util.List;

/**
 * Created by L on 01/12/17.
 */

public interface Wilayahlistener {

    public void wilayah(List<Wilayah> wilayahs);
    public void getToko(List<TokoEntity> tokoEntities);
}

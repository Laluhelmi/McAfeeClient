package com.hilmiproject.omdutz.mcafee.Interface;

import com.hilmiproject.omdutz.mcafee.Pojo.LocationPojo;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by L on 25/11/17.
 */

public interface AfterMapDatLoaded {
    public void makeMap(List<LatLng> latLngs, List<LocationPojo> locationPojos);
}

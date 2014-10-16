package com.wizardofoz.workout;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.model.system.GeoPoint;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

import java.util.UUID;
@ServerType("Location")
public class WorkoutLocation extends DataItem {
    @ServerProperty("Name")
    private String name;
    @ServerProperty("Coordinates")
    private GeoPoint location;
    @ServerProperty("Picture")
    private UUID picture;
    @ServerProperty("Description")
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public UUID getPicture() {
        return picture;
    }

    public void setPicture(UUID picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

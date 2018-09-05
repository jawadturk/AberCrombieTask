package com.example.abercrombietask.di;

import com.example.abercrombietask.data.DataService;

import dagger.Component;


@AberCrombieApplicationScope
@Component(modules = {DataServiceModule.class})
public interface AberCrombieApplicationComponent {

    DataService getDataService();
}

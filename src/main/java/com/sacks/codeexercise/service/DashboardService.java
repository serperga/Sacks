package com.sacks.codeexercise.service;

import java.util.List;

import com.sacks.codeexercise.model.DashboardInformation;

public interface DashboardService {
    List<List<String>> createDashboardForUser();

}

package com.sacks.codeexercise.service;

import java.util.List;

public interface DashboardService {
    List<List<String>> createDashboardForUser(String username);
    List<List<String>> createDashboardForUsers();

}

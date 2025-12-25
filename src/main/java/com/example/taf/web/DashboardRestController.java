package com.example.taf.web;

import com.example.taf.dto.DashboardStat;
import com.example.taf.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardRestController {

    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStat getStats() {
        return dashboardService.getDashboardStats();
    }
}
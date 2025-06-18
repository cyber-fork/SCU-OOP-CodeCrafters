package com.fasttracklogistics;

import com.fasttracklogistics.view.panels.AssignDriverPanel;
import com.fasttracklogistics.view.panels.CustomerNotificationPanel; // ✅ Add this import
import com.fasttracklogistics.view.panels.DriverManagementPanel;
import com.fasttracklogistics.view.panels.DriverNotificationPanel;
import com.fasttracklogistics.view.panels.ManageShipmentsPanel;
import com.fasttracklogistics.view.panels.MonthlyReportPanel;
import com.fasttracklogistics.view.panels.SheduelingPanel;
import com.fasttracklogistics.view.panels.TrackingPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("FastTrack Logistics System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Driver Management", new DriverManagementPanel());
        tabs.addTab("Manage Shipments", new ManageShipmentsPanel());
        tabs.addTab("Assign Driver", new AssignDriverPanel());
        tabs.addTab("Scheduling", new SheduelingPanel());
        tabs.addTab("Driver Notifications", new DriverNotificationPanel());
        tabs.addTab("Customer Notifications", new CustomerNotificationPanel());
        tabs.addTab("Monthly Report", new MonthlyReportPanel());
        tabs.addTab("Tracking", new TrackingPanel());

        add(tabs, BorderLayout.CENTER);
    }
}
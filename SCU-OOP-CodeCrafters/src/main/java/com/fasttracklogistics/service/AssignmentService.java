package com.fasttracklogistics.service;

import com.fasttracklogistics.dao.AssignmentDao;
import com.fasttracklogistics.models.Assignment;

import java.util.List;

public class AssignmentService {
    private final AssignmentDao dao = new AssignmentDao();

    public void assignDriver(Assignment a) {
        dao.assignDriver(a);
    }

    public void removeAssignment(int assignmentId) {
        dao.removeAssignment(assignmentId);  // ✅ correct method name
    }

    public List<Assignment> getAllAssignments() {
        return dao.getAllAssignments();
    }
}

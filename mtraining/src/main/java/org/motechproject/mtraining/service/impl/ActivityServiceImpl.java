package org.motechproject.mtraining.service.impl;

import org.motechproject.mtraining.domain.ActivityRecord;
import org.motechproject.mtraining.domain.ActivityState;
import org.motechproject.mtraining.repository.ActivityDataService;
import org.motechproject.mtraining.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by kosh on 7/14/14.
 */
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDataService activityDataService;

    /**
     * Add activity for a user (identified by externalId field in the record)
     * @param activityRecord activity to record
     * @return activity record from the operation
     */
    @Override
    public ActivityRecord addActivity(ActivityRecord activityRecord) {
        return activityDataService.create(activityRecord);
    }

    /**
     * Update the activity for a user (set completion time, etc)
     * @param activityRecord activity record to update
     * @return updated activity record
     */
    @Override
    public ActivityRecord updateActivity(ActivityRecord activityRecord) {
        return activityDataService.update(activityRecord);
    }

    /**
     * Get a list of all activity for a user
     * @param externalId Id of the user
     * @return list of activity records
     */
    @Override
    public List<ActivityRecord> findActivityForUser(String externalId) {
        return activityDataService.findRecordsForUser(externalId);
    }

    /**
     * Get all completed activity for user identified by external id (decided by
     * whether completion time has been set on the record)
     * @param externalId external id of the user
     * @return list of activity records
     */
    @Override
    public List<ActivityRecord> getCompletedActivityForUser(String externalId) {
        return activityDataService.findCompletedRecordsForUser(externalId, ActivityState.FINISHED);
    }

    /**
     * Delete the activity by record id
     * @param activityRecordId activity record id to delete
     */
    @Override
    public void deleteActivity(long activityRecordId) {
        activityDataService.delete("id", activityRecordId);
    }

    /**
     * Delete all activity for a user (intended for data cleanups)
     * @param externalId Id of the user
     */
    @Override
    public void deleteActivityForUser(String externalId) {
        activityDataService.delete("externalId", externalId);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const filterType = urlParams.get('filter') || 'general';

    fetch('/api/recent-activity')
        .then(response => response.json())
        .then(data => {
            const activityColumn = document.querySelector('.activity-column .card-body');
            activityColumn.innerHTML = '';

            let filteredData = [];
            if (filterType === 'approval') {
                filteredData = data.filter(a => a.activity === 'APPROVED_USER' || a.activity === 'PENDING_APPROVAL' || a.activity === 'REJECTED_APPROVAL');
            } else {
                filteredData = data.filter(a => a.activity !== 'APPROVED_USER' && a.activity !== 'PENDING_APPROVAL' && a.activity !== 'REJECTED_APPROVAL');
            }

            // Show only the first 3 activities initially
            const initialActivities = filteredData.slice(0, 3);
            initialActivities.forEach(renderActivityItem);

            // Add "View All" button if more exist
            if (filteredData.length > 3) {
                const viewAllButton = document.createElement('a');
                viewAllButton.href = '#';
                viewAllButton.className = 'btn-view mt-2';
                viewAllButton.innerHTML = 'View All Activity <i class="fas fa-chevron-right"></i>';
                activityColumn.appendChild(viewAllButton);

                viewAllButton.addEventListener('click', function (event) {
                    event.preventDefault();
                    viewAllButton.remove();

                    const remainingActivities = filteredData.slice(3, 10);
                    remainingActivities.forEach(renderActivityItem);
                });
            }
        });

    function renderActivityItem(activity) {
        const activityColumn = document.querySelector('.activity-column .card-body');

        const activityItem = document.createElement('div');
        activityItem.className = 'activity-item';

        const activityInfo = document.createElement('div');
        activityInfo.className = 'activity-info';

        const title = document.createElement('div');
        title.className = 'activity-title';
        title.textContent = formatActivityTitle(activity.activity);

        const desc = document.createElement('div');
        desc.className = 'activity-desc';
        desc.textContent = getActivityDescription(activity);

        activityInfo.appendChild(title);
        activityInfo.appendChild(desc);

        const time = document.createElement('div');
        time.className = 'activity-time';
        time.textContent = formatTime(activity.date);

        activityItem.appendChild(activityInfo);
        activityItem.appendChild(time);

        activityColumn.appendChild(activityItem);
    }
});

function formatActivityTitle(activityType) {
    switch (activityType) {
        case 'CREATED_USER': return 'Added new user';
        case 'DELETED_USER': return 'Deleted user';
        case 'UPDATED_USER': return 'Updated profile';
        case 'APPROVED_USER': return 'Approved creation of the user';
        case 'PENDING_APPROVAL': return 'New pending approval';
        case 'REJECTED_APPROVAL': return 'Admin creation rejected';
        case 'ACTIVATED_USER': return 'Activated user profile';
        case 'DEACTIVATED_USER': return 'Deactivated user profile';
        case 'INITIALIZED_TEST': return 'Started test';
        case 'FAILED_TEST': return 'Test failed';
        case 'BIKE_ADDED': return 'Added a new bike';
        case 'BIKE_REMOVED': return 'Removed a bike';
        default: return 'Unknown';
    }
}

function getActivityDescription(activity) {
    return activity.description;
}

function formatTime(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diffTime = Math.abs(now - date);
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays === 0) return 'Today';
    if (diffDays === 1) return 'Yesterday';
    return `${diffDays} days ago`;
}
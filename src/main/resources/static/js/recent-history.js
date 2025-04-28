document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/recent-activity')
        .then(response => response.json())
        .then(data => {
            const activityColumn = document.querySelector('.activity-column .card-body');

            activityColumn.innerHTML = '';

            // Show only the first 3 activities initially
            const initialActivities = data.slice(0, 3);
            initialActivities.forEach(activity => {
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
            });

            const viewAllButton = document.createElement('a');
            viewAllButton.href = '#';
            viewAllButton.className = 'btn-view mt-2';
            viewAllButton.innerHTML = 'View All Activity <i class="fas fa-chevron-right"></i>';
            activityColumn.appendChild(viewAllButton);

            viewAllButton.addEventListener('click', function(event) {
                event.preventDefault();

                viewAllButton.remove();

                // Load all remaining activities when the button is clicked (starting from index 3)
                const remainingActivities = data.slice(3, 20);
                remainingActivities.forEach(activity => {
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
                });
            });
        });
});

function formatActivityTitle(activityType) {
    switch (activityType) {
        case 'CREATED_USER': return 'Added new user';
        case 'DELETED_USER': return 'Deleted user';
        case 'UPDATED_USER': return 'Updated profile';
        case 'APPROVED_USER': return 'Approved creation of the user';
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
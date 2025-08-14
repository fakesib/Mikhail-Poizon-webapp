         // Data for content feed
        const feedItems = [
            {
                id: 1,
                title: "Скоро тут будут новости",
                description: "Это только первый релиз, сильно не ругайте",
                image: "/photos/news.png",
                author: "",
                date: "8 авг 2025"
            },
            
        ];

        // Original feed items for search functionality
        let currentFeedItems = [...feedItems];

        // DOM Elements
        const profileButton = document.getElementById('profileButton');
        const dropdownContent = document.getElementById('dropdownContent');
        const overlay = document.getElementById('overlay');
        const searchInput = document.getElementById('searchInput');
        const contentGrid = document.getElementById('contentGrid');

        // Mobile check
        function isMobile() {
            return window.innerWidth < 640;
        }

        // Toggle dropdown menu
        function toggleDropdown() {
            const isOpen = dropdownContent.classList.contains('show');
            if (isOpen) {
                dropdownContent.classList.remove('show');
                overlay.classList.remove('show');
            } else {
                dropdownContent.classList.add('show');
                overlay.classList.add('show');
            }
        }

        // Close dropdown
        function closeDropdown() {
            dropdownContent.classList.remove('show');
            overlay.classList.remove('show');
        }

        // Search functionality
        function handleSearch() {
            const query = searchInput.value.toLowerCase().trim();
            
            if (query === '') {
                currentFeedItems = [...feedItems];
            } else {
                currentFeedItems = feedItems.filter(item => 
                    item.title.toLowerCase().includes(query) ||
                    item.description.toLowerCase().includes(query) ||
                    item.author.toLowerCase().includes(query)
                );
            }
            
            renderContent();
        }

        // Create content card HTML
        function createContentCard(item) {
            return `
                <div class="content-card">
                    <img src="${item.image}" alt="${item.title}" class="content-card-image">
                    <div class="content-card-content">
                        <h3 class="content-card-title line-clamp-2">${item.title}</h3>
                        <p class="content-card-description line-clamp-2">${item.description}</p>
                        <div class="content-card-meta">
                            <span class="content-card-author truncate">${item.author}</span>
                            <span class="content-card-date">${item.date}</span>
                        </div>
                    </div>
                </div>
            `;
        }

        // Render content feed
        function renderContent() {
            if (currentFeedItems.length === 0) {
                contentGrid.innerHTML = `
                    <div class="text-center p-6">
                        <p class="text-gray-500">Ничего не найдено</p>
                    </div>
                `;
                return;
            }

            contentGrid.innerHTML = currentFeedItems
                .map(item => createContentCard(item))
                .join('');
        }

        // Show/hide mobile auth buttons in dropdown
        function updateMobileAuthVisibility() {
            const mobileAuthDropdown = document.querySelector('.mobile-auth-dropdown');
            if (isMobile()) {
                mobileAuthDropdown.style.display = 'block';
            } else {
                mobileAuthDropdown.style.display = 'none';
            }
        }

        // Event Listeners
        profileButton.addEventListener('click', function(e) {
            e.stopPropagation();
            toggleDropdown();
        });

        overlay.addEventListener('click', closeDropdown);

        // Close dropdown when clicking outside
        document.addEventListener('click', function(e) {
            if (!profileButton.contains(e.target) && !dropdownContent.contains(e.target)) {
                closeDropdown();
            }
        });

        // Search input event listeners
        searchInput.addEventListener('input', handleSearch);
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                handleSearch();
            }
        });

        // Handle window resize
        window.addEventListener('resize', function() {
            updateMobileAuthVisibility();
            // Close dropdown on resize to prevent positioning issues
            closeDropdown();
        });

        // Handle dropdown item clicks
        document.querySelectorAll('.dropdown-item').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                closeDropdown();
                
                // Handle different actions
                const href = this.getAttribute('href');
                console.log('Navigating to:', href);
                
                // Add your navigation logic here
                // For example: window.location.href = href;
            });
        });

        // Handle control panel item clicks
        document.querySelectorAll('.control-panel-item').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                
                const href = this.getAttribute('href');
                console.log('Navigating to:', href);
                
                // Add your navigation logic here
                // For example: window.location.href = href;
            });
        });

        // Handle content card title clicks
        document.addEventListener('click', function(e) {
            if (e.target.classList.contains('content-card-title')) {
                console.log('Opening article:', e.target.textContent);
                // Add your article opening logic here
            }
        });

        // Initialize
        function init() {
            renderContent();
            updateMobileAuthVisibility();
        }

        // Start the application
        init();
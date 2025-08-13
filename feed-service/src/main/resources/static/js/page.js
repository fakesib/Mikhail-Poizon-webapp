         // Data for content feed
        const feedItems = [
            {
                id: 1,
                title: "Новые технологии в производстве",
                description: "Обзор последних инноваций и их применение в современной индустрии.",
                image: "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=400",
                author: "Иван Петров",
                date: "8 авг 2025"
            },
            {
                id: 2,
                title: "Успешные кейсы наших клиентов",
                description: "Истории успеха компаний, которые выбрали наши решения.",
                image: "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=400",
                author: "Мария Сидорова",
                date: "7 авг 2025"
            },
            {
                id: 3,
                title: "Тренды цифровой трансформации",
                description: "Как современные технологии меняют бизнес-процессы.",
                image: "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=400",
                author: "Алексей Козлов",
                date: "6 авг 2025"
            },
            {
                id: 4,
                title: "Экологичные решения",
                description: "Как технологии помогают сохранять окружающую среду.",
                image: "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=400",
                author: "Елена Новикова",
                date: "5 авг 2025"
            },
            {
                id: 5,
                title: "Автоматизация процессов",
                description: "Преимущества автоматизации для малого и среднего бизнеса.",
                image: "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=400",
                author: "Дмитрий Волков",
                date: "4 авг 2025"
            },
            {
                id: 6,
                title: "Будущее интернета вещей",
                description: "IoT технологии и их влияние на повседневную жизнь.",
                image: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400",
                author: "Ольга Смирнова",
                date: "3 авг 2025"
            }
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
                    <img src="${item.image}" alt="${item.title}" class="content-card-image" 
                         onerror="this.src='https://images.unsplash.com/photo-1557804506-669a67965ba0?w=400'">
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
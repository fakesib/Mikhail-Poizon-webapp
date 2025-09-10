// ==========================
// Общие DOM элементы
// ==========================
const overlay = document.getElementById('overlay');

// ==========================
// Дропдаун (первый кусок)
// ==========================
const profileButton = document.getElementById('profileButton');
const dropdownContent = document.getElementById('dropdownContent');
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

// Show/hide mobile auth buttons in dropdown
function updateMobileAuthVisibility() {
    const mobileAuthDropdown = document.querySelector('.mobile-auth-dropdown');
    if (isMobile()) {
        mobileAuthDropdown.style.display = 'block';
    } else {
        mobileAuthDropdown.style.display = 'none';
    }
}

// Event Listeners for dropdown
profileButton?.addEventListener('click', function(e) {
    e.stopPropagation();
    toggleDropdown();
});

overlay.addEventListener('click', () => {
    closeDropdown();
    closeSidebar(); // чтобы не мешался сайдбар
});

// Close dropdown when clicking outside
document.addEventListener('click', function(e) {
    if (!profileButton?.contains(e.target) && !dropdownContent?.contains(e.target)) {
        closeDropdown();
    }
});

// Search input event listeners
searchInput?.addEventListener('input', handleSearch);
searchInput?.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        handleSearch();
    }
});

// Handle window resize
window.addEventListener('resize', function() {
    updateMobileAuthVisibility();
    closeDropdown();
});

// Handle content card title clicks
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('content-card-title')) {
        console.log('Opening article:', e.target.textContent);
        // Add your article opening logic here
    }
});

// Initialize dropdown logic
function initDropdown() {
    renderContent();
    updateMobileAuthVisibility();
}
initDropdown();


// ==========================
// Сайдбар + профиль (второй кусок)
// ==========================
const menuBtn = document.getElementById('menuBtn');
const mobileSidebar = document.getElementById('mobileSidebar');
const closeSidebarBtn = document.getElementById('closeSidebarBtn');

menuBtn?.addEventListener('click', openSidebar);
closeSidebarBtn?.addEventListener('click', closeSidebar);

function openSidebar() {
    mobileSidebar.classList.add('open');
    overlay.classList.add('show');
    document.body.style.overflow = 'hidden';
}

function closeSidebar() {
    mobileSidebar.classList.remove('open');
    overlay.classList.remove('show');
    document.body.style.overflow = '';
}

// Input Management
function clearInput(inputId) {
    const input = document.getElementById(inputId);
    input.value = '';
    input.focus();
}

// Email Management
function editEmail() {
    const emailDisplay = document.getElementById('emailDisplay');
    const emailEdit = document.getElementById('emailEdit');
    const emailInput = document.getElementById('emailInput');
    const currentEmail = document.getElementById('currentEmail');

    emailInput.value = currentEmail.textContent;
    emailDisplay.style.display = 'none';
    emailEdit.style.display = 'block';
    emailInput.focus();
}

function confirmEmail() {
    const emailDisplay = document.getElementById('emailDisplay');
    const emailEdit = document.getElementById('emailEdit');
    const emailInput = document.getElementById('emailInput');
    const currentEmail = document.getElementById('currentEmail');

    if (emailInput.value.trim() && isValidEmail(emailInput.value)) {
        currentEmail.textContent = emailInput.value;
        emailDisplay.style.display = 'block';
        emailEdit.style.display = 'none';
    } else {
        alert('Пожалуйста, введите корректный email адрес');
    }
}

function cancelEmailEdit() {
    const emailDisplay = document.getElementById('emailDisplay');
    const emailEdit = document.getElementById('emailEdit');

    emailDisplay.style.display = 'block';
    emailEdit.style.display = 'none';
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Navigation Functions
function goHome() {
    alert('Переход на главную страницу');
    // window.location.href = '/';
}

function saveProfile() {
    const formData = {
        username: document.getElementById('username').value,
        tgUsername: document.getElementById('tgUsername').value,
        birthDate: document.getElementById('birthDate').value,
        city: document.getElementById('city').value,
        street: document.getElementById('street').value,
        house: document.getElementById('house').value,
        email: document.getElementById('currentEmail').textContent
    };

    console.log('Saving profile:', formData);
    alert('Профиль успешно сохранен!');
}

// Password Management Functions
function togglePassword(fieldId) {
    const input = document.getElementById(fieldId);
    const showIcon = document.getElementById(fieldId + '-show');
    const hideIcon = document.getElementById(fieldId + '-hide');

    if (input.type === 'password') {
        input.type = 'text';
        showIcon.style.display = 'none';
        hideIcon.style.display = 'block';
    } else {
        input.type = 'password';
        showIcon.style.display = 'block';
        hideIcon.style.display = 'none';
    }
}

function validateNewPassword() {
    const newPassword = document.getElementById('newPassword').value;
    const errorElement = document.getElementById('newPasswordError');

    if (newPassword && newPassword.length < 8) {
        errorElement.style.display = 'block';
    } else {
        errorElement.style.display = 'none';
    }

    updateChangePasswordButton();
    validatePasswordMatch();
}

function validatePasswordMatch() {
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const errorElement = document.getElementById('confirmPasswordError');

    if (confirmPassword && newPassword !== confirmPassword) {
        errorElement.style.display = 'block';
    } else {
        errorElement.style.display = 'none';
    }

    updateChangePasswordButton();
}

function updateChangePasswordButton() {
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const changeBtn = document.getElementById('changePasswordBtn');

    const isValid = currentPassword.length > 0 && 
                   newPassword.length >= 8 && 
                   confirmPassword.length > 0 && 
                   newPassword === confirmPassword;

    changeBtn.disabled = !isValid;
}

function changePassword() {
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (!currentPassword) {
        alert('Введите текущий пароль');
        return;
    }

    if (!newPassword) {
        alert('Введите новый пароль');
        return;
    }

    if (newPassword.length < 8) {
        alert('Новый пароль должен содержать минимум 8 символов');
        return;
    }

    if (newPassword !== confirmPassword) {
        alert('Пароли не совпадают');
        return;
    }

    console.log('Changing password');
    
    document.getElementById('currentPassword').value = '';
    document.getElementById('newPassword').value = '';
    document.getElementById('confirmPassword').value = '';
    
    document.getElementById('newPasswordError').style.display = 'none';
    document.getElementById('confirmPasswordError').style.display = 'none';
    
    updateChangePasswordButton();
    
    alert('Пароль успешно изменен!');
}

// Navigation Links
document.querySelectorAll('.nav-item').forEach(item => {
    if (!item.onclick) {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const href = item.getAttribute('href');
            const page = href.replace('#', '');
            
            closeSidebar();
            
            setTimeout(() => {
                alert(`Переход на страницу: ${getPageName(page)}`);
            }, 300);
        });
    }
});

function getPageName(page) {
    const pageNames = {
        'profile': 'Профиль',
        'news': 'Новости',
        'order': 'Заказать',
        'calculator': 'Калькулятор',
        'my-orders': 'Мои заказы',
        'reviews': 'Отзывы'
    };
    return pageNames[page] || page;
}

// Keyboard Shortcuts
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        if (mobileSidebar.classList.contains('open')) {
            closeSidebar();
        }
        if (document.getElementById('emailEdit').style.display === 'block') {
            cancelEmailEdit();
        }
    }
});

// Auto-hide clear buttons when input is empty
document.querySelectorAll('.input-with-clear input').forEach(input => {
    const clearBtn = input.parentNode.querySelector('.btn-clear');
    
    function toggleClearButton() {
        clearBtn.style.display = input.value ? 'block' : 'none';
    }
    
    toggleClearButton();
    input.addEventListener('input', toggleClearButton);
});

// Password input event listeners
document.getElementById('currentPassword')?.addEventListener('input', updateChangePasswordButton);
document.getElementById('newPassword')?.addEventListener('input', validateNewPassword);
document.getElementById('confirmPassword')?.addEventListener('input', validatePasswordMatch);
        function handleLogin() {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            if (!username || !password) {
                alert('Пожалуйста, заполните все поля');
                return;
            }
        }

        // Простая валидация в реальном времени
        document.getElementById('username').addEventListener('input', function(e) {
            if (e.target.value.length > 0) {
                e.target.style.borderColor = '#84FFFE';
            } else {
                e.target.style.borderColor = 'rgba(0, 0, 0, 0.1)';
            }
        });

        document.getElementById('password').addEventListener('input', function(e) {
            if (e.target.value.length > 0) {
                e.target.style.borderColor = '#84FFFE';
            } else {
                e.target.style.borderColor = 'rgba(0, 0, 0, 0.1)';
            }
        });
    
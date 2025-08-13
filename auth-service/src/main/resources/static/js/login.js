        function handleLogin() {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            if (!username || !password) {
                alert('Пожалуйста, заполните все поля');
                return;
            }
            
            console.log('Login attempt:', { username, password });
            alert('Попытка входа: ' + username);
            // Здесь можно добавить логику для отправки данных на сервер
        }

        function handleForgotPassword() {
            console.log('Forgot password clicked');
            alert('Функция восстановления пароля');
            // Здесь можно добавить логику для восстановления пароля
        }

        function handleRegister() {
            console.log('Register clicked');
            alert('Переход к регистрации');
            // Здесь можно добавить логику для перехода к форме регистрации
        }

        // Обработка отправки формы по Enter
        document.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                handleLogin();
            }
        });

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
    